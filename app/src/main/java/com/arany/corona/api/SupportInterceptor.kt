package com.arany.corona.api

import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class SupportInterceptor @Inject constructor(): Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        var request = chain.request()
        request = request.newBuilder()
            .addHeader("accept", "application/json")
            .addHeader("Accept-Language", "en_US")
            .addHeader("user-agent","Mozilla/5.0 (Macintosh; Intel Mac OS X 10_11_6) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/56.0.2924.87 Safari/537.36")
            .build()
        return chain.proceed(request)
    }
}