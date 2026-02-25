package uz.akbarovdev.myexpenses.features.dashboard.presentation.view_model

import android.content.Context
import android.text.format.DateUtils
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import uz.akbarovdev.myexpenses.core.constants.PrefKeys
import uz.akbarovdev.myexpenses.core.enums.TransactionType
import uz.akbarovdev.myexpenses.core.extension.sharedPreferences
import uz.akbarovdev.myexpenses.features.dashboard.daos.balance.BalanceEntity
import uz.akbarovdev.myexpenses.features.dashboard.daos.transaction.TransactionEntity
import uz.akbarovdev.myexpenses.features.dashboard.domain.models.CategoryUi
import uz.akbarovdev.myexpenses.features.dashboard.domain.models.TransactionUi
import uz.akbarovdev.myexpenses.features.dashboard.domain.repositories.BalanceRepository
import uz.akbarovdev.myexpenses.features.dashboard.domain.repositories.TransactionRepository
import uz.akbarovdev.myexpenses.features.preference.domain.models.CurrencyUi

class DashboardViewModel(
    val transactionRepository: TransactionRepository,
    val balanceRepository: BalanceRepository,
    val applicationContext: Context
) : ViewModel() {

    val eventChannel = Channel<DashboardEvents>()
    val events = eventChannel.receiveAsFlow()

    private var hasLoadedInitialData = false

    private val _state = MutableStateFlow(DashboardState())
    val state = _state.onStart {
        if (!hasLoadedInitialData) {
            onAction(DashboardAction.Initialization)
            hasLoadedInitialData = true
        }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000L),
        initialValue = DashboardState()
    )

    fun onAction(action: DashboardAction) {
        when (action) {
            is DashboardAction.OpenCreateTransactionBottomSheet -> {
                _state.update {
                    it.copy(
                        manageCreatingTransactionBottomSheet = action.isVisible,
                    )
                }
            }

            is DashboardAction.OnAmountInputChange -> {
                _state.update { it.copy(amountText = action.amount) }
            }

            is DashboardAction.OnChangeTransactionType -> {
                _state.update {
                    it.copy(transactionType = action.transactionType)
                }
            }

            DashboardAction.OnCreateTransaction -> {
                createTransaction()
            }

            is DashboardAction.OnNoteInputChange -> {
                _state.update { it.copy(noteText = action.note) }
            }

            is DashboardAction.OnReceiverInputChange -> {
                _state.update { it.copy(receiverText = action.receiver) }
            }

            is DashboardAction.OnSelectCategory -> selectCategory(action.categoryUi)
            DashboardAction.OnShowExportBottomSheet -> {
                val visible = state.value.exportBottomSheet
                _state.update {
                    it.copy(
                        exportBottomSheet = !visible
                    )
                }
            }

            is DashboardAction.OnDeleteTransaction -> deleteTransaction(action.transactionUi)
            is DashboardAction.OnExportToExcel -> exportToExcel()
            is DashboardAction.OnManageDeleteConfirmTransaction -> {
                viewModelScope.launch {
                    _state.update { it.copy(showConfirmDeleteTransaction = action.value) }
                }
            }

            DashboardAction.Initialization -> initialization()
            is DashboardAction.OnSelectEditTransaction -> {
                if (action.transactionUi != null) {
                    _state.update {
                        it.copy(
                            editingTransaction = action.transactionUi,
                            amountText = action.transactionUi.amount.toString(),
                            noteText = action.transactionUi.note,
                            receiverText = action.transactionUi.receiver,
                            selectedCategoryUi = action.transactionUi.icon,
                            transactionType = if (action.transactionUi.type == TransactionType.Income) TransactionType.Income else TransactionType.Expense
                        )
                    }
                } else {
                    _state.update {
                        it.copy(
                            amountText = "",
                            noteText = "",
                            receiverText = "",
                            selectedCategoryUi = null,
                            transactionType = TransactionType.Expense,
                            editingTransaction = null
                        )
                    }
                }
            }

            DashboardAction.OnEditTransaction -> editTransaction()
        }
    }

    private fun exportToExcel() {
        viewModelScope.launch {
            val transactions = transactionRepository.getTransactions()
            eventChannel.send(DashboardEvents.OnExportTransactionsDone)
        }
    }

    private fun initialization() {
        val symbolOfCurrency by applicationContext.sharedPreferences(PrefKeys.CURRENCY_SYMBOL)
        viewModelScope.launch {
            getBalances()
            getTransactions()
            _state.update {
                it.copy(selectedCurrencyUi = CurrencyUi.entries.find { currency -> currency.code == symbolOfCurrency }
                    ?: CurrencyUi.UZS)
            }
            findLargestTransaction()
            calculateWeeklyTransaction()
            getDailyTransactions()
        }
    }

    private suspend fun getBalances() {
        val balances = balanceRepository.getAllBalances()
        if (balances.isNotEmpty()) {
            val lastBalance = balances.last()
            _state.update {
                it.copy(
                    balance = lastBalance.amount
                )
            }

        }
    }

    private suspend fun getDailyTransactions() {
        val transactions = transactionRepository.getTransactions()
        val dailyTransactions = transactions.filter {
            DateUtils.isToday(it.createdAt)
        }.sortedByDescending { it.createdAt }.map { transaction ->
            TransactionUi(
                id = transaction.id,
                amount = transaction.amount,
                type = identifyType(transaction.type),
                note = transaction.note ?: "",
                receiver = transaction.receiver ?: "",
                icon = CategoryUi.entries.find { it.name == transaction.category }
                    ?: CategoryUi.ENTERTAINMENT)
        }
        _state.update { it.copy(dailyTransactions = dailyTransactions) }
    }

    private fun identifyType(value: String): TransactionType {
        val transactionType = if (value.equals(
                TransactionType.Income.name, ignoreCase = true
            )
        ) TransactionType.Income else TransactionType.Expense
        return transactionType
    }

    private fun editTransaction() {
        viewModelScope.launch {
            val currentState = state.value
            val editingTransaction = currentState.editingTransaction ?: return@launch

            val newAmount = currentState.amountText.toDoubleOrNull() ?: 0.0
            val oldAmount = editingTransaction.amount

            val balance = getLastBalance()

            val balanceDifference = if (editingTransaction.type == TransactionType.Income) {
                newAmount - oldAmount
            } else {
                oldAmount - newAmount
            }

            balanceRepository.instertBalance(
                balance.copy(amount = balance.amount + balanceDifference)
            )

            val updatedTransaction = TransactionEntity(
                id = editingTransaction.id,
                amount = newAmount,
                note = currentState.noteText,
                receiver = currentState.receiverText,
                type = currentState.transactionType.name,
                category = currentState.selectedCategoryUi?.name
            )

            transactionRepository.createTransaction(updatedTransaction)

            _state.update {
                it.copy(
                    amountText = "",
                    noteText = "",
                    receiverText = "",
                    selectedCategoryUi = null,
                    editingTransaction = null,

                    )
            }
            initialization()
        }
    }


    private fun createTransaction() {

        viewModelScope.launch {
            val currentState = state.value
            val amount = currentState.amountText.toDoubleOrNull() ?: 0.0
            val note = currentState.noteText
            val receiver = currentState.receiverText
            val transactionType = currentState.transactionType
            val selectedCategory = currentState.selectedCategoryUi?.name
            if (transactionType == TransactionType.Income) {
                val balances = balanceRepository.getAllBalances()
                if (balances.isNotEmpty()) {
                    val lastBalance = getLastBalance()
                    val updatedBalance = lastBalance.copy(
                        amount = lastBalance.amount + amount
                    )
                    balanceRepository.instertBalance(updatedBalance)
                } else {
                    val balance = BalanceEntity(
                        amount = amount
                    )
                    balanceRepository.instertBalance(balance)
                }
            } else {
                val balances = balanceRepository.getAllBalances()
                if (balances.isNotEmpty()) {
                    val lastBalance = getLastBalance()
                    val updatedBalance = lastBalance.copy(
                        amount = lastBalance.amount - amount
                    )
                    balanceRepository.instertBalance(updatedBalance)
                }
            }

            val transaction = TransactionEntity(
                id = currentState.editingTransaction?.id ?: 0,
                amount = amount,
                note = note,
                receiver = receiver,
                type = transactionType.name,
                category = selectedCategory
            )
            transactionRepository.createTransaction(transaction)

            _state.update {
                it.copy(
                    amountText = "",
                    noteText = "",
                    receiverText = "",
                    manageCreatingTransactionBottomSheet = false,
                )
            }
            initialization()
        }

    }

    private suspend fun getTransactions() {
        val transactions = transactionRepository.getTransactions()
        val modifiedTransaction = mutableListOf<TransactionUi>()
        transactions.sortedByDescending { it.createdAt }.forEach { transaction ->
            val transactionUi = TransactionUi(
                id = transaction.id,
                amount = transaction.amount,
                type = identifyType(transaction.type),
                note = transaction.note ?: "",
                receiver = transaction.receiver ?: "",
                icon = CategoryUi.entries.find { it.name == transaction.category }
                    ?: CategoryUi.ENTERTAINMENT)
            modifiedTransaction.add(transactionUi)
        }
        _state.update {
            it.copy(
                transactions = modifiedTransaction
            )
        }
    }


    private fun selectCategory(categoryUi: CategoryUi) {
        _state.update {
            it.copy(
                selectedCategoryUi = categoryUi
            )
        }
    }

    private fun deleteTransaction(transactionUi: TransactionUi) {
        viewModelScope.launch {
            val balance = getLastBalance()

            val transactionEntity = TransactionEntity(
                id = transactionUi.id,
                amount = transactionUi.amount,
                type = transactionUi.type.name,
                note = transactionUi.note,
                receiver = transactionUi.receiver,
                category = transactionUi.icon.name
            )
            if (transactionUi.type == TransactionType.Income) {
                balanceRepository.instertBalance(
                    balance.copy(
                        amount = balance.amount - transactionUi.amount
                    )
                )
            } else {
                balanceRepository.instertBalance(
                    balance.copy(
                        amount = balance.amount + transactionUi.amount
                    )
                )
            }



            transactionRepository.deleteTransaction(transactionEntity)
            initialization()
        }
    }


    private suspend fun getLastBalance(): BalanceEntity {
        val balances = balanceRepository.getAllBalances()
        return balances.last()
    }

    private suspend fun findLargestTransaction() {
        val transactions = transactionRepository.getTransactions()
        val largestTransaction = transactions.filter { it.type == TransactionType.Expense.name }
            .maxByOrNull { it.amount }
        if (largestTransaction != null) {
            val transactionUi = TransactionUi(
                id = largestTransaction.id,
                amount = largestTransaction.amount,
                type = identifyType(largestTransaction.type),
                note = largestTransaction.note ?: "",
                receiver = largestTransaction.receiver ?: "",
                icon = CategoryUi.entries.find { it.name == largestTransaction.category }
                    ?: CategoryUi.ENTERTAINMENT)
            _state.update { it.copy(largestTransactionUi = transactionUi) }
        }
    }

    private suspend fun calculateWeeklyTransaction() {
        val sevenDaysInMillis = 7 * 24 * 60 * 60 * 1000L
        val currentTime = System.currentTimeMillis()
        val cutoffTime = currentTime - sevenDaysInMillis

        val transactions = transactionRepository.getTransactions()


        val totalWeeklyTransaction =
            transactions.filter { it.type == TransactionType.Expense.name && it.createdAt >= cutoffTime }
                .sumOf { it.amount }

        _state.update { it.copy(totalPreviewWeekTransaction = totalWeeklyTransaction) }
    }

    companion object {
        val TAG = "DashboardViewModel"
    }

}