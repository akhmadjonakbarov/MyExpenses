package uz.akbarovdev.myexpenses.features.dashboard.presentation


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import org.koin.androidx.compose.koinViewModel
import uz.akbarovdev.myexpenses.R
import uz.akbarovdev.myexpenses.app.navigation.NavigationRoutes
import uz.akbarovdev.myexpenses.features.dashboard.presentation.components.AccountBalance
import uz.akbarovdev.myexpenses.core.design_system.common_components.CreatingTransactionBottomSheetWrapper
import uz.akbarovdev.myexpenses.core.design_system.common_components.NoTransaction
import uz.akbarovdev.myexpenses.features.dashboard.presentation.components.DashboardHeader
import uz.akbarovdev.myexpenses.features.dashboard.presentation.components.DeletableTransactionItem
import uz.akbarovdev.myexpenses.features.dashboard.presentation.components.FloatButton
import uz.akbarovdev.myexpenses.features.dashboard.presentation.components.TransactionInfo
import uz.akbarovdev.myexpenses.features.dashboard.presentation.components.TransactionItem
import uz.akbarovdev.myexpenses.features.dashboard.presentation.components.TransactionList
import uz.akbarovdev.myexpenses.features.dashboard.presentation.view_model.DashboardAction
import uz.akbarovdev.myexpenses.features.dashboard.presentation.view_model.DashboardState
import uz.akbarovdev.myexpenses.features.dashboard.presentation.view_model.DashboardViewModel
import uz.akbarovdev.myexpenses.ui.theme.MyExpensesTheme

@Composable
fun DashboardRoot(
    navController: NavController,
    viewModel: DashboardViewModel = koinViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    DashboardScreen(
        state = state, onAction = viewModel::onAction,
        navController = navController
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DashboardScreen(
    navController: NavController,
    state: DashboardState,
    onAction: (DashboardAction) -> Unit,
) {
    Scaffold(
        floatingActionButton = {
            FloatButton(
                onClick = { onAction(DashboardAction.OpenCreateTransactionBottomSheet(true)) }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(color = Color.Gray)
                .background(
                    brush = Brush.radialGradient(
                        radius = 1000f, colors = listOf(
                            Color(0xFF5A00C8), Color(0xFF24005A)
                        ), center = Offset(250f, 300f)
                    )
                )
        ) {

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(0.9f)

            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 10.dp, vertical = 15.dp),
                    verticalArrangement = Arrangement.SpaceBetween,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    DashboardHeader(
                        goSettingsClick = {
                            navController.navigate(NavigationRoutes.Settings)
                        },
                        exportClick = {
                            onAction(DashboardAction.OnShowExportBottomSheet)
                        }
                    )
                    AccountBalance(
                        balance = state.balance,
                        currencyUi = state.selectedCurrencyUi
                    )
                    TransactionInfo(
                        state = state
                    )
                }
            }

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1.1f)
                    .background(
                        color = MaterialTheme.colorScheme.onPrimary, shape = RoundedCornerShape(
                            topStart = 20.dp, topEnd = 20.dp,
                        )
                    )

            ) {
                Column(
                    Modifier
                        .fillMaxWidth()
                        .padding(
                            top = 10.dp, start = 10.dp, end = 10.dp
                        )
                ) {
                    Row(
                        Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            stringResource(R.string.latest_transactions),
                            style = MaterialTheme.typography.titleLarge
                        )
                        // TODO: if transactions don't exist, hide the button
                        TextButton(
                            onClick = {
                                navController.navigate(NavigationRoutes.Transactions)
                            },
                        ) {
                            Text(
                                stringResource(R.string.show_all),
                                style = MaterialTheme.typography.titleMedium,
                                color = MaterialTheme.colorScheme.primary
                            )
                        }
                    }
                    when {
                        state.transactions.isEmpty() -> {
                            Box(
                                Modifier.fillMaxSize()
                            ) {
                                NoTransaction()
                            }
                        }

                        else -> {
                            Text(
                                stringResource(R.string.today),
                                style = MaterialTheme.typography.bodyLarge,
                            )
                            Spacer(
                                Modifier.height(10.dp)
                            )
                            TransactionList(state, onAction)
                        }
                    }
                }
            }
        }
    }

    if (state.manageCreatingTransactionBottomSheet) {
        CreatingTransactionBottomSheetWrapper(
            onDismiss = {
                onAction(
                    DashboardAction.OpenCreateTransactionBottomSheet(false),
                )
            }, state = state, onAction = onAction
        )
    }
    if (state.exportBottomSheet) {
        ModalBottomSheet(

            sheetState = rememberModalBottomSheetState(
                skipPartiallyExpanded = true
            ),
            onDismissRequest = {
                onAction(DashboardAction.OnShowExportBottomSheet)
            },

            ) {
            Column(
                Modifier
                    .fillMaxWidth()
                    .padding(10.dp)
            ) {
                Column(
                    Modifier.fillMaxWidth()
                ) {
                    Text(
                        "Export",
                        style = MaterialTheme.typography.titleLarge.copy(
                            fontWeight = FontWeight.W600,
                        )
                    )
                    Text(
                        "Export transactions to CSV format",
                        style = MaterialTheme.typography.bodySmall.copy(
                            fontWeight = FontWeight.W400,
                        )
                    )
                }
                Spacer(Modifier.height(8.dp))
                ExposedDropdownMenuBox(expanded = true, onExpandedChange = {}) {

                }
            }
        }
    }
}


@Preview
@Composable
private fun Preview() {
    MyExpensesTheme {
        DashboardScreen(
            state = DashboardState(
                exportBottomSheet = true
            ),
            onAction = {},
            navController = rememberNavController()
        )
    }
}