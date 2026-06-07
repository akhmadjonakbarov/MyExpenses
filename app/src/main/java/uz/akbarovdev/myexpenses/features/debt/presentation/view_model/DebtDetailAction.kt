package uz.akbarovdev.myexpenses.features.debt.presentation.view_model

import uz.akbarovdev.myexpenses.features.debt.domain.models.DebtTransactionType
import uz.akbarovdev.myexpenses.features.debt.domain.models.DebtTransactionUi

sealed interface DebtDetailAction {
    data class Initialization(val userId: Int) : DebtDetailAction
    data object OnShowAddDialog : DebtDetailAction
    data object OnDismissDialog : DebtDetailAction
    data class OnAmountInputChange(val amount: String) : DebtDetailAction
    data class OnNoteInputChange(val note: String) : DebtDetailAction
    data class OnChangeTransactionType(val type: DebtTransactionType) : DebtDetailAction
    data object OnSaveTransaction : DebtDetailAction
    data class OnSelectEditTransaction(val transaction: DebtTransactionUi) : DebtDetailAction
    data class OnDeleteTransaction(val transaction: DebtTransactionUi) : DebtDetailAction
    data object OnConfirmDelete : DebtDetailAction
    data object OnDismissDelete : DebtDetailAction
    data object OnLoadUser : DebtDetailAction
    data class OnPayTransaction(val transaction: DebtTransactionUi) : DebtDetailAction
    data object OnConfirmPay : DebtDetailAction
    data object OnDismissPay : DebtDetailAction
}
