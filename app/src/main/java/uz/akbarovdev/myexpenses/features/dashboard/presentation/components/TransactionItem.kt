package uz.akbarovdev.myexpenses.features.dashboard.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.rounded.Delete
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
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
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.DialogProperties
import kotlinx.coroutines.launch
import uz.akbarovdev.myexpenses.R
import uz.akbarovdev.myexpenses.core.design_system.common_components.IconLabelBox
import uz.akbarovdev.myexpenses.core.enums.TransactionType
import uz.akbarovdev.myexpenses.core.formatters.CurrencyFormatter
import uz.akbarovdev.myexpenses.features.dashboard.domain.models.CategoryUi
import uz.akbarovdev.myexpenses.features.dashboard.domain.models.TransactionUi
import uz.akbarovdev.myexpenses.features.dashboard.presentation.view_model.DashboardAction
import uz.akbarovdev.myexpenses.features.dashboard.presentation.view_model.DashboardState
import uz.akbarovdev.myexpenses.features.preference.domain.models.CurrencyUi
import uz.akbarovdev.myexpenses.ui.theme.Success


//@Composable
//fun DeletableTransactionItem(
//    transactionUi: TransactionUi,
//    currencyUi: CurrencyUi,
//    onDelete: () -> Unit
//) {
//    var showDialog by remember { mutableStateOf(false) }
//    val scope = rememberCoroutineScope()
//
//    val dismissState = rememberSwipeToDismissBoxState(
//        confirmValueChange = { value ->
//            if (value == SwipeToDismissBoxValue.EndToStart) {
//                showDialog = true
//                false
//            } else {
//                false
//            }
//        }
//    )
//
//
//    if (showDialog) {
//        AlertDialog(
//            onDismissRequest = {
//                showDialog = false
//                scope.launch { dismissState.reset() }
//            },
//            icon = {
//
//                Icon(
//                    imageVector = Icons.Rounded.Delete,
//                    contentDescription = null,
//                    tint = MaterialTheme.colorScheme.primary,
//                    modifier = Modifier.size(32.dp)
//                )
//            },
//            title = {
//                Text(
//                    text = stringResource(R.string.delete_transaction),
//                    style = MaterialTheme.typography.headlineSmall,
//                    fontWeight = FontWeight.Bold,
//                    color = MaterialTheme.colorScheme.onSurface,
//                    textAlign = TextAlign.Center,
//                    modifier = Modifier.fillMaxWidth()
//                )
//            },
//            text = {
//                Text(
//                    text = stringResource(R.string.confirm_delete),
//                    style = MaterialTheme.typography.bodyMedium,
//                    color = MaterialTheme.colorScheme.onSurfaceVariant, // Your #44474B
//                    textAlign = TextAlign.Center,
//                    modifier = Modifier.fillMaxWidth()
//                )
//            },
//            confirmButton = {
//
//                Button(
//                    onClick = {
//                        onDelete()
//                        showDialog = false
//                    },
//                    colors = ButtonDefaults.buttonColors(
//                        containerColor = MaterialTheme.colorScheme.primary,
//                        contentColor = MaterialTheme.colorScheme.onPrimary
//                    ),
//                    shape = RoundedCornerShape(16.dp),
//
//                    ) {
//                    Text(stringResource(R.string.delete), fontWeight = FontWeight.SemiBold)
//                }
//            },
//            dismissButton = {
//                // Using your SurfaceContainerLow for a subtle "Cancel" button
//                FilledTonalButton(
//                    onClick = {
//                        showDialog = false
//                        scope.launch { dismissState.reset() }
//                    },
//                    colors = ButtonDefaults.filledTonalButtonColors(
//                        containerColor = MaterialTheme.colorScheme.surfaceContainerLow,
//                        contentColor = MaterialTheme.colorScheme.onSurfaceVariant
//                    ),
//                    shape = RoundedCornerShape(16.dp),
//
//                    ) {
//                    Text(stringResource(R.string.cancel))
//                }
//            },
//            // Modern M3 Styling
//            shape = RoundedCornerShape(28.dp),
//            containerColor = MaterialTheme.colorScheme.surfaceContainerLowest, // Pure White #FFFFFF
//            tonalElevation = 0.dp // We use your explicit surface colors instead of elevation tints
//        )
//    }
//
//    SwipeToDismissBox(
//        state = dismissState,
//        enableDismissFromStartToEnd = false,
//        backgroundContent = {
//            val color = if (dismissState.dismissDirection == SwipeToDismissBoxValue.EndToStart) {
//                MaterialTheme.colorScheme.errorContainer
//            } else Color.Transparent
//
//            Box(
//                modifier = Modifier
//                    .fillMaxSize()
//                    .background(color, shape = RoundedCornerShape(12.dp))
//                    .padding(horizontal = 20.dp),
//                contentAlignment = Alignment.CenterEnd
//            ) {
//                Icon(Icons.Default.Delete, "Delete", tint = MaterialTheme.colorScheme.error)
//            }
//        }
//    ) {
//        TransactionItem(
//            modifier = Modifier.background(MaterialTheme.colorScheme.surface),
//            transactionUi = transactionUi,
//            currencyUi = currencyUi
//        )
//    }
//}

@Composable
fun DeletableTransactionItem(
    transactionUi: TransactionUi,
    currencyUi: CurrencyUi,
    state: DashboardState,
    onAction: (DashboardAction) -> Unit,
    onDelete: () -> Unit
) {
    var showDeleteDialog by remember { mutableStateOf(false) }
    var showEditDialog by remember { mutableStateOf(false) }
    val scope = rememberCoroutineScope()

    val dismissState = rememberSwipeToDismissBoxState(
        confirmValueChange = { value ->
            if (value == SwipeToDismissBoxValue.EndToStart) {
                showDeleteDialog = true
                false
            } else {
                false
            }
        }
    )

    // --- DELETE CONFIRMATION DIALOG ---
    if (showDeleteDialog) {
        AlertDialog(
            onDismissRequest = {
                showDeleteDialog = false
                scope.launch { dismissState.reset() }
            },
            icon = {
                Icon(
                    imageVector = Icons.Rounded.Delete,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.size(32.dp)
                )
            },
            title = {
                Text(
                    text = stringResource(R.string.delete_transaction),
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                )
            },
            text = {
                Text(
                    text = stringResource(R.string.confirm_delete),
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                )
            },
            confirmButton = {
                Button(
                    onClick = {
                        onDelete()
                        showDeleteDialog = false
                    },
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary),
                    shape = RoundedCornerShape(16.dp)
                ) {
                    Text(stringResource(R.string.delete), fontWeight = FontWeight.Bold)
                }
            },
            dismissButton = {
                TextButton(
                    onClick = {
                        showDeleteDialog = false
                        scope.launch { dismissState.reset() }
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(stringResource(R.string.cancel), color = MaterialTheme.colorScheme.outline)
                }
            },
            shape = RoundedCornerShape(28.dp),
            containerColor = MaterialTheme.colorScheme.surfaceContainerLowest
        )
    }


    if (showEditDialog) {
        AlertDialog(
            onDismissRequest = {
                showEditDialog = false
                onAction(
                    DashboardAction.OnSelectEditTransaction(null)
                )
            },
            properties = DialogProperties(usePlatformDefaultWidth = false),
            modifier = Modifier.fillMaxWidth(0.92f),
            containerColor = MaterialTheme.colorScheme.surfaceContainerLowest,
            shape = RoundedCornerShape(28.dp),
            title = {
                Row(
                    Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        stringResource(R.string.edit_transaction),
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold
                    )
                    IconButton(
                        onClick = {
                            onAction(DashboardAction.OnSelectEditTransaction(null))
                            showEditDialog = false
                        },
                    ) {
                        Icon(Icons.Default.Close, contentDescription = "Close Icon")
                    }
                }
            },
            text = {
                Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
                    OutlinedTextField(
                        value = state.amountText,
                        onValueChange = { onAction(DashboardAction.OnAmountInputChange(it)) },
                        label = { Text("") },
                        prefix = { Text("${currencyUi.code} ") },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(16.dp),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal)
                    )

                    OutlinedTextField(
                        value = state.receiverText,
                        onValueChange = { onAction(DashboardAction.OnReceiverInputChange(it)) },
                        label = { Text(stringResource(R.string.from)) },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(16.dp)
                    )

                    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                        Text(
                            stringResource(R.string.categories),
                            style = MaterialTheme.typography.labelLarge,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                        LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                            items(CategoryUi.entries) { category ->
                                val isSelected = state.selectedCategoryUi == category
                                FilterChip(
                                    selected = isSelected,
                                    onClick = { onAction(DashboardAction.OnSelectCategory(category)) },
                                    label = { Text(stringResource(category.code)) },
                                    leadingIcon = { Text(category.emoji) },
                                    shape = RoundedCornerShape(12.dp),
                                    colors = FilterChipDefaults.filterChipColors(
                                        selectedContainerColor = MaterialTheme.colorScheme.secondaryContainer,
                                        selectedLabelColor = MaterialTheme.colorScheme.onSurface
                                    )
                                )
                            }
                        }
                    }
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        onAction(DashboardAction.OnEditTransaction)
                        showEditDialog = false
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(52.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary),
                    shape = RoundedCornerShape(16.dp)
                ) {
                    Text(stringResource(R.string.save), fontWeight = FontWeight.Bold)
                }
            }
        )
    }

    // --- SWIPE BACKGROUND ---
    SwipeToDismissBox(
        state = dismissState,
        enableDismissFromStartToEnd = false,
        backgroundContent = {
            val isDismissing = dismissState.targetValue == SwipeToDismissBoxValue.EndToStart
            val backgroundColor =
                if (isDismissing) MaterialTheme.colorScheme.errorContainer else Color.Transparent
            val iconColor =
                if (isDismissing) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.outline

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(vertical = 4.dp) // Aligns with item padding
                    .background(backgroundColor, shape = RoundedCornerShape(16.dp))
                    .padding(horizontal = 24.dp),
                contentAlignment = Alignment.CenterEnd
            ) {
                Icon(
                    imageVector = Icons.Rounded.Delete,
                    contentDescription = "Delete",
                    tint = iconColor,
                    modifier = Modifier.size(28.dp)
                )
            }
        }
    ) {
        TransactionItem(
            modifier = Modifier.background(
                MaterialTheme.colorScheme.surface,
                shape = RoundedCornerShape(16.dp)
            ),
            transactionUi = transactionUi,
            currencyUi = currencyUi,
            onEditClick = {
                onAction(DashboardAction.OnSelectEditTransaction(transactionUi))
                showEditDialog = true
            }
        )
    }
}

@Composable
fun TransactionItem(
    modifier: Modifier = Modifier,
    transactionUi: TransactionUi,
    currencyUi: CurrencyUi = CurrencyUi.UZS,

    onEditClick: () -> Unit = {}
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .pointerInput(Unit) {
                detectTapGestures(
                    onLongPress = { _ ->
                        onEditClick()
                    }
                )
            }
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


        Amount(transactionUi.type, transactionUi.amount, currencyUi)

    }
}

@Composable
fun Amount(type: TransactionType, amount: Double, currencyUi: CurrencyUi) {
    val formattedNumber = CurrencyFormatter.format(amount)
    val displayValue =
        if (type == TransactionType.Expense) "-$formattedNumber" else "+$formattedNumber"

    Text(
        text = "$displayValue ${currencyUi.code}",
        style = MaterialTheme.typography.titleMedium,
        color = if (type == TransactionType.Expense) MaterialTheme.colorScheme.error else Success,
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
            .padding(5.dp),
        transactionUi = TransactionUi(
            amount = 25.25,
            icon = CategoryUi.ENTERTAINMENT,
            note = "for debt",
            type = TransactionType.Income,
            receiver = "amazon",
            id = 1
        )
    )
}