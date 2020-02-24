package com.lvfq.kotlinbase.api.net

import okhttp3.Headers
import okhttp3.Interceptor
import okhttp3.Response
import okhttp3.internal.http.HttpHeaders
import okhttp3.internal.platform.Platform
import okio.Buffer
import okio.GzipSource
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import java.io.EOFException
import java.nio.charset.Charset
import java.util.concurrent.TimeUnit

/**
 * HttpLogIntercepter
 * @author FaQiang on 2018/11/14 下午3:56
 * @desc :
 *
 */
class HttpLogIntercepter constructor(private var logger: Logger) : Interceptor {
    private val UTF8 = Charset.forName("UTF-8")

    constructor() : this(Logger.DEFAULT)

    enum class Level {
        /** No logs.  */
        NONE,
        /**
         * Logs request and response lines.
         *
         *
         * Example:
         * <pre>`--> POST /greeting http/1.1 (3-byte body)
         *
         * <-- 200 OK (22ms, 6-byte body)
        `</pre> *
         */
        BASIC,
        /**
         * Logs request and response lines and their respective headers.
         *
         *
         * Example:
         * <pre>`--> POST /greeting http/1.1
         * Host: example.com
         * Content-Type: plain/text
         * Content-Length: 3
         * --> END POST
         *
         * <-- 200 OK (22ms)
         * Content-Type: plain/text
         * Content-Length: 6
         * <-- END HTTP
        `</pre> *
         */
        HEADERS,
        /**
         * Logs request and response lines and their respective headers and bodies (if present).
         *
         *
         * Example:
         * <pre>`--> POST /greeting http/1.1
         * Host: example.com
         * Content-Type: plain/text
         * Content-Length: 3
         *
         * Hi?
         * --> END POST
         *
         * <-- 200 OK (22ms)
         * Content-Type: plain/text
         * Content-Length: 6
         *
         * Hello!
         * <-- END HTTP
        `</pre> *
         */
        BODY
    }


    interface Logger {
        fun log(message: String?)

        companion object {
            /** A [Logger] defaults output appropriate for the current platform.  */
            val DEFAULT: Logger = object : Logger {
                override fun log(message: String?) {
                    Platform.get().log(Platform.INFO, message, null)
                }
            }
        }
    }

    @Volatile
    private var level = Level.NONE


    /** Change the level at which this interceptor logs.  */
    fun setLevel(level: Level): HttpLogIntercepter {
        this.level = level
        return this
    }

    fun getLevel(): Level {
        return level
    }

    override fun intercept(chain: Interceptor.Chain): Response {
        val level = this.level

        val request = chain.request()
        if (level == Level.NONE) {
            return chain.proceed(request)
        }

        val logBuilder = StringBuilder()

        val logBody = level == Level.BODY
        val logHeaders = logBody || level == Level.HEADERS

        val requestBody = request.body()
        val hasRequestBody = requestBody != null

        val connection = chain.connection()
        var requestStartMessage = ("--> "
                + request.method()
                + ' '.toString() + request.url()
                + if (connection != null) " " + connection.protocol() else "")
        if (!logHeaders && hasRequestBody) {
            requestStartMessage += " (" + requestBody?.contentLength() + "-byte body)"
        }
        logBuilder.append(requestStartMessage)

        if (logHeaders) {
            if (hasRequestBody) {
                // Request body headers are only present when installed as a network interceptor. Force
                // them to be included (when available) so there values are known.
                if (requestBody?.contentType() != null) {
                    logBuilder.append("\nContent-Type: " + requestBody.contentType())
                }
                if (requestBody?.contentLength() != -1L) {
                    logBuilder.append("\nContent-Length: " + requestBody?.contentLength())
                }
            }

            val headers = request.headers()
            var i = 0
            val count = headers.size()
            while (i < count) {
                val name = headers.name(i)
                // Skip headers from the request body as they are explicitly logged above.
                if (!"Content-Type".equals(name, ignoreCase = true) && !"Content-Length".equals(name, ignoreCase = true)) {
                    logBuilder.append("\n" + name + ": " + headers.value(i))
                }
                i++
            }

            if (!logBody || !hasRequestBody) {
                logBuilder.append("\n--> END " + request.method())
            } else if (bodyHasUnknownEncoding(request.headers())) {
                logBuilder.append("\n--> END " + request.method() + " (encoded body omitted)")
            } else {
                val buffer = Buffer()
                requestBody?.writeTo(buffer)

                var charset: Charset? = UTF8
                val contentType = requestBody?.contentType()
                if (contentType != null) {
                    charset = contentType.charset(UTF8)
                }

                logBuilder.append("\n")
                if (isPlaintext(buffer)) {
                    logBuilder.append("\n" + buffer.readString(charset))
                    logBuilder.append("\n--> END " + request.method()
                            + " (" + requestBody?.contentLength() + "-byte body)")
                } else {
                    logBuilder.append("\n--> END " + request.method() + " (binary "
                            + requestBody?.contentLength() + "-byte body omitted)")
                }
            }
        }

        val startNs = System.nanoTime()
        val response: Response
        try {
            response = chain.proceed(request)
        } catch (e: Exception) {
            logBuilder.append("\n<-- HTTP FAILED: $e")
            throw e
        }

        val tookMs = TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - startNs)

        val responseBody = response.body()
        val contentLength = responseBody?.contentLength()
        val bodySize = if (contentLength != -1L) contentLength.toString() + "-byte" else "unknown-length"
        logBuilder.append("\n<-- "
                + response.code()
                + (if (response.message().isEmpty()) "" else ' ' + response.message())
                + ' '.toString() + response.request().url()
                + " (" + tookMs + "ms" + (if (!logHeaders) ", $bodySize body" else "") + ')'.toString())

        if (logHeaders) {
            val headers = response.headers()
            var i = 0
            val count = headers.size()
            while (i < count) {
                logBuilder.append("\n" + headers.name(i) + ": " + headers.value(i))
                i++
            }

            if (!logBody || !HttpHeaders.hasBody(response)) {
                logBuilder.append("\n<-- END HTTP")
            } else if (bodyHasUnknownEncoding(response.headers())) {
                logBuilder.append("\n<-- END HTTP (encoded body omitted)")
            } else {
                val source = responseBody?.source()
                source?.request(java.lang.Long.MAX_VALUE) // Buffer the entire body.
                var buffer = source?.buffer()

                var gzippedLength: Long? = null
                if ("gzip".equals(headers.get("Content-Encoding"), ignoreCase = true)) {
                    gzippedLength = buffer?.size() ?: 0
                    var gzippedResponseBody: GzipSource? = null
                    try {
                        if (buffer != null) {
                            gzippedResponseBody = GzipSource(buffer.clone())
                            buffer = Buffer()
                            buffer.writeAll(gzippedResponseBody)
                        }
                    } finally {
                        gzippedResponseBody?.close()
                    }
                }

                var charset: Charset? = UTF8
                val contentType = responseBody?.contentType()
                if (contentType != null) {
                    charset = contentType.charset(UTF8)
                }

                if (!isPlaintext(buffer)) {
                    logBuilder.append("\n")
                    logBuilder.append("\n<-- END HTTP (binary " + (buffer?.size()
                            ?: 0) + "-byte body omitted)")
                    return response
                }

                if (contentLength != 0L) {
                    logBuilder.append("\n")
                    logBuilder.append("\n" + formatJson(buffer?.clone()?.readString(charset)))
                }

                if (gzippedLength != null) {
                    logBuilder.append("\n<-- END HTTP (" + (buffer?.size() ?: 0) + "-byte, "
                            + gzippedLength + "-gzipped-byte body)")
                } else {
                    logBuilder.append("\n<-- END HTTP (" + (buffer?.size() ?: 0) + "-byte body)")
                }
            }
        }
        logger.log(logBuilder.toString())
        return response
    }

    /**
     * Returns true if the body in question probably contains human readable text. Uses a small sample
     * of code points to detect unicode control characters commonly used in binary file signatures.
     */
    private fun isPlaintext(buffer: Buffer?): Boolean {
        if (buffer == null) {
            return false
        }
        try {
            val prefix = Buffer()
            val byteCount = if (buffer.size() < 64) buffer.size() else 64
            buffer.copyTo(prefix, 0, byteCount)
            for (i in 0..15) {
                if (prefix.exhausted()) {
                    break
                }
                val codePoint = prefix.readUtf8CodePoint()
                if (Character.isISOControl(codePoint) && !Character.isWhitespace(codePoint)) {
                    return false
                }
            }
            return true
        } catch (e: EOFException) {
            return false // Truncated UTF-8 sequence.
        }

    }

    private fun bodyHasUnknownEncoding(headers: Headers): Boolean {
        val contentEncoding = headers.get("Content-Encoding")
        return (contentEncoding != null
                && !contentEncoding.equals("identity", ignoreCase = true)
                && !contentEncoding.equals("gzip", ignoreCase = true))
    }

    private fun formatJson(json: String?): String? {
        if (json == null) {
            return json
        }
        var tempJson = json
        try {
            if (json.startsWith("{")) {
                tempJson = JSONObject(json).toString(4)
            } else if (json.startsWith("[")) {
                tempJson = JSONArray(json).toString(4)
            }
        } catch (e: JSONException) {
            e.printStackTrace()
        }
        return tempJson
    }
}