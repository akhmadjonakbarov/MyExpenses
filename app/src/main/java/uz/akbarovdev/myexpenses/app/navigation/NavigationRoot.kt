package uz.akbarovdev.myexpenses.app.navigation


import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import uz.akbarovdev.myexpenses.features.dashboard.presentation.DashboardRoot
import uz.akbarovdev.myexpenses.features.preference.PreferenceRoot
import uz.akbarovdev.myexpenses.features.settings.SettingsRoot
import uz.akbarovdev.myexpenses.features.transactions.TransactionsRoot

@Composable
fun NavigationRoot(
    navController: NavHostController
) {
    NavHost(
        navController = navController,
        startDestination = NavigationRoutes.Dashboard
    ) {
        composable<NavigationRoutes.Dashboard> {
            DashboardRoot(navController)
        }
        composable<NavigationRoutes.Settings> {
            SettingsRoot(navController)
        }
        composable<NavigationRoutes.Transactions> {
            TransactionsRoot(navController)
        }
        composable<NavigationRoutes.Preference> {
            PreferenceRoot(navController)
        }
    }

}