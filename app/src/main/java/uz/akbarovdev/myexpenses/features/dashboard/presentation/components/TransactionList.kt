package uz.akbarovdev.myexpenses.features.dashboard.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import uz.akbarovdev.myexpenses.features.dashboard.presentation.view_model.DashboardAction
import uz.akbarovdev.myexpenses.features.dashboard.presentation.view_model.DashboardState

@Composable
fun TransactionList(
    state: DashboardState,
    onAction: (DashboardAction) -> Unit
) {
    LazyColumn(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(state.transactions) { transactionUi ->
            DeletableTransactionItem(
                transactionUi = transactionUi,
                currencyUi = state.selectedCurrencyUi,
                state = state,
                onAction = onAction,
                onDelete = {
                    onAction(
                        DashboardAction.OnDeleteTransaction(
                            transactionUi
                        )
                    )
                }
            )
        }
    }
}