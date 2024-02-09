package com.monsjoker.namadaexplorer.data.network.supabase.aauxuambgprwlwvfpksz

import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response

class AauxuambgprwlwvfpkszInterceptor: Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        var request: Request = chain.request()
        request = request.newBuilder()
            .addHeader("Apikey", "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6ImFhdXh1YW1iZ3Byd2x3dmZwa3N6Iiwicm9sZSI6ImFub24iLCJpYXQiOjE2ODE3MjI1NTMsImV4cCI6MTk5NzI5ODU1M30.R5SJhC6QmHDeHxpIvCdCSGx6Lhd5BFHWMzlmPKvWpBc")
            .build()
        return chain.proceed(request)
    }
}