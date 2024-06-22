package br.com.davidcastro.meurastreio.core.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import br.com.davidcastro.meurastreio.features.details.view.screen.DetailsScreen
import br.com.davidcastro.meurastreio.features.home.view.screen.HomeScreen

@Composable
fun AppNavigation(
    navController: NavHostController = rememberNavController(),
) {
    NavHost(
        navController = navController,
        startDestination = Routes.HomeScreen,
    ) {
        composable<Routes.HomeScreen> {
            HomeScreen(
                navController = navController,
            )
        }

        composable<Routes.DetailScreen> {
            DetailsScreen(
                navController = navController
            )
        }
    }
}