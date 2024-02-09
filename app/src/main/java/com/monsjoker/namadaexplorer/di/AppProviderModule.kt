package com.monsjoker.namadaexplorer.di

import com.google.gson.FieldNamingPolicy
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.monsjoker.namadaexplorer.BuildConfig
import com.monsjoker.namadaexplorer.data.network.NamadaInfo
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
            interceptor.level = HttpLoggingInterceptor.Level.BODY
        }

    @Provides
    @Singleton
    fun provideGson(): Gson =
        GsonBuilder().setFieldNamingPolicy(FieldNamingPolicy.IDENTITY).create()

    @Singleton
    @Provides
    fun providesOkHttpClientBuilder(
        httpLoggingInterceptor: HttpLoggingInterceptor
    ): OkHttpClient.Builder {
        val builder = OkHttpClient.Builder()
            .readTimeout(NetworkUtils.TIME_OUT, TimeUnit.MILLISECONDS)
            .connectTimeout(NetworkUtils.TIME_OUT, TimeUnit.MILLISECONDS)
        return if (BuildConfig.DEBUG) {
            builder.addInterceptor(httpLoggingInterceptor)
        } else {
            builder
        }
    }

    @Singleton
    @Provides
    fun providesAauxuambgprwlwvfpkszNetwork(
        okHttpBuilder: OkHttpClient.Builder,
        factory: Gson
    ): AauxuambgprwlwvfpkszNetwork = Retrofit.Builder()
        .baseUrl(BuildConfig.AAUXUAMBGPRWLWVFPKSZ_SUPABASE_URL)
        .addConverterFactory(GsonConverterFactory.create(factory))
        .client(okHttpBuilder.addInterceptor(AauxuambgprwlwvfpkszInterceptor()).build())
        .build()
        .create()

    @Singleton
    @Provides
    fun providesTgwsikrpibxhbmtgrhboNetwork(
        okHttpBuilder: OkHttpClient.Builder,
        factory: Gson
    ): TgwsikrpibxhbmtgrhboNetwork = Retrofit.Builder()
        .baseUrl(BuildConfig.TGWSIKRPIBXHBMTEGRHBO_SUPABASE_URL)
        .addConverterFactory(GsonConverterFactory.create(factory))
        .client(okHttpBuilder.addInterceptor(TgwsikrpibxhbmtgrhboInterceptor()).build())
        .build()
        .create()

    fun providesNamadaInfo(
        okHttpBuilder: OkHttpClient.Builder,
        factory: Gson
    ): NamadaInfo = Retrofit.Builder()
        .baseUrl(BuildConfig.TGWSIKRPIBXHBMTEGRHBO_SUPABASE_URL)
        .addConverterFactory(GsonConverterFactory.create(factory))
        .client(okHttpBuilder.build())
        .build()
        .create()
}