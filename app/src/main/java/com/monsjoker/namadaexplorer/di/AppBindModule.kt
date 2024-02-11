package com.monsjoker.namadaexplorer.di

import android.content.Context
import com.moandjiezana.toml.Toml
import com.monsjoker.namadaexplorer.data.local.TomlLocal
import com.monsjoker.namadaexplorer.data.local.TomlLocalImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class AppBindModule {
    @Binds
    abstract fun getTomlLocal(tomlLocal: TomlLocalImpl): TomlLocal
}