package com.monsjoker.namadaexplorer.uis.screens.governance

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.monsjoker.namadaexplorer.data.domain.DataState
import com.monsjoker.namadaexplorer.data.network.id_namada_red.ItNamadaRedNetwork
import com.monsjoker.namadaexplorer.data.network.id_namada_red.models.Proposal
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Singleton

@HiltViewModel
class GovernanceViewModel @Inject constructor(
    @Singleton private val itNamadaRedNetwork: ItNamadaRedNetwork
) : ViewModel() {
    var proposalsState by mutableStateOf<DataState<List<Proposal>>>(DataState.Loading())
        private set

    init {
        loadProposals()
    }

    fun loadProposals() {
        proposalsState = DataState.Loading()
        viewModelScope.launch {
            proposalsState = try {
                val proposals = itNamadaRedNetwork.fetchProposals().proposals
                DataState.Success(data = proposals)
            } catch (e: Exception) {
                DataState.Error(error = e)
            }
        }
    }
}