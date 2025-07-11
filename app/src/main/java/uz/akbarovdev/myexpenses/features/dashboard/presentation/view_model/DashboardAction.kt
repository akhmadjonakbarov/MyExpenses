package uz.akbarovdev.myexpenses.features.dashboard.presentation.view_model

import uz.akbarovdev.myexpenses.core.enums.TransactionType

sealed interface DashboardAction {
    data class OpenCreateTransactionBottomSheet(
        val isVisible: Boolean
    ) : DashboardAction

    data class OnReceiverInputChange(val receiver: String) : DashboardAction
    data class OnAmountInputChange(val amount: String) : DashboardAction
    data class OnNoteInputChange(val note: String) : DashboardAction
    data class OnChangeTransactionType(val transactionType: TransactionType) : DashboardAction
    data object OnCreateTransaction : DashboardAction
}