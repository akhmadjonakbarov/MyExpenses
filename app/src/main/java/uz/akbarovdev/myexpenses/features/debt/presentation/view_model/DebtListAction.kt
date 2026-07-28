package uz.akbarovdev.myexpenses.features.debt.presentation.view_model

import uz.akbarovdev.myexpenses.features.debt.domain.models.DebtUserUi

sealed interface DebtListAction {
    data object Initialization : DebtListAction
    data object OnShowCreateDialog : DebtListAction
    data object OnDismissDialog : DebtListAction
    data class OnNameInputChange(val name: String) : DebtListAction
    data class OnPhoneInputChange(val phone: String) : DebtListAction
    data object OnSaveUser : DebtListAction
    data class OnSelectEditUser(val user: DebtUserUi) : DebtListAction
    data class OnDeleteUser(val user: DebtUserUi) : DebtListAction
    data object OnConfirmDelete : DebtListAction
    data object OnDismissDelete : DebtListAction
    data object DismissError : DebtListAction
}
