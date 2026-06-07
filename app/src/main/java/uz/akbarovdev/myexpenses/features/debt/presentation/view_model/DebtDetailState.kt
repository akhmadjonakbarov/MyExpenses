package uz.akbarovdev.myexpenses.features.debt.presentation.view_model

import uz.akbarovdev.myexpenses.features.debt.domain.models.DebtTransactionType
import uz.akbarovdev.myexpenses.features.debt.domain.models.DebtTransactionUi
import uz.akbarovdev.myexpenses.features.debt.domain.models.DebtUserUi

data class DebtDetailState(
    val user: DebtUserUi? = null,
    val transactions: List<DebtTransactionUi> = emptyList(),
    val showAddDialog: Boolean = false,
    val editingTransaction: DebtTransactionUi? = null,
    val amountInput: String = "",
    val noteInput: String = "",
    val transactionType: DebtTransactionType = DebtTransactionType.GAVE,
    val transactionToDelete: DebtTransactionUi? = null,
    val showPayConfirm: Boolean = false,
    val payingTransaction: DebtTransactionUi? = null
)
