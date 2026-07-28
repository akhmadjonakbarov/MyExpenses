package uz.akbarovdev.myexpenses.features.debt.presentation.view_model

import uz.akbarovdev.myexpenses.features.debt.domain.models.DebtUserUi

data class DebtListState(
    val isLoading: Boolean = false,
    val error: String? = null,
    val users: List<DebtUserUi> = emptyList(),
    val showCreateDialog: Boolean = false,
    val editingUser: DebtUserUi? = null,
    val nameInput: String = "",
    val phoneInput: String = "",
    val userToDelete: DebtUserUi? = null
)
