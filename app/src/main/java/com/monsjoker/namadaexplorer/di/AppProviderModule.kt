package com.monsjoker.namadaexplorer.di

import com.google.gson.FieldNamingPolicy
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.moandjiezana.toml.Toml
import com.monsjoker.namadaexplorer.BuildConfig
import com.monsjoker.namadaexplorer.data.network.namada_info.NamadaInfoNetwork
import com.monsjoker.namadaexplorer.data.network.supabase.aauxuambgprwlwvfpksz.AauxuambgprwlwvfpkszInterceptor
import com.monsjoker.namadaexplorer.data.network.supabase.aauxuambgprwlwvfpksz.AauxuambgprwlwvfpkszNetwork
import com.monsjoker.namadaexplorer.data.network.supabase.tgwsikrpibxhbmtgrhbo.TgwsikrpibxhbmtgrhboInterceptor
import com.monsjoker.namadaexplorer.data.network.supabase.tgwsikrpibxhbmtgrhbo.TgwsikrpibxhbmtgrhboNetwork
import com.monsjoker.namadaexplorer.utils.NetworkUtils
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppProviderModule {
    @Singleton
    @Provides
    fun providesHttpLoggingInterceptor(): HttpLoggingInterceptor =
        HttpLoggingInterceptor().also { interceptor ->
            interceptor.setLevel(HttpLoggingInterceptor.Level.HEADERS)
            interceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
        }

    @Provides
    @Singleton
    fun provideGson(): Gson =
        GsonBuilder().setFieldNamingPolicy(FieldNamingPolicy.IDENTITY).create()

    @Singleton
    @Provides
    fun providesAauxuambgprwlwvfpkszNetwork(
        httpLoggingInterceptor: HttpLoggingInterceptor,
        factory: Gson
    ): AauxuambgprwlwvfpkszNetwork {
        val client = OkHttpClient.Builder()
            .readTimeout(NetworkUtils.TIME_OUT, TimeUnit.MILLISECONDS)
            .connectTimeout(NetworkUtils.TIME_OUT, TimeUnit.MILLISECONDS).addInterceptor(AauxuambgprwlwvfpkszInterceptor())
        if (BuildConfig.DEBUG) {
            client.addInterceptor(httpLoggingInterceptor)
        }
        return Retrofit.Builder()
            .baseUrl(BuildConfig.AAUXUAMBGPRWLWVFPKSZ_SUPABASE_URL)
            .addConverterFactory(GsonConverterFactory.create(factory))
            .client(client.build())
            .build()
            .create()
    }

    @Singleton
    @Provides
    fun providesTgwsikrpibxhbmtgrhboNetwork(
        httpLoggingInterceptor: HttpLoggingInterceptor,
        factory: Gson
    ): TgwsikrpibxhbmtgrhboNetwork {
        val client = OkHttpClient.Builder()
            .readTimeout(NetworkUtils.TIME_OUT, TimeUnit.MILLISECONDS)
            .connectTimeout(NetworkUtils.TIME_OUT, TimeUnit.MILLISECONDS).addInterceptor(TgwsikrpibxhbmtgrhboInterceptor())
        if (BuildConfig.DEBUG) {
            client.addInterceptor(httpLoggingInterceptor)
        }
        return Retrofit.Builder()
            .baseUrl(BuildConfig.TGWSIKRPIBXHBMTEGRHBO_SUPABASE_URL)
            .addConverterFactory(GsonConverterFactory.create(factory))
            .client(client.build())
            .build()
            .create()
    }

    @Singleton
    @Provides
    fun providesNamadaInfoNetwork(
        httpLoggingInterceptor: HttpLoggingInterceptor,
        factory: Gson
    ): NamadaInfoNetwork {
        val client = OkHttpClient.Builder()
            .readTimeout(NetworkUtils.TIME_OUT, TimeUnit.MILLISECONDS)
            .connectTimeout(NetworkUtils.TIME_OUT, TimeUnit.MILLISECONDS)
        if (BuildConfig.DEBUG) {
            client.addInterceptor(httpLoggingInterceptor)
        }
        return Retrofit.Builder()
            .baseUrl(BuildConfig.NAMADA_INFO_URL)
            .addConverterFactory(GsonConverterFactory.create(factory))
            .client(client.build())
            .build()
            .create()
    }

    @Singleton
    @Provides
    fun providesToml(): Toml = Toml()
}