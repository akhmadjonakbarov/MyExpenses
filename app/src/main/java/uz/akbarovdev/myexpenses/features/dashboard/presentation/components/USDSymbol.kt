package uz.akbarovdev.myexpenses.features.dashboard.presentation.components

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import uz.akbarovdev.myexpenses.core.enums.TransactionType
import uz.akbarovdev.myexpenses.ui.theme.Success

@Composable
fun USDSymbol(
    transactionType: TransactionType
) {
    if (transactionType == TransactionType.Expense)
        Text(
            text = "-$",
            style = MaterialTheme.typography.displayMedium,
            color = MaterialTheme.colorScheme.error
        )
    else
        Text(
            text = "$",
            style = MaterialTheme.typography.displayMedium,
            color = Success
        )
}