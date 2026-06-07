@file:OptIn(ExperimentalMaterial3Api::class)

package uz.akbarovdev.myexpenses.features.debt.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import org.koin.androidx.compose.koinViewModel
import uz.akbarovdev.myexpenses.R
import uz.akbarovdev.myexpenses.core.design_system.top_bar.Title
import uz.akbarovdev.myexpenses.features.debt.domain.models.DebtTransactionType
import uz.akbarovdev.myexpenses.features.debt.domain.models.DebtTransactionUi
import uz.akbarovdev.myexpenses.features.debt.domain.models.DebtUserUi
import uz.akbarovdev.myexpenses.features.debt.presentation.view_model.DebtDetailAction
import uz.akbarovdev.myexpenses.features.debt.presentation.view_model.DebtDetailState
import uz.akbarovdev.myexpenses.features.debt.presentation.view_model.DebtDetailViewModel
import uz.akbarovdev.myexpenses.ui.theme.MyExpensesTheme
import uz.akbarovdev.myexpenses.ui.theme.Success

@Composable
fun DebtDetailRoot(
    navController: NavController,
    userId: Int,
    viewModel: DebtDetailViewModel = koinViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    androidx.compose.runtime.LaunchedEffect(userId) {
        viewModel.onAction(DebtDetailAction.Initialization(userId))
    }

    DebtDetailScreen(state = state, onAction = viewModel::onAction, navController = navController)
}

@Composable
fun DebtDetailScreen(
    state: DebtDetailState,
    onAction: (DebtDetailAction) -> Unit,
    navController: NavController
) {
    Scaffold(
        containerColor = Color(0xFFF4E8FF),
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color(0xFFF4E8FF)),
                navigationIcon = {
                    IconButton(onClick = { navController.navigateUp() }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = null)
                    }
                },
                title = { Title(title = state.user?.name ?: "Debt Details") }
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { onAction(DebtDetailAction.OnShowAddDialog) },
                containerColor = MaterialTheme.colorScheme.primary
            ) {
                Icon(Icons.Default.Add, contentDescription = null, tint = Color.White)
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(10.dp)
        ) {
            state.user?.let { user ->
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(
                            MaterialTheme.colorScheme.surfaceContainerLowest,
                            RoundedCornerShape(16.dp)
                        )
                        .padding(16.dp)
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            "Total Balance",
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                        Spacer(Modifier.height(4.dp))
                        Text(
                            text = "${if (user.totalAmount >= 0) "+" else ""}${
                                String.format(
                                    "%.0f",
                                    user.totalAmount
                                )
                            }",
                            style = MaterialTheme.typography.headlineLarge,
                            fontWeight = FontWeight.Bold,
                            color = if (user.totalAmount >= 0) Success else MaterialTheme.colorScheme.error
                        )
                        Spacer(Modifier.height(4.dp))
                        Text(
                            if (user.totalAmount >= 0) "${user.name} owes you" else "You owe ${user.name}",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            }

            Spacer(Modifier.height(12.dp))

            if (state.transactions.isEmpty()) {
                Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text(
                        stringResource(R.string.transactions_do_not_exist),
                        style = MaterialTheme.typography.bodyLarge
                    )
                }
            } else {
                LazyColumn(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                    items(state.transactions, key = { it.id }) { transaction ->
                        DebtTransactionItem(
                            transaction = transaction,
                            onEdit = { onAction(DebtDetailAction.OnSelectEditTransaction(transaction)) },
                            onDelete = { onAction(DebtDetailAction.OnDeleteTransaction(transaction)) },
                            onPay = { onAction(DebtDetailAction.OnPayTransaction(transaction)) }
                        )
                    }
                }
            }
        }
    }

    if (state.showAddDialog) {
        DebtTransactionDialog(
            amount = state.amountInput,
            note = state.noteInput,
            transactionType = state.transactionType,
            isEditing = state.editingTransaction != null,
            onAmountChange = { onAction(DebtDetailAction.OnAmountInputChange(it)) },
            onNoteChange = { onAction(DebtDetailAction.OnNoteInputChange(it)) },
            onTypeChange = { onAction(DebtDetailAction.OnChangeTransactionType(it)) },
            onSave = { onAction(DebtDetailAction.OnSaveTransaction) },
            onDismiss = { onAction(DebtDetailAction.OnDismissDialog) }
        )
    }

    if (state.transactionToDelete != null) {
        AlertDialog(
            onDismissRequest = { onAction(DebtDetailAction.OnDismissDelete) },
            icon = {
                Icon(
                    Icons.Default.Delete,
                    null,
                    tint = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.size(32.dp)
                )
            },
            title = {
                Text(
                    "Delete Transaction",
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                )
            },
            text = {
                Text(
                    "Are you sure?",
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                )
            },
            confirmButton = {
                Button(
                    onClick = { onAction(DebtDetailAction.OnConfirmDelete) },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp)
                ) {
                    Text("Delete", fontWeight = FontWeight.Bold)
                }
            },
            dismissButton = {
                TextButton(
                    onClick = { onAction(DebtDetailAction.OnDismissDelete) },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Cancel", color = MaterialTheme.colorScheme.outline)
                }
            },
            shape = RoundedCornerShape(28.dp),
            containerColor = MaterialTheme.colorScheme.surfaceContainerLowest
        )
    }

    if (state.showPayConfirm) {
        AlertDialog(
            onDismissRequest = { onAction(DebtDetailAction.OnDismissPay) },
            icon = {
                Icon(
                    Icons.Default.Check,
                    null,
                    tint = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.size(32.dp)
                )
            },
            title = {
                Text(
                    "Confirm Payment",
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                )
            },
            text = {
                Text(
                    "Do you confirm this payment?",
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                )
            },
            confirmButton = {
                Button(
                    onClick = { onAction(DebtDetailAction.OnConfirmPay) },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp)
                ) {
                    Text("Confirm", fontWeight = FontWeight.Bold)
                }
            },
            dismissButton = {
                TextButton(
                    onClick = { onAction(DebtDetailAction.OnDismissPay) },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Cancel", color = MaterialTheme.colorScheme.outline)
                }
            },
            shape = RoundedCornerShape(28.dp),
            containerColor = MaterialTheme.colorScheme.surfaceContainerLowest
        )
    }
}

@Composable
fun DebtTransactionItem(
    transaction: DebtTransactionUi,
    onEdit: () -> Unit,
    onDelete: () -> Unit,
    onPay: () -> Unit = {}
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.surfaceContainerLowest, RoundedCornerShape(16.dp))
            .padding(12.dp)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .background(
                        if (transaction.type == DebtTransactionType.GAVE) Success.copy(0.15f) else MaterialTheme.colorScheme.error.copy(
                            0.15f
                        ),
                        RoundedCornerShape(10.dp)
                    ),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    if (transaction.type == DebtTransactionType.GAVE) "→" else "←",
                    fontWeight = FontWeight.Bold,
                    color = if (transaction.type == DebtTransactionType.GAVE) Success else MaterialTheme.colorScheme.error
                )
            }
            Spacer(Modifier.width(10.dp))
            Column(Modifier.weight(1f)) {
                Text(
                    if (transaction.type == DebtTransactionType.GAVE) "I gave" else "I took",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.W500
                )
                if (transaction.note.isNotEmpty()) {
                    Text(
                        transaction.note,
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }
            }
            Text(
                text = "${if (transaction.type == DebtTransactionType.GAVE) "-" else "+"}${
                    String.format(
                        "%.0f",
                        transaction.amount
                    )
                }",
                style = MaterialTheme.typography.titleMedium.copy(
                    textDecoration = if (transaction.isPaid) TextDecoration.LineThrough else TextDecoration.None
                ),
                fontWeight = FontWeight.Bold,
                color = if (transaction.isPaid) MaterialTheme.colorScheme.onSurfaceVariant
                else if (transaction.type == DebtTransactionType.GAVE) MaterialTheme.colorScheme.error else Success
            )
            if (transaction.isPaid) {
                Text(
                    "Paid",
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier.padding(start = 4.dp)
                )
            } else {
                IconButton(onClick = onPay, modifier = Modifier.size(32.dp)) {
                    Icon(Icons.Default.Check, null, tint = Success, modifier = Modifier.size(18.dp))
                }
            }
            IconButton(onClick = onEdit, modifier = Modifier.size(32.dp)) {
                Icon(Icons.Default.Edit, null, modifier = Modifier.size(16.dp))
            }
            IconButton(onClick = onDelete, modifier = Modifier.size(32.dp)) {
                Icon(
                    Icons.Default.Delete,
                    null,
                    tint = MaterialTheme.colorScheme.error,
                    modifier = Modifier.size(16.dp)
                )
            }
        }
    }
}

@Composable
fun DebtTransactionDialog(
    amount: String,
    note: String,
    transactionType: DebtTransactionType,
    isEditing: Boolean,
    onAmountChange: (String) -> Unit,
    onNoteChange: (String) -> Unit,
    onTypeChange: (DebtTransactionType) -> Unit,
    onSave: () -> Unit,
    onDismiss: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        containerColor = MaterialTheme.colorScheme.surfaceContainerLowest,
        shape = RoundedCornerShape(28.dp),
        title = {
            Row(
                Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    if (isEditing) "Edit Transaction" else "Add Transaction",
                    fontWeight = FontWeight.Bold
                )
                IconButton(onClick = onDismiss) { Icon(Icons.Default.Close, null) }
            }
        },
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    Button(
                        onClick = { onTypeChange(DebtTransactionType.GAVE) },
                        modifier = Modifier.weight(1f),
                        shape = RoundedCornerShape(12.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = if (transactionType == DebtTransactionType.GAVE) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.primary.copy(
                                0.08f
                            ),
                            contentColor = if (transactionType == DebtTransactionType.GAVE) Color.White else MaterialTheme.colorScheme.primary
                        ),
                        contentPadding = PaddingValues(8.dp)
                    ) { Text("I gave") }
                    Button(
                        onClick = { onTypeChange(DebtTransactionType.TOOK) },
                        modifier = Modifier.weight(1f),
                        shape = RoundedCornerShape(12.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = if (transactionType == DebtTransactionType.TOOK) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.primary.copy(
                                0.08f
                            ),
                            contentColor = if (transactionType == DebtTransactionType.TOOK) Color.White else MaterialTheme.colorScheme.primary
                        ),
                        contentPadding = PaddingValues(8.dp)
                    ) { Text("I took") }
                }
                OutlinedTextField(
                    value = amount, onValueChange = onAmountChange,
                    label = { Text("Amount") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal)
                )
                OutlinedTextField(
                    value = note, onValueChange = onNoteChange,
                    label = { Text("Note (optional)") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp)
                )
            }
        },
        confirmButton = {
            Button(
                onClick = onSave,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp),
                shape = RoundedCornerShape(16.dp)
            ) {
                Text(if (isEditing) "Save" else "Add", fontWeight = FontWeight.Bold)
            }
        }
    )
}

@Preview
@Composable
private fun Preview() {
    MyExpensesTheme {
        DebtDetailScreen(
            state = DebtDetailState(user = DebtUserUi(1, "John", "+998901234567", 150.0)),
            onAction = {},
            navController = rememberNavController()
        )
    }
}
