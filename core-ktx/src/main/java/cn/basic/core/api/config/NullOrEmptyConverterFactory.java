package cn.basic.core.api.config;

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

import okhttp3.ResponseBody;
import retrofit2.Converter;
import retrofit2.Retrofit;

/**
 * NullOnEmptyConverterFactory2020/6/29 11:29 PM
 *
 * @desc :  用来处理接口为返回数据 所造成的 Null 问题
 */
public class NullOrEmptyConverterFactory extends Converter.Factory {
    @Override
    public Converter<ResponseBody, ?> responseBodyConverter(Type type, Annotation[] annotations, Retrofit retrofit) {
        final Converter<ResponseBody, ?> delegate = retrofit.nextResponseBodyConverter(this, type, annotations);
        return (Converter<ResponseBody, Object>) body -> {
            long contentLength = body.contentLength();
            if (contentLength == 0) {
                return "{}";
            }
            return delegate.convert(body);
        };
    }
}
