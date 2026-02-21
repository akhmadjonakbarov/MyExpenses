package uz.akbarovdev.myexpenses.features.dashboard.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SwipeToDismissBox
import androidx.compose.material3.SwipeToDismissBoxValue
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberSwipeToDismissBoxState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch
import uz.akbarovdev.myexpenses.core.design_system.common_components.IconLabelBox
import uz.akbarovdev.myexpenses.features.dashboard.domain.models.CategoryUi
import uz.akbarovdev.myexpenses.features.dashboard.domain.models.TransactionUi
import uz.akbarovdev.myexpenses.features.preference.domain.models.CurrencyUi
import uz.akbarovdev.myexpenses.ui.theme.Success
import java.text.NumberFormat
import java.util.Locale

@Composable
fun DeletableTransactionItem(
    transactionUi: TransactionUi,
    currencyUi: CurrencyUi,
    onDelete: () -> Unit
) {
    var showDialog by remember { mutableStateOf(false) }
    val scope = rememberCoroutineScope()

    val dismissState = rememberSwipeToDismissBoxState(
        confirmValueChange = { value ->
            if (value == SwipeToDismissBoxValue.EndToStart) {
                showDialog = true
                false
            } else {
                false
            }
        }
    )


    if (showDialog) {
        AlertDialog(
            onDismissRequest = {
                showDialog = false
                scope.launch { dismissState.reset() }
            },
            title = { Text("Delete Transaction") },
            text = { Text("Are you sure you want to delete this transaction for ${transactionUi.receiver}?") },
            confirmButton = {
                TextButton(onClick = {
                    onDelete()
                    showDialog = false
                }) {
                    Text("Delete", color = MaterialTheme.colorScheme.error)
                }
            },
            dismissButton = {
                TextButton(onClick = {
                    showDialog = false
                    scope.launch { dismissState.reset() }
                }) {
                    Text("Cancel")
                }
            }
        )
    }

    SwipeToDismissBox(
        state = dismissState,
        enableDismissFromStartToEnd = false,
        backgroundContent = {
            val color = if (dismissState.dismissDirection == SwipeToDismissBoxValue.EndToStart) {
                MaterialTheme.colorScheme.errorContainer
            } else Color.Transparent

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(color, shape = RoundedCornerShape(12.dp))
                    .padding(horizontal = 20.dp),
                contentAlignment = Alignment.CenterEnd
            ) {
                Icon(Icons.Default.Delete, "Delete", tint = MaterialTheme.colorScheme.error)
            }
        }
    ) {
        TransactionItem(
            modifier = Modifier.background(MaterialTheme.colorScheme.surface),
            transactionUi = transactionUi,
            currencyUi = currencyUi
        )
    }
}


@Composable
fun TransactionItem(
    modifier: Modifier = Modifier,
    transactionUi: TransactionUi,
    currencyUi: CurrencyUi = CurrencyUi.UZS,
    onDeleteClick: () -> Unit = {},
    onEditClick: () -> Unit = {}
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .clickable { onEditClick() } // Clicking the row triggers edit
            .padding(8.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Left Side: Icon and Details
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.weight(1f) // Takes up available space
        ) {
            IconLabelBox(
                modifier = Modifier.size(45.dp),
                iconString = transactionUi.icon.emoji,
                iconSize = 30.dp
            )
            Column {
                Text(
                    text = transactionUi.receiver,
                    style = MaterialTheme.typography.titleMedium,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    text = transactionUi.icon.label,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }

        // Right Side: Amount and Delete Button
        Amount(transactionUi.type, transactionUi.amount, currencyUi)

    }
}

@Composable
fun Amount(type: String, amount: Double, currencyUi: CurrencyUi) {
    // 1. Get a Number instance instead of a Currency instance
    val formatter = NumberFormat.getNumberInstance(Locale.getDefault()).apply {
        minimumFractionDigits = 2
        maximumFractionDigits = 2
    }

    val formattedNumber = formatter.format(amount)

    // 2. Add your custom prefix
    val displayValue = if (type == "expense") "-$formattedNumber" else "+$formattedNumber"

    Text(
        text = "$displayValue ${currencyUi.code}",
        style = MaterialTheme.typography.titleMedium,
        color = if (type == "expense") MaterialTheme.colorScheme.error else Success,
        fontWeight = FontWeight.W600
    )
}


@Preview(
    showBackground = true
)
@Composable
private fun TransactionPreview() {
    TransactionItem(
        modifier = Modifier
            .fillMaxWidth()
            .padding(5.dp), onDeleteClick = {},
        transactionUi = TransactionUi(
            amount = 25.25,
            icon = CategoryUi.ENTERTAINMENT,
            note = "for debt",
            type = "income",
            receiver = "amazon",
            id = 1
        )
    )
}