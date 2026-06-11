package uz.akbarovdev.myexpenses.app.navigation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.DrawerState
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.NavigationDrawerItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.toRoute
import kotlinx.coroutines.launch
import uz.akbarovdev.myexpenses.R
import uz.akbarovdev.myexpenses.features.dashboard.presentation.DashboardRoot
import uz.akbarovdev.myexpenses.features.debt.presentation.DebtDetailRoot
import uz.akbarovdev.myexpenses.features.debt.presentation.DebtListRoot
import uz.akbarovdev.myexpenses.features.preference.presentation.PreferenceRoot
import uz.akbarovdev.myexpenses.features.settings.SettingsRoot
import uz.akbarovdev.myexpenses.features.transactions.TransactionStatisticsRoot
import uz.akbarovdev.myexpenses.features.transactions.TransactionsRoot

val LocalDrawerState = staticCompositionLocalOf<DrawerState> {
    error("No DrawerState provided")
}

@Composable
fun DrawerNavigationRoot(
    navController: NavHostController,
    drawerState: DrawerState
) {
    val scope = rememberCoroutineScope()
    val currentBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = currentBackStackEntry?.destination?.route

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet(modifier = Modifier.width(280.dp)) {
                AppDrawerHeader()
                Spacer(Modifier.height(8.dp))
                Column(Modifier.verticalScroll(rememberScrollState())) {
                    DrawerItem(
                        label = stringResource(R.string.dashboard),
                        icon = Icons.Default.Home,
                        isSelected = currentRoute?.contains("Dashboard") == true,
                        onClick = {
                            navController.navigate(NavigationRoutes.Dashboard) {
                                popUpTo(NavigationRoutes.Dashboard) { inclusive = true }
                            }
                            scope.launch { drawerState.close() }
                        }
                    )
                    DrawerItem(
                        label = stringResource(R.string.all_transactions),
                        icon = Icons.Default.List,
                        isSelected = currentRoute?.contains("Transactions") == true,
                        onClick = {
                            navController.navigate(NavigationRoutes.Transactions)
                            scope.launch { drawerState.close() }
                        }
                    )
                    DrawerItem(
                        label = stringResource(R.string.debt_notebook),
                        icon = Icons.Default.Person,
                        isSelected = currentRoute?.contains("Debt") == true,
                        onClick = {
                            navController.navigate(NavigationRoutes.DebtList) {
                                popUpTo(NavigationRoutes.Dashboard)
                            }
                            scope.launch { drawerState.close() }
                        }
                    )
                    DrawerItem(
                        label = stringResource(R.string.settings),
                        icon = Icons.Default.Settings,
                        isSelected = currentRoute?.contains("Settings") == true,
                        onClick = {
                            navController.navigate(NavigationRoutes.Settings)
                            scope.launch { drawerState.close() }
                        }
                    )
                }
            }
        }
    ) {
        CompositionLocalProvider(LocalDrawerState provides drawerState) {
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
                composable<NavigationRoutes.DebtList> {
                    DebtListRoot(navController)
                }
                composable<NavigationRoutes.DebtDetail> { backStackEntry ->
                    val route = backStackEntry.toRoute<NavigationRoutes.DebtDetail>()
                    DebtDetailRoot(navController, route.userId)
                }
                composable<NavigationRoutes.Statistics> {
                    TransactionStatisticsRoot(navController)
                }
            }
        }
    }
}

@Composable
private fun AppDrawerHeader() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.primary)
            .padding(horizontal = 24.dp, vertical = 32.dp)
    ) {
        Column {
            Text(
                stringResource(R.string.app_name),
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onPrimary
            )
        }
    }
}

@Composable
private fun DrawerItem(
    label: String,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    NavigationDrawerItem(
        label = {
            Text(
                label,
                fontWeight = if (isSelected) FontWeight.SemiBold else FontWeight.Normal
            )
        },
        icon = { Icon(icon, contentDescription = label) },
        selected = isSelected,
        onClick = onClick,
        modifier = Modifier.padding(horizontal = 12.dp),
        colors = NavigationDrawerItemDefaults.colors(
            selectedContainerColor = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.4f),
            unselectedContainerColor = Color.Transparent
        ),
        shape = RoundedCornerShape(12.dp)
    )
}
