package com.monsjoker.namadaexplorer.uis.screens.home

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.monsjoker.namadaexplorer.data.domain.DataState
import com.monsjoker.namadaexplorer.data.network.id_namada_red.ItNamadaRedNetwork
import com.monsjoker.namadaexplorer.data.network.namada_rpc_hadesguard_tech.NamadaRpcHadesGuardTechNetwork
import com.monsjoker.namadaexplorer.data.network.namada_rpc_hadesguard_tech.models.ValidatorInfoRequest
import com.monsjoker.namadaexplorer.data.network.supabase.aauxuambgprwlwvfpksz.AauxuambgprwlwvfpkszNetwork
import com.monsjoker.namadaexplorer.data.network.supabase.aauxuambgprwlwvfpksz.models.Validator
import com.monsjoker.namadaexplorer.data.network.supabase.models.SupabaseOrder
import com.monsjoker.namadaexplorer.data.network.supabase.models.SupabaseSelect
import com.monsjoker.namadaexplorer.data.network.supabase.models.createQueryString
import com.monsjoker.namadaexplorer.data.network.supabase.tgwsikrpibxhbmtgrhbo.TgwsikrpibxhbmtgrhboNetwork
import com.monsjoker.namadaexplorer.data.network.supabase.tgwsikrpibxhbmtgrhbo.models.Block
import com.monsjoker.namadaexplorer.uis.screens.home.data.HomeDetailsData
import com.monsjoker.namadaexplorer.utils.base64Number
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Singleton

@HiltViewModel
class HomeViewModel @Inject constructor(
    @Singleton private val supabaseAaNetwork: AauxuambgprwlwvfpkszNetwork,
    @Singleton private val supabaseTgNetwork: TgwsikrpibxhbmtgrhboNetwork,
    @Singleton private val itNamadaRedNetwork: ItNamadaRedNetwork,
    @Singleton private val namadaRpcHadesGuardTechNetwork: NamadaRpcHadesGuardTechNetwork
) : ViewModel() {
    var validatorsState by mutableStateOf<DataState<List<Validator>>>(DataState.Loading())
        private set

    var blocksState by mutableStateOf<DataState<List<Block>>>(DataState.Loading())
        private set

    var homeDetailsState by mutableStateOf<DataState<HomeDetailsData>>(DataState.Loading())
        private set

    private var isBlocksLoaded = false

    init {
        loadHomeDetails()
    }

    fun load10Validators() {
        validatorsState = DataState.Loading()
        viewModelScope.launch {
            validatorsState = try {
                val validators = get10Validator().map { it }
                DataState.Success(validators)
            } catch (e: Exception) {
                DataState.Error(e)
            }
        }
    }

    fun load10Blocks() {
        if (isBlocksLoaded) return
        blocksState = DataState.Loading()
        viewModelScope.launch {
            blocksState = try {
                val blocks = get10Blocks()
                DataState.Success(blocks)
            } catch (e: Exception) {
                DataState.Error(e)
            }
        }
    }

    fun loadHomeDetails() {
        homeDetailsState = DataState.Loading()
        viewModelScope.launch {
            try {
                when (val currentState = blocksState) {
                    is DataState.Loading -> {
                        val blocks = get10Blocks()
                        blocksState = DataState.Success(blocks)
                        setHomeDataState(blocks)
                        isBlocksLoaded = true
                    }

                    is DataState.Success -> {
                        setHomeDataState(currentState.data)
                    }

                    is DataState.Error -> {
                        homeDetailsState = DataState.Error(error = currentState.error)
                    }
                }
            } catch (e: Exception) {
                isBlocksLoaded = true
                blocksState = DataState.Error(e)
                homeDetailsState = DataState.Error(e)
            }
        }
    }

    private suspend fun get10Validator(): List<Validator> {
        return supabaseAaNetwork.fetchValidators(
            select = SupabaseSelect.empty.createQueryString(),
            order = listOf(
                SupabaseOrder(
                    SupabaseOrder.SortField.VOTING_POWER,
                    SupabaseOrder.SortOrder.DESC
                )
            ).createQueryString(),
            limit = 10,
            offset = 0
        )
    }

    private suspend fun get10Blocks(): List<Block> {
        return supabaseTgNetwork.fetchBlocks(
            select = SupabaseSelect.blocks.createQueryString(),
            order = listOf(
                SupabaseOrder(
                    SupabaseOrder.SortField.HEADER_HEIGHT,
                    SupabaseOrder.SortOrder.DESC
                )
            ).createQueryString(),
            limit = 10,
            offset = 0
        )
    }

    private suspend fun setHomeDataState(blocks: List<Block>) {
        homeDetailsState = try {
            val blockHeight = blocks.maxOfOrNull { it.headerHeight } ?: 0
            val validators = supabaseAaNetwork.fetchValidators(
                select = listOf(SupabaseSelect.VOTING_POWER).createQueryString()
            )
            val epoch =
                namadaRpcHadesGuardTechNetwork.fetcVaidatorsInfo(request = ValidatorInfoRequest.epoch).result.response.value.base64Number
            val totalStake =
                namadaRpcHadesGuardTechNetwork.fetcVaidatorsInfo(request = ValidatorInfoRequest.totalStake).result.response.value.base64Number
            val allStake =
                supabaseAaNetwork.fetchValidators(listOf(SupabaseSelect.VOTING_POWER).createQueryString())
                    .sumOf { it.votingPower.toDouble() / 1_000_000 }.toLong()
            val governanceProposals = itNamadaRedNetwork.fetchProposals().proposals.size
            val data = HomeDetailsData(
                epoch = epoch,
                blockHeight = blockHeight,
                totalStake = totalStake,
                allState = allStake,
                validators = validators.size,
                governanceProposals = governanceProposals
            )
            DataState.Success(data)
        } catch (e: Exception) {
            DataState.Error(e)
        }
    }
}