package uz.akbarovdev.myexpenses.app.navigation


import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import uz.akbarovdev.myexpenses.features.dashboard.presentation.DashboardRoot
import uz.akbarovdev.myexpenses.features.settings.SettingsRoot

@Composable
fun NavigationRoot(
    navController: NavHostController
) {
    NavHost(
        navController = navController,
        startDestination = NavigationRoutes.Dashboard
    ) {
        composable<NavigationRoutes.Dashboard> {
            DashboardRoot(
                navController
            )
        }
        composable<NavigationRoutes.Settings> {
            SettingsRoot()
        }
    }

}