package com.monsjoker.namadaexplorer.data.network.supabase.tgwsikrpibxhbmtgrhbo

import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response

class TgwsikrpibxhbmtgrhboInterceptor: Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        var request: Request = chain.request()
        request = request.newBuilder()
            .addHeader("Apikey", "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6InRnd3Npa3JwaWJ4aGJtdGdyaGJvIiwicm9sZSI6ImFub24iLCJpYXQiOjE3MDczODQxMjMsImV4cCI6MjAyMjk2MDEyM30._nk0neGqFkSp2Nx7Jpr0Nj0Z1vqILRjcPx1kQdqGV64")
            .addHeader("accept-profile", "shielded_expedition_bis")
            .build()
        return chain.proceed(request)
    }
}