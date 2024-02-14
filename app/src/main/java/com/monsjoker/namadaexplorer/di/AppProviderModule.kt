@file:Suppress("NAME_SHADOWING")

package com.monsjoker.namadaexplorer.di

import com.google.gson.FieldNamingPolicy
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.moandjiezana.toml.Toml
import com.monsjoker.namadaexplorer.BuildConfig
import com.monsjoker.namadaexplorer.data.network.id_namada_red.ItNamadaRedNetwork
import com.monsjoker.namadaexplorer.data.network.namada_info.NamadaInfoNetwork
import com.monsjoker.namadaexplorer.data.network.namada_rpc_hadesguard_tech.NamadaRpcHadesGuardTechNetwork
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

    @Provides
    fun providesClientBuilder(): OkHttpClient.Builder = OkHttpClient.Builder()
        .readTimeout(NetworkUtils.TIME_OUT, TimeUnit.MILLISECONDS)
        .connectTimeout(NetworkUtils.TIME_OUT, TimeUnit.MILLISECONDS)

    @Singleton
    @Provides
    fun providesAauxuambgprwlwvfpkszNetwork(
        httpLoggingInterceptor: HttpLoggingInterceptor,
        factory: Gson,
        clientBuilder: OkHttpClient.Builder
    ): AauxuambgprwlwvfpkszNetwork {
        val clientBuilder = clientBuilder.addInterceptor(AauxuambgprwlwvfpkszInterceptor())
        if (BuildConfig.DEBUG) {
            clientBuilder.addInterceptor(httpLoggingInterceptor)
        }
        return Retrofit.Builder()
            .baseUrl(BuildConfig.AAUXUAMBGPRWLWVFPKSZ_SUPABASE_URL)
            .addConverterFactory(GsonConverterFactory.create(factory))
            .client(clientBuilder.build())
            .build()
            .create()
    }

    @Singleton
    @Provides
    fun providesTgwsikrpibxhbmtgrhboNetwork(
        httpLoggingInterceptor: HttpLoggingInterceptor,
        factory: Gson,
        clientBuilder: OkHttpClient.Builder
    ): TgwsikrpibxhbmtgrhboNetwork {
        val clientBuilder = clientBuilder.addInterceptor(TgwsikrpibxhbmtgrhboInterceptor())
        if (BuildConfig.DEBUG) {
            clientBuilder.addInterceptor(httpLoggingInterceptor)
        }
        return Retrofit.Builder()
            .baseUrl(BuildConfig.TGWSIKRPIBXHBMTEGRHBO_SUPABASE_URL)
            .addConverterFactory(GsonConverterFactory.create(factory))
            .client(clientBuilder.build())
            .build()
            .create()
    }

    @Singleton
    @Provides
    fun providesNamadaInfoNetwork(
        httpLoggingInterceptor: HttpLoggingInterceptor,
        factory: Gson,
        clientBuilder: OkHttpClient.Builder
    ): NamadaInfoNetwork {
        if (BuildConfig.DEBUG) {
            clientBuilder.addInterceptor(httpLoggingInterceptor)
        }
        return Retrofit.Builder()
            .baseUrl(BuildConfig.NAMADA_INFO_URL)
            .addConverterFactory(GsonConverterFactory.create(factory))
            .client(clientBuilder.build())
            .build()
            .create()
    }

    @Singleton
    @Provides
    fun providesItNamadaRedNetwork(
        httpLoggingInterceptor: HttpLoggingInterceptor,
        factory: Gson,
        clientBuilder: OkHttpClient.Builder
    ): ItNamadaRedNetwork {
        if (BuildConfig.DEBUG) {
            clientBuilder.addInterceptor(httpLoggingInterceptor)
        }
        return Retrofit.Builder()
            .baseUrl(BuildConfig.IT_NAMADA_RED_URL)
            .addConverterFactory(GsonConverterFactory.create(factory))
            .client(clientBuilder.build())
            .build()
            .create()
    }

    @Singleton
    @Provides
    fun providesNamadaRpcHadesGuardTechNetwork(
        httpLoggingInterceptor: HttpLoggingInterceptor,
        factory: Gson,
        clientBuilder: OkHttpClient.Builder
    ): NamadaRpcHadesGuardTechNetwork {
        if (BuildConfig.DEBUG) {
            clientBuilder.addInterceptor(httpLoggingInterceptor)
        }
        return Retrofit.Builder()
            .baseUrl(BuildConfig.NAMADA_RPC_HADESGUARD_TECH_URL)
            .addConverterFactory(GsonConverterFactory.create(factory))
            .client(clientBuilder.build())
            .build()
            .create()
    }

    @Singleton
    @Provides
    fun providesToml(): Toml = Toml()
}