package com.monsjoker.namadaexplorer.uis.screens.validators.views

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.monsjoker.namadaexplorer.data.network.supabase.aauxuambgprwlwvfpksz.models.Validator
import com.monsjoker.namadaexplorer.uis.shared_view.BottomSheetSelectedView

@Composable
fun ValidatorBottomSheetView(
    navController: NavController,
    validator: Validator,
    modifier: Modifier = Modifier
) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()

    Column(
        modifier = Modifier
            .padding(horizontal = 12.dp)
            .padding(bottom = 32.dp) then modifier,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Text(text = "Address:")
            BottomSheetSelectedView(
                text = validator.address!!,
                modifier = Modifier.clickable {
                    navBackStackEntry?.savedStateHandle?.set(
                        "validator_address",
                        validator.address
                    )
                    navController.navigate("validator")
                }
            )
        }
    }
}