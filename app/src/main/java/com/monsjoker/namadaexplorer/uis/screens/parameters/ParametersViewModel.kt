package com.monsjoker.namadaexplorer.uis.screens.parameters

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.monsjoker.namadaexplorer.data.domain.DataState
import com.monsjoker.namadaexplorer.data.network.namada_info.NamadaInfoNetwork
import com.monsjoker.namadaexplorer.data.network.namada_info.models.GenesisAccount
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Singleton

@HiltViewModel
class ParametersViewModel @Inject constructor(
    @Singleton private val namadaInfoNetwork: NamadaInfoNetwork
) : ViewModel() {
    var genesisAccountsState by mutableStateOf<DataState<List<GenesisAccount>>>(DataState.Loading())
        private set

    init {
        loadGenesisAccounts()
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
