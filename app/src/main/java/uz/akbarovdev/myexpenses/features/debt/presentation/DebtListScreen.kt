@file:OptIn(ExperimentalMaterial3Api::class)

package uz.akbarovdev.myexpenses.features.debt.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
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
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Person
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import org.koin.androidx.compose.koinViewModel
import uz.akbarovdev.myexpenses.R
import uz.akbarovdev.myexpenses.app.navigation.NavigationRoutes
import uz.akbarovdev.myexpenses.core.design_system.top_bar.Title
import uz.akbarovdev.myexpenses.core.formatters.CurrencyFormatter
import uz.akbarovdev.myexpenses.features.debt.domain.models.DebtUserUi
import uz.akbarovdev.myexpenses.features.debt.presentation.components.DebtUserDialog
import uz.akbarovdev.myexpenses.features.debt.presentation.view_model.DebtListAction
import uz.akbarovdev.myexpenses.features.debt.presentation.view_model.DebtListState
import uz.akbarovdev.myexpenses.features.debt.presentation.view_model.DebtListViewModel
import uz.akbarovdev.myexpenses.ui.theme.MyExpensesTheme
import uz.akbarovdev.myexpenses.ui.theme.Success

@Composable
fun DebtListRoot(
    navController: NavController,
    viewModel: DebtListViewModel = koinViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    DebtListScreen(state = state, onAction = viewModel::onAction, navController = navController)
}

@Composable
fun DebtListScreen(
    state: DebtListState,
    onAction: (DebtListAction) -> Unit,
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
                title = { Title(title = stringResource(R.string.debt_notebook)) }
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { onAction(DebtListAction.OnShowCreateDialog) },
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
            if (state.users.isEmpty()) {
                Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text(
                        stringResource(R.string.no_debts),
                        style = MaterialTheme.typography.bodyLarge
                    )
                }
            } else {
                LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    items(state.users, key = { it.id }) { user ->
                        DebtUserItem(
                            user = user,
                            onEdit = { onAction(DebtListAction.OnSelectEditUser(user)) },
                            onDelete = { onAction(DebtListAction.OnDeleteUser(user)) },
                            onClick = {
                                navController.navigate(NavigationRoutes.DebtDetail(user.id))
                            }
                        )
                    }
                }
            }
        }
    }

    if (state.showCreateDialog) {
        DebtUserDialog(
            name = state.nameInput,
            phone = state.phoneInput,
            isEditing = state.editingUser != null,
            onNameChange = { onAction(DebtListAction.OnNameInputChange(it)) },
            onPhoneChange = { onAction(DebtListAction.OnPhoneInputChange(it)) },
            onSave = { onAction(DebtListAction.OnSaveUser) },
            onDismiss = { onAction(DebtListAction.OnDismissDialog) }
        )
    }

    if (state.userToDelete != null) {
        AlertDialog(
            onDismissRequest = { onAction(DebtListAction.OnDismissDelete) },
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
                    "Delete User",
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                )
            },
            text = {
                Text(
                    "Are you sure you want to delete ${state.userToDelete.name}?",
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                )
            },
            confirmButton = {
                Button(
                    onClick = { onAction(DebtListAction.OnConfirmDelete) },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp)
                ) {
                    Text("Delete", fontWeight = FontWeight.Bold)
                }
            },
            dismissButton = {
                TextButton(
                    onClick = { onAction(DebtListAction.OnDismissDelete) },
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
fun DebtUserItem(
    user: DebtUserUi,
    onEdit: () -> Unit,
    onDelete: () -> Unit,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.surfaceContainerLowest, RoundedCornerShape(16.dp))
            .clickable(onClick = onClick)
            .padding(12.dp)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Box(
                modifier = Modifier
                    .size(45.dp)
                    .background(
                        MaterialTheme.colorScheme.primaryContainer,
                        RoundedCornerShape(12.dp)
                    ),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    Icons.Default.Person,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onPrimary,
                    modifier = Modifier.size(25.dp)
                )
            }
            Spacer(Modifier.width(12.dp))
            Column(Modifier.weight(1f)) {
                Text(
                    user.name,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.W600
                )
                if (user.phone.isNotEmpty()) {
                    Text(
                        user.phone,
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
            Text(
                text = "${if (user.totalAmount >= 0) "+" else ""}${CurrencyFormatter.format(user.totalAmount)}",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = if (user.totalAmount >= 0) Success else MaterialTheme.colorScheme.error
            )
            IconButton(onClick = onEdit, modifier = Modifier.size(36.dp)) {
                Icon(Icons.Default.Edit, null, modifier = Modifier.size(18.dp))
            }
            IconButton(onClick = onDelete, modifier = Modifier.size(36.dp)) {
                Icon(
                    Icons.Default.Delete,
                    null,
                    tint = MaterialTheme.colorScheme.error,
                    modifier = Modifier.size(18.dp)
                )
            }
        }
    }
}



@Preview
@Composable
private fun Preview() {
    MyExpensesTheme {
        DebtListScreen(
            state = DebtListState(),
            onAction = {},
            navController = rememberNavController()
        )
    }
}
