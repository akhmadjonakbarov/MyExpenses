package uz.akbarovdev.myexpenses.features.dashboard.presentation.view_model

import uz.akbarovdev.myexpenses.core.enums.TransactionType
import uz.akbarovdev.myexpenses.features.dashboard.domain.models.CategoryUi
import uz.akbarovdev.myexpenses.features.dashboard.domain.models.TransactionUi

sealed interface DashboardAction {
    data class OpenCreateTransactionBottomSheet(
        val isVisible: Boolean
    ) : DashboardAction

    data class OnReceiverInputChange(val receiver: String) : DashboardAction
    data class OnAmountInputChange(val amount: String) : DashboardAction
    data class OnNoteInputChange(val note: String) : DashboardAction
    data class OnChangeTransactionType(val transactionType: TransactionType) : DashboardAction
    data object OnCreateTransaction : DashboardAction
    data class OnSelectCategory(val categoryUi: CategoryUi) : DashboardAction
    data object OnShowExportBottomSheet : DashboardAction
    data object Initialization : DashboardAction
    data class OnDeleteTransaction(val transactionUi: TransactionUi) : DashboardAction
}