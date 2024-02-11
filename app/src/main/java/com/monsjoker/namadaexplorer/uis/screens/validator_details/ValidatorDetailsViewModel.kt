package com.monsjoker.namadaexplorer.uis.screens.validator_details

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.monsjoker.namadaexplorer.data.domain.DataState
import com.monsjoker.namadaexplorer.data.network.supabase.aauxuambgprwlwvfpksz.AauxuambgprwlwvfpkszNetwork
import com.monsjoker.namadaexplorer.data.network.supabase.aauxuambgprwlwvfpksz.models.Validator
import com.monsjoker.namadaexplorer.data.network.supabase.models.SupabaseOrder
import com.monsjoker.namadaexplorer.data.network.supabase.models.SupabaseSelect
import com.monsjoker.namadaexplorer.data.network.supabase.models.createQueryString
import com.monsjoker.namadaexplorer.data.network.supabase.tgwsikrpibxhbmtgrhbo.TgwsikrpibxhbmtgrhboNetwork
import com.monsjoker.namadaexplorer.data.network.supabase.tgwsikrpibxhbmtgrhbo.models.Block
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ValidatorDetailsViewModel @Inject constructor(
    private val supabaseAaNetwork: AauxuambgprwlwvfpkszNetwork,
    private val supabaseTgNetwork: TgwsikrpibxhbmtgrhboNetwork
) : ViewModel() {
    var validatorState by mutableStateOf<DataState<Validator>>(DataState.Loading())
        private set
    var blocksState by mutableStateOf<DataState<List<Block>>>(DataState.Loading())

    fun loadValidator(validatorAddress: String) {
        validatorState = DataState.Loading()
        viewModelScope.launch {
            validatorState = try {
                val validator = supabaseAaNetwork.fetchValidator(
                    select = SupabaseSelect.empty.createQueryString(),
                    address = "eq.$validatorAddress"
                ).first()
                DataState.Success(data = validator)
            } catch (e: Exception) {
                DataState.Error(error = e)
            }
        }
    }

    fun loadBlocks(validatorAddress: String) {
        blocksState = DataState.Loading()
        viewModelScope.launch {
            blocksState = try {
                val blocks = supabaseTgNetwork.fetchBlocks(
                    select = SupabaseSelect.blocks.createQueryString(),
                    order = listOf(
                        SupabaseOrder(
                            field = SupabaseOrder.SortField.HEADER_HEIGHT,
                            order = SupabaseOrder.SortOrder.DESC
                        )
                    ).createQueryString(),
                    offset = 0,
                    limit = 10,
                    headerProposerAddress = "eq.$validatorAddress"
                )
                DataState.Success(data = blocks)
            } catch (e: Exception) {
                DataState.Error(error = e)
            }
        }
    }
}