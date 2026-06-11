package uz.akbarovdev.myexpenses.features.debt.presentation.view_model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import uz.akbarovdev.myexpenses.features.debt.daos.DebtTransactionEntity
import uz.akbarovdev.myexpenses.features.debt.daos.DebtUserEntity
import uz.akbarovdev.myexpenses.features.debt.domain.models.DebtTransactionType
import uz.akbarovdev.myexpenses.features.debt.domain.models.DebtTransactionUi
import uz.akbarovdev.myexpenses.features.debt.domain.models.DebtUserUi
import uz.akbarovdev.myexpenses.features.debt.domain.repositories.DebtRepository

class DebtDetailViewModel(
    private val debtRepository: DebtRepository
) : ViewModel() {

    private val _state = MutableStateFlow(DebtDetailState())
    val state: StateFlow<DebtDetailState> = _state
        .onStart { /* initialized via action */ }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000L), DebtDetailState())

    fun onAction(action: DebtDetailAction) {
        when (action) {
            is DebtDetailAction.Initialization -> loadData(action.userId)
            DebtDetailAction.OnShowAddDialog -> _state.update {
                it.copy(showAddDialog = true, amountInput = "", noteInput = "", transactionType = DebtTransactionType.GAVE, editingTransaction = null)
            }
            DebtDetailAction.OnDismissDialog -> _state.update { it.copy(showAddDialog = false) }
            is DebtDetailAction.OnAmountInputChange -> _state.update { it.copy(amountInput = action.amount) }
            is DebtDetailAction.OnNoteInputChange -> _state.update { it.copy(noteInput = action.note) }
            is DebtDetailAction.OnChangeTransactionType -> _state.update { it.copy(transactionType = action.type) }
            DebtDetailAction.OnSaveTransaction -> saveTransaction()
            is DebtDetailAction.OnSelectEditTransaction -> {
                val t = action.transaction
                _state.update {
                    it.copy(
                        showAddDialog = true,
                        editingTransaction = t,
                        amountInput = t.amount.toString(),
                        noteInput = t.note,
                        transactionType = t.type
                    )
                }
            }
            is DebtDetailAction.OnDeleteTransaction -> _state.update { it.copy(transactionToDelete = action.transaction) }
            DebtDetailAction.OnConfirmDelete -> deleteTransaction()
            DebtDetailAction.OnDismissDelete -> _state.update { it.copy(transactionToDelete = null) }
            DebtDetailAction.OnLoadUser -> loadUser()
            is DebtDetailAction.OnPayTransaction -> _state.update {
                it.copy(showPayConfirm = true, payingTransaction = action.transaction)
            }
            DebtDetailAction.OnConfirmPay -> payTransaction()
            DebtDetailAction.OnDismissPay -> _state.update { it.copy(showPayConfirm = false, payingTransaction = null) }
        }
    }

    private var currentUserId: Int = 0

    private fun loadData(userId: Int) {
        currentUserId = userId
        loadUser()
        viewModelScope.launch {
            debtRepository.getTransactionsByUserId(userId).collect { entities ->
                _state.update { it.copy(transactions = entities.map { e ->
                    DebtTransactionUi(
                        id = e.id,
                        userId = e.userId,
                        amount = e.amount,
                        type = if (e.type == DebtTransactionType.GAVE.name) DebtTransactionType.GAVE else DebtTransactionType.TOOK,
                        note = e.note ?: "",
                        isPaid = e.isPaid,
                        createdAt = e.createdAt
                    )
                })}
            }
        }
    }

    private fun loadUser() {
        viewModelScope.launch {
            val entity = debtRepository.getUserById(currentUserId) ?: return@launch
            _state.update { it.copy(user = DebtUserUi(entity.id, entity.name, entity.phone ?: "", entity.totalAmount, entity.createdAt)) }
        }
    }

    private fun saveTransaction() {
        viewModelScope.launch {
            val s = _state.value
            val amount = s.amountInput.toDoubleOrNull() ?: return@launch
            val type = s.transactionType.name

            val existing = s.editingTransaction
            if (existing != null) {
                val oldEntity = debtRepository.getUserById(currentUserId) ?: return@launch
                val oldAmountDiff = if (existing.type == DebtTransactionType.GAVE) existing.amount else -existing.amount
                val newAmountDiff = if (s.transactionType == DebtTransactionType.GAVE) amount else -amount
                val updatedTotal = oldEntity.totalAmount - oldAmountDiff + newAmountDiff

                debtRepository.updateUser(oldEntity.copy(totalAmount = updatedTotal))
                debtRepository.updateTransaction(
                    DebtTransactionEntity(
                        id = existing.id, userId = currentUserId, amount = amount, type = type,
                        note = s.noteInput.trim().ifEmpty { null }, isPaid = existing.isPaid
                    )
                )
            } else {
                val user = debtRepository.getUserById(currentUserId) ?: return@launch
                val diff = if (type == DebtTransactionType.GAVE.name) amount else -amount
                debtRepository.updateUser(user.copy(totalAmount = user.totalAmount + diff))
                debtRepository.insertTransaction(
                    DebtTransactionEntity(
                        userId = currentUserId, amount = amount, type = type,
                        note = s.noteInput.trim().ifEmpty { null }
                    )
                )
            }
            _state.update { it.copy(showAddDialog = false, editingTransaction = null) }
            loadUser()
        }
    }

    private fun deleteTransaction() {
        viewModelScope.launch {
            val t = _state.value.transactionToDelete ?: return@launch
            val user = debtRepository.getUserById(currentUserId) ?: return@launch
            val diff = if (t.type == DebtTransactionType.GAVE) -t.amount else t.amount
            debtRepository.updateUser(user.copy(totalAmount = user.totalAmount + diff))
            debtRepository.deleteTransaction(
                DebtTransactionEntity(id = t.id, userId = currentUserId, amount = t.amount, type = t.type.name)
            )
            _state.update { it.copy(transactionToDelete = null) }
            loadUser()
        }
    }

    private fun payTransaction() {
        viewModelScope.launch {
            val t = _state.value.payingTransaction ?: return@launch
            if (t.isPaid) return@launch

            val user = debtRepository.getUserById(currentUserId) ?: return@launch
            val diff = if (t.type == DebtTransactionType.GAVE) -t.amount else t.amount
            debtRepository.updateUser(user.copy(totalAmount = user.totalAmount + diff))
            debtRepository.updateTransaction(
                DebtTransactionEntity(id = t.id, userId = currentUserId, amount = t.amount, type = t.type.name, isPaid = true)
            )
            _state.update { it.copy(showPayConfirm = false, payingTransaction = null) }
            loadUser()
        }
    }
}
