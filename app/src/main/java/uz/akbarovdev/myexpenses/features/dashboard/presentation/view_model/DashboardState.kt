package uz.akbarovdev.myexpenses.features.dashboard.presentation.view_model

import uz.akbarovdev.myexpenses.core.enums.TransactionType

data class DashboardState(
    val manageCreatingTransactionBottomSheet: Boolean = false,
    val transactionType: TransactionType = TransactionType.Expense,
    val receiverText: String = "",
    val noteText: String = "",
    val amountText: String = ""
)