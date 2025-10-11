package com.tamin.taminhamrah.di.interceptor


import jafari.movie.BuildConfig
import okhttp3.Interceptor
import okhttp3.Response
import timber.log.Timber
import java.io.IOException

class RequestInterceptor : Interceptor {

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        val originalUrl = originalRequest.url
        val url = originalUrl.newBuilder()
            .build()

        val requestBuilder = originalRequest.newBuilder().url(url)
        val request = requestBuilder.build()
        if (BuildConfig.DEBUG) {
            Timber.tag("NetworkInterceptor").i(
                "header :${originalRequest.headers} \n body:${originalRequest.body}\n requesr=$request",
            )
        }
        return chain.proceed(request)
    }
}
