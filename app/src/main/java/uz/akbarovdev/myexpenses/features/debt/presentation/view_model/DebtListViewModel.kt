package uz.akbarovdev.myexpenses.features.debt.presentation.view_model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import uz.akbarovdev.myexpenses.features.debt.daos.DebtUserEntity
import uz.akbarovdev.myexpenses.features.debt.domain.models.DebtUserUi
import uz.akbarovdev.myexpenses.features.debt.domain.repositories.DebtRepository

class DebtListViewModel(
    private val debtRepository: DebtRepository
) : ViewModel() {

    private val _state = MutableStateFlow(DebtListState())
    val state: StateFlow<DebtListState> = _state
        .onStart { loadUsers() }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000L), DebtListState())

    private fun loadUsers() {
        viewModelScope.launch {
            debtRepository.getAllUsers().collect { entities ->
                _state.update { it.copy(users = entities.map { it.toUi() }) }
            }
        }
    }

    fun onAction(action: DebtListAction) {
        when (action) {
            DebtListAction.Initialization -> loadUsers()
            DebtListAction.OnShowCreateDialog -> _state.update {
                it.copy(showCreateDialog = true, nameInput = "", phoneInput = "", editingUser = null)
            }
            DebtListAction.OnDismissDialog -> _state.update { it.copy(showCreateDialog = false) }
            is DebtListAction.OnNameInputChange -> _state.update { it.copy(nameInput = action.name) }
            is DebtListAction.OnPhoneInputChange -> _state.update { it.copy(phoneInput = action.phone) }
            DebtListAction.OnSaveUser -> saveUser()
            is DebtListAction.OnSelectEditUser -> _state.update {
                it.copy(
                    showCreateDialog = true,
                    editingUser = action.user,
                    nameInput = action.user.name,
                    phoneInput = action.user.phone
                )
            }
            is DebtListAction.OnDeleteUser -> _state.update { it.copy(userToDelete = action.user) }
            DebtListAction.OnConfirmDelete -> deleteUser()
            DebtListAction.OnDismissDelete -> _state.update { it.copy(userToDelete = null) }
        }
    }

    private fun saveUser() {
        viewModelScope.launch {
            val s = _state.value
            val name = s.nameInput.trim()
            if (name.isEmpty()) return@launch

            val existing = s.editingUser
            if (existing != null) {
                debtRepository.updateUser(
                    DebtUserEntity(
                        id = existing.id,
                        name = name,
                        phone = s.phoneInput.trim().ifEmpty { null },
                        totalAmount = existing.totalAmount,
                        createdAt = existing.createdAt
                    )
                )
            } else {
                debtRepository.insertUser(
                    DebtUserEntity(name = name, phone = s.phoneInput.trim().ifEmpty { null })
                )
            }
            _state.update { it.copy(showCreateDialog = false, editingUser = null) }
        }
    }

    private fun deleteUser() {
        viewModelScope.launch {
            val user = _state.value.userToDelete ?: return@launch
            debtRepository.deleteUser(DebtUserEntity(id = user.id, name = user.name))
            _state.update { it.copy(userToDelete = null) }
        }
    }

    private fun DebtUserEntity.toUi() = DebtUserUi(
        id = id, name = name, phone = phone ?: "", totalAmount = totalAmount, createdAt = createdAt
    )
}
