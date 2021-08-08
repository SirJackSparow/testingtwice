package com.startup.twiscodetest.data.server

import android.util.Log
import okhttp3.Interceptor
import okhttp3.Response
import java.net.SocketTimeoutException

class GlobalInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        var request = chain.request()
        request = request.newBuilder()
            .build()
        if (isConnectionTimedOut(chain))
            throw SocketTimeoutException()
        return chain.proceed(request)
    }

    private fun isConnectionTimedOut(chain: Interceptor.Chain): Boolean {
        try {
            val response = chain.proceed(chain.request())
            val content = response.toString()
            response.close()
            Log.d("tag", "isConnectionTimedOut() => $content")
        } catch (e: SocketTimeoutException) {
            return true
        }
        return false
    }
}