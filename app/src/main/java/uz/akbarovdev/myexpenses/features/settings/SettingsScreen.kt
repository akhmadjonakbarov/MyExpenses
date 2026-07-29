@file:OptIn(ExperimentalMaterial3Api::class)

package uz.akbarovdev.myexpenses.features.settings

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import uz.akbarovdev.myexpenses.R
import uz.akbarovdev.myexpenses.app.navigation.NavigationRoutes
import uz.akbarovdev.myexpenses.core.design_system.top_bar.Title
import uz.akbarovdev.myexpenses.features.settings.components.MenuItem
import uz.akbarovdev.myexpenses.ui.theme.MyExpensesTheme

@Composable
fun SettingsRoot(
    navController: NavController,
    onLogout: () -> Unit = {},
) {
    SettingsScreen(
        navController = navController,
        onLogout = onLogout,
    )
}


@Composable
fun SettingsScreen(
    navController: NavController,
    onLogout: () -> Unit = {},
) {

    Scaffold(
        containerColor = Color(0xFFF4E8FF),
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xFFF4E8FF)
                ),
                navigationIcon = {
                    IconButton(
                        onClick = navController::navigateUp
                    ) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = null)
                    }
                },
                title = {
                    Title(
                        title = stringResource(R.string.settings),
                    )
                }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(10.dp)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        color = MaterialTheme.colorScheme.surfaceContainerLowest,
                        shape = RoundedCornerShape(15.dp)
                    )
                    .padding(5.dp),
            ) {
                Column {
                    MenuItem(
                        icon = Icons.Outlined.Settings,
                        containerColor = MaterialTheme.colorScheme.surfaceContainerLow,
                        text = stringResource(R.string.preference),
                        onClick = { navController.navigate(NavigationRoutes.Preference) }
                    )

                    Spacer(
                        modifier = Modifier.height(
                            15.dp
                        )
                    )
                    MenuItem(
                        icon = Icons.Default.Lock,
                        containerColor = MaterialTheme.colorScheme.surfaceContainerLow,
                        text = stringResource(R.string.security),
                        onClick = { navController.navigate(NavigationRoutes.Preference) }
                    )

                }
            }

            Spacer(
                Modifier.height(
                    10.dp
                )
            )
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        color = MaterialTheme.colorScheme.surfaceContainerLowest,
                        shape = RoundedCornerShape(15.dp)
                    )
                    .padding(5.dp),
            ) {
                MenuItem(
                    icon = Icons.Default.ExitToApp,
                    text = stringResource(R.string.logout),
                    containerColor = MaterialTheme.colorScheme.error.copy(0.08f),
                    textColor = MaterialTheme.colorScheme.error,
                    onClick = onLogout
                )
            }
        }
    }
}


@Preview
@Composable
private fun Preview() {
    MyExpensesTheme {
        SettingsScreen(
            navController = rememberNavController()
        )
    }
}