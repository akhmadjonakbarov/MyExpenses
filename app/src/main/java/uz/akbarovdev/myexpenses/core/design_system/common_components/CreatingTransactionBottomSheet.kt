package uz.akbarovdev.myexpenses.core.design_system.common_components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import uz.akbarovdev.myexpenses.R
import uz.akbarovdev.myexpenses.core.design_system.buttons.PrimaryButton
import uz.akbarovdev.myexpenses.core.design_system.text_fields.TransparentBasicTextField
import uz.akbarovdev.myexpenses.core.enums.TransactionType
import uz.akbarovdev.myexpenses.features.dashboard.presentation.components.AmountInputTextField
import uz.akbarovdev.myexpenses.features.dashboard.presentation.components.CategoryDropdown
import uz.akbarovdev.myexpenses.features.dashboard.presentation.view_model.DashboardAction
import uz.akbarovdev.myexpenses.features.dashboard.presentation.view_model.DashboardState
import uz.akbarovdev.myexpenses.ui.theme.OnPrimaryFixed


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreatingTransactionBottomSheetWrapper(
    state: DashboardState,
    onAction: (DashboardAction) -> Unit,
    onDismiss: () -> Unit,
) {

    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)

    ModalBottomSheet(
        sheetState = sheetState,
        onDismissRequest = onDismiss,
    ) {
        CreatingTransactionBottomSheet(
            onDismiss = onDismiss,
            state = state,
            onAction = onAction,
        )
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreatingTransactionBottomSheet(
    modifier: Modifier = Modifier,
    onDismiss: () -> Unit,
    state: DashboardState,
    onAction: (DashboardAction) -> Unit,
) {


    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(10.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                stringResource(R.string.add_new_transaction),
                style = MaterialTheme.typography.titleLarge
            )
            IconButton(
                onClick = onDismiss,
            ) {
                Icon(
                    imageVector = Icons.Default.Close, contentDescription = "Close Icon",
                )
            }
        }
        Row(
            Modifier
                .fillMaxWidth()
                .background(
                    color = MaterialTheme.colorScheme.primary.copy(
                        0.08f,
                    ),
                    shape = RoundedCornerShape(
                        16.dp,
                    )
                )
                .padding(
                    vertical = 2.dp, horizontal = 4.dp,
                )
        ) {
            TabButton(
                modifier = Modifier.weight(0.5f),
                onClick = {
                    onAction(
                        DashboardAction.OnChangeTransactionType(
                            TransactionType.Expense
                        )
                    )
                },
                text = stringResource(R.string.expense),
                isSelected = state.transactionType == TransactionType.Expense,
                transactionType = TransactionType.Expense
            )
            Spacer(Modifier.width(5.dp))
            TabButton(
                modifier = Modifier.weight(0.5f),
                onClick = {
                    onAction(
                        DashboardAction.OnChangeTransactionType(
                            TransactionType.Income
                        )
                    )
                },
                text = stringResource(R.string.income),
                isSelected = state.transactionType == TransactionType.Income,
                transactionType = TransactionType.Income
            )
        }
        Spacer(
            modifier = Modifier.height(34.dp)
        )
        Column(
            Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(15.dp)
        ) {

            TransparentBasicTextField(
                text = state.receiverText,
                onValueChange = {
                    onAction(
                        DashboardAction.OnReceiverInputChange(
                            it
                        )
                    )
                },
                hintText = if (state.transactionType == TransactionType.Expense) stringResource(R.string.to) else stringResource(
                    R.string.from
                )
            )

            AmountInputTextField(
                state = state,
                onValueChange = {
                    onAction(DashboardAction.OnAmountInputChange(it))
                },
            )

            TransparentBasicTextField(
                hintText = stringResource(R.string.add_note),
                text = state.noteText,
                onValueChange = {
                    onAction(
                        DashboardAction.OnNoteInputChange(
                            it
                        )
                    )
                }
            )
        }

        Spacer(Modifier.height(15.dp))

        CategoryDropdown(
            selectedCategory = state.selectedCategoryUi,
            onCategorySelected = {
                onAction(
                    DashboardAction.OnSelectCategory(it),
                )
            }
        )
        Spacer(Modifier.height(10.dp))
        PrimaryButton(
            modifier = Modifier
                .fillMaxWidth()
                .height(40.dp),
            onClick = {
                onAction(
                    DashboardAction.OnCreateTransaction
                )
            }
        ) {
            Text(stringResource(R.string.save), style = MaterialTheme.typography.titleMedium)
        }
    }
}


@Composable
fun TabButton(
    modifier: Modifier = Modifier,
    isSelected: Boolean,
    onClick: () -> Unit,
    text: String,
    transactionType: TransactionType,
) {
    Button(
        modifier = modifier,
        colors = ButtonDefaults.buttonColors(
            containerColor = if (isSelected) MaterialTheme.colorScheme.onPrimary else Color.Transparent,
        ),
        onClick = onClick, shape = RoundedCornerShape(12.dp),
        contentPadding = PaddingValues(
            vertical = 10.dp
        ),
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Text(
                text, style = MaterialTheme.typography.titleMedium,
                color = if (isSelected) MaterialTheme.colorScheme.primary else OnPrimaryFixed
            )
            Icon(
                imageVector = if (transactionType == TransactionType.Expense) ImageVector.vectorResource(
                    R.drawable.expenses
                ) else ImageVector.vectorResource(
                    R.drawable.income
                ),
                contentDescription = null,
                tint = if (isSelected) MaterialTheme.colorScheme.primary else OnPrimaryFixed
            )
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Preview(
    showBackground = true
)
@Composable
private fun CreatingTransactionBottomSheetPreview() {
    CreatingTransactionBottomSheet(
        onDismiss = {}, state = DashboardState(),
        onAction = {}

    )
}