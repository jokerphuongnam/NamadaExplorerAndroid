package com.monsjoker.namadaexplorer.uis.screens.parameters

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.monsjoker.namadaexplorer.data.domain.DataState
import com.monsjoker.namadaexplorer.data.local.TomlLocal
import com.monsjoker.namadaexplorer.data.local.models.TomlData
import com.monsjoker.namadaexplorer.data.network.namada_info.NamadaInfoNetwork
import com.monsjoker.namadaexplorer.data.network.namada_info.models.GenesisAccount
import com.monsjoker.namadaexplorer.utils.Logger
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Singleton

@HiltViewModel
class ParametersViewModel @Inject constructor(
    @Singleton private val namadaInfoNetwork: NamadaInfoNetwork,
    @Singleton private val tomlLocal: TomlLocal
) : ViewModel() {
    var tomlDataState by mutableStateOf<DataState<TomlData>>(DataState.Loading())
        private set

    var genesisAccountsState by mutableStateOf<DataState<List<GenesisAccount>>>(DataState.Loading())
        private set

    init {
        loadTomlData()
        loadGenesisAccounts()
    }

    fun loadTomlData() {
        tomlDataState = DataState.Loading()
        Logger.d("loadTomlData", "Loading")
        viewModelScope.launch {
            tomlDataState = try {
                val tomlData = tomlLocal.readParameters()
                Logger.d("loadTomlData", "Success: $tomlData")
                DataState.Success(data = tomlData)
            } catch (e: Exception) {
                Logger.e("loadTomlData", "Error: $e")
                DataState.Error(error = e)
            }
        }
    }

    fun loadGenesisAccounts() {
        genesisAccountsState = DataState.Loading()
        viewModelScope.launch(Dispatchers.IO) {
            genesisAccountsState = try {
                val validators = namadaInfoNetwork.fetchGenesisAccounts()
                DataState.Success(validators)
            } catch (e: Exception) {
                DataState.Error(e)
            }
        }
    }
}
