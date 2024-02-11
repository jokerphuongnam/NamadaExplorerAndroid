package com.monsjoker.namadaexplorer.data.local

import com.monsjoker.namadaexplorer.data.local.models.TomlData
import javax.inject.Singleton

@Singleton
interface TomlLocal {
    suspend fun readParameters(): TomlData
}