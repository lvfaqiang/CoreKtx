package com.lvfq.kotlinbase.utils.tool

import android.annotation.SuppressLint
import android.content.Context
import android.location.*
import android.os.Bundle
import cn.basic.core.util.LogUtil
import java.util.*

/**
 * LocationUtil8/10/21 12:14 AM
 * @desc :
 *
 */
class LocationUtil(val context: Context) {

    private var locationManager: LocationManager? = null
    private var locationListener: LocationListener? = null

    var locationCallBack: (Location) -> Unit = {}

    // 初始化调用
    fun initLocationManager(isToSetting: Boolean = false) {
        locationManager = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager?
//        if (isToSetting && locationManager?.isProviderEnabled(LocationManager.GPS_PROVIDER) == false) {
//            // 转到手机设置界面，用户设置GPS
//            val intent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
//            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
//            context.startActivity(intent)
//        }
        locationListener = object : LocationListener {

            override fun onLocationChanged(location: Location) {
                if (location == null) {
                    return
                }
                locationCallBack(location)
                release()
            }

            override fun onStatusChanged(provider: String?, status: Int, extras: Bundle?) {
            }

            override fun onProviderEnabled(provider: String) {
            }

            override fun onProviderDisabled(provider: String) {
//                if (isForeground) onLocationGranted(false)
            }
        }
        getProvider()
    }

    @SuppressLint("MissingPermission")
    private fun getProvider() {
        val list = locationManager?.getProviders(true)

        val provider = when {
            list?.contains(LocationManager.GPS_PROVIDER) == true -> LocationManager.NETWORK_PROVIDER
            list?.contains(LocationManager.NETWORK_PROVIDER) == true -> LocationManager.NETWORK_PROVIDER
            else -> null
        }

        if (provider != null) {
            val location = locationManager?.getLastKnownLocation(provider)
            if (location != null) {
                LogUtil.i("------------------")
//                currentLocation = LatLng(location.latitude, location.longitude)
            }
            locationManagerRequestLocationUpdates()
        }
    }

    @SuppressLint("MissingPermission")
    private fun locationManagerRequestLocationUpdates() {
        release()
        for (provider in listOf(LocationManager.NETWORK_PROVIDER, LocationManager.GPS_PROVIDER)) {
            if (locationManager?.isProviderEnabled(provider) == true) {
                locationListener?.let {
                    locationManager?.requestLocationUpdates(provider, 2000, 50f, it)
                }
            }
        }
    }

    // 转换结果编码。
    fun getAddress(context: Context, location: Location): Address? {
        val gc = Geocoder(context, Locale.getDefault())
        val result = try {
            gc.getFromLocation(location.latitude, location.longitude, 1)
        } catch (e: Exception) {
            listOf<Address>()
        }
        if (result.isNotEmpty()) {
            return result[0]
        }
        //  省  address?.adminArea    市 address?.locality   区： address?.subLocality
        return null
    }

    fun release() {
        locationListener?.let {
            locationManager?.removeUpdates(it)
        }
    }

}