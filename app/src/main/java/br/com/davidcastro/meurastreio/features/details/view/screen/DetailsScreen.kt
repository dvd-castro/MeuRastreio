package br.com.davidcastro.meurastreio.features.details.view.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import br.com.davidcastro.meurastreio.core.theme.GetSecondaryColor
import br.com.davidcastro.meurastreio.features.details.view.components.DetailsToolbar

@Composable
fun DetailsScreen(
    navController: NavHostController,
) {
    Scaffold(
        containerColor = GetSecondaryColor(),
        topBar = {
            DetailsToolbar {
                navController.popBackStack()
            }
        }
    ) { padding ->
        Column(modifier = Modifier.padding(padding)) {

        }
    }
}