package uz.akbarovdev.myexpenses.features.dashboard.presentation.view_model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update

class DashboardViewModel : ViewModel() {

    private var hasLoadedInitialData = false

    private val _state = MutableStateFlow(DashboardState())
    val state = _state
        .onStart {
            if (!hasLoadedInitialData) {
                /** Load initial data here **/
                hasLoadedInitialData = true
            }
        }
        .stateIn(
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

            DashboardAction.OnCreateTransaction -> createTransaction()
            is DashboardAction.OnNoteInputChange -> {
                _state.update { it.copy(noteText = action.note) }
            }

            is DashboardAction.OnReceiverInputChange -> {
                _state.update { it.copy(receiverText = action.receiver) }
            }
        }
    }


    private fun createTransaction() {

    }

}