package uz.akbarovdev.myexpenses.features.dashboard.presentation

import androidx.compose.animation.AnimatedVisibility
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import uz.akbarovdev.myexpenses.features.dashboard.presentation.components.AccountBalance
import uz.akbarovdev.myexpenses.features.dashboard.presentation.components.CreatingTransactionBottomSheetWrapper
import uz.akbarovdev.myexpenses.features.dashboard.presentation.components.DashboardHeader
import uz.akbarovdev.myexpenses.features.dashboard.presentation.components.FloatButton
import uz.akbarovdev.myexpenses.features.dashboard.presentation.components.TransactionInfo
import uz.akbarovdev.myexpenses.features.dashboard.presentation.components.TransactionItem
import uz.akbarovdev.myexpenses.features.dashboard.presentation.view_model.DashboardAction
import uz.akbarovdev.myexpenses.features.dashboard.presentation.view_model.DashboardState
import uz.akbarovdev.myexpenses.features.dashboard.presentation.view_model.DashboardViewModel
import uz.akbarovdev.myexpenses.ui.theme.MyExpensesTheme

@Composable
fun DashboardRoot(
    viewModel: DashboardViewModel = viewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    DashboardScreen(
        state = state, onAction = viewModel::onAction
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DashboardScreen(
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
                    DashboardHeader()
                    AccountBalance()
                    TransactionInfo()
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
                            "Latest Transaction", style = MaterialTheme.typography.titleLarge
                        )
                        TextButton(
                            onClick = {}) {
                            Text(
                                "Show all",
                                style = MaterialTheme.typography.titleMedium,
                                color = MaterialTheme.colorScheme.primary
                            )
                        }
                    }
                    Text(
                        "Today",
                        style = MaterialTheme.typography.bodyLarge,
                    )
                    Spacer(
                        Modifier.height(10.dp)
                    )
                    LazyColumn(
                        modifier = Modifier.fillMaxWidth(),
                        verticalArrangement = Arrangement.spacedBy(8.dp) // ⬅️ space between items
                    ) {
                        items(10) {
                            TransactionItem()
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
}


@Preview
@Composable
private fun Preview() {
    MyExpensesTheme {
        DashboardScreen(
            state = DashboardState(
                manageCreatingTransactionBottomSheet = false
            ), onAction = {})
    }
}