package com.monsjoker.namadaexplorer.uis.screens.validators

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
import com.monsjoker.namadaexplorer.data.network.supabase.models.SupabaseOrder
import com.monsjoker.namadaexplorer.data.network.supabase.models.SupabaseSelect
import com.monsjoker.namadaexplorer.data.network.supabase.models.createQueryString
import com.monsjoker.namadaexplorer.data.network.supabase.tgwsikrpibxhbmtgrhbo.TgwsikrpibxhbmtgrhboNetwork
import com.monsjoker.namadaexplorer.data.network.supabase.tgwsikrpibxhbmtgrhbo.models.Block
import com.monsjoker.namadaexplorer.uis.screens.home.data.HomeDetailsData
import com.monsjoker.namadaexplorer.utils.Constants
import com.monsjoker.namadaexplorer.utils.base64Number
import com.monsjoker.namadaexplorer.utils.paging.supabase.supabasePager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Singleton

@HiltViewModel
class ValidatorsViewModel @Inject constructor(
    @Singleton private val supabaseAaNetwork: AauxuambgprwlwvfpkszNetwork,
    @Singleton private val supabaseTgNetwork: TgwsikrpibxhbmtgrhboNetwork,
    @Singleton private val itNamadaRedNetwork: ItNamadaRedNetwork,
    @Singleton private val namadaRpcHadesGuardTechNetwork: NamadaRpcHadesGuardTechNetwork
) : ViewModel() {
    val pagingData = supabasePager { page ->
        supabaseAaNetwork.fetchValidators(
            select = SupabaseSelect.empty.createQueryString(),
            order = listOf(
                SupabaseOrder(
                    field = SupabaseOrder.SortField.VOTING_POWER,
                    order = SupabaseOrder.SortOrder.DESC
                ),
                SupabaseOrder(
                    field = SupabaseOrder.SortField.HEIGHT,
                    order = SupabaseOrder.SortOrder.DESC
                )
            ).createQueryString(),
            limit = Constants.limitPage,
            offset = page * Constants.limitPage
        )
    }.flow

    var homeDetailsState by mutableStateOf<DataState<HomeDetailsData>>(DataState.Loading())
        private set

    private var isDetailLoaded = false

    init {
        loadHomeDetails()
    }

    fun loadHomeDetails() {
        if (!isDetailLoaded) {
            homeDetailsState = DataState.Loading()
            viewModelScope.launch {
                try {
                    val blocks = get10Blocks()
                    setHomeDataState(blocks)
                } catch (e: Exception) {
                    homeDetailsState = DataState.Error(e)
                }
            }
        }
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
            val governanceProposals = itNamadaRedNetwork.fetchProposals().proposals.size
            val data = HomeDetailsData(
                epoch = epoch,
                blockHeight = blockHeight,
                totalStake = totalStake,
                validators = validators.size,
                governanceProposals = governanceProposals
            )
            DataState.Success(data)
        } catch (e: Exception) {
            DataState.Error(e)
        }
    }
}