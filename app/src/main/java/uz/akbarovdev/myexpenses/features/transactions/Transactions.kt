@file:OptIn(ExperimentalMaterial3Api::class)

package uz.akbarovdev.myexpenses.features.transactions

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import uz.akbarovdev.myexpenses.R
import uz.akbarovdev.myexpenses.core.design_system.buttons.BackButton
import uz.akbarovdev.myexpenses.core.design_system.common_components.NoTransaction
import uz.akbarovdev.myexpenses.core.design_system.top_bar.Title
import uz.akbarovdev.myexpenses.features.dashboard.presentation.components.TransactionItem
import uz.akbarovdev.myexpenses.features.dashboard.presentation.view_model.DashboardAction
import uz.akbarovdev.myexpenses.features.dashboard.presentation.view_model.DashboardState
import uz.akbarovdev.myexpenses.features.dashboard.presentation.view_model.DashboardViewModel
import uz.akbarovdev.myexpenses.ui.theme.MyExpensesTheme

@Composable
fun TransactionsRoot(
    navController: NavController,
    viewModel: DashboardViewModel = viewModel(),

    ) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    TransactionsScreen(
        navController = navController,
        state = state, onAction = viewModel::onAction
    )
}

@Composable
fun TransactionsScreen(
    navController: NavController,
    state: DashboardState,
    onAction: (DashboardAction) -> Unit,
) {
    Scaffold(
        topBar = {
            TopAppBar(navigationIcon = {
                BackButton(onClick = navController::navigateUp)
            }, title = {
                Title(
                    "All Transaction",
                )
            }, actions = {
                when {
                    state.transactions.isNotEmpty() -> {
                        Box(
                            contentAlignment = Alignment.Center
                        ) {
                            IconButton(
                                onClick = {}) {
                                Icon(
                                    imageVector = ImageVector.vectorResource(
                                        R.drawable.download_icon
                                    ),
                                    tint = Color.Black,
                                    contentDescription = null,
                                )
                            }
                        }
                    }
                }
            })
        }) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(
                    innerPadding
                )
                .padding(10.dp)
        ) {
            when {
                state.transactions.isEmpty() -> {
                    NoTransaction()
                }

                else -> {
                    LazyColumn(
                        verticalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        items(10) {
                            TransactionItem()
                        }
                    }
                }
            }
        }
    }
}

@Preview
@Composable
private fun Preview() {
    MyExpensesTheme {
        TransactionsScreen(
            state = DashboardState(), onAction = {}, navController = rememberNavController()
        )
    }
}