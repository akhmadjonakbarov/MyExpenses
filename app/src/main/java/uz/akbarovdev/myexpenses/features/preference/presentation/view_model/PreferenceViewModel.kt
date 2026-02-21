package uz.akbarovdev.myexpenses.features.preference.presentation.view_model

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import uz.akbarovdev.myexpenses.core.constants.PrefKeys
import uz.akbarovdev.myexpenses.core.extension.sharedPreferences

class PreferenceViewModel(
    val applicationContext: Context
) : ViewModel() {

    val eventChannel = Channel<PreferenceEvents>()
    val events = eventChannel.receiveAsFlow()

    var symbolOfMoney: String by applicationContext.sharedPreferences(PrefKeys.CURRENCY_SYMBOL)

    private var hasLoadedInitialData = false

    private val _state = MutableStateFlow(PreferenceState())
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
            initialValue = PreferenceState()
        )

    fun onAction(action: PreferenceAction) {
        when (action) {
            is PreferenceAction.OnSelectCurrency -> selectCurrency(action.selectedCurrencyUi.code)
        }
    }

    private fun selectCurrency(currencyCode: String) {
        symbolOfMoney = currencyCode
        viewModelScope.launch {
            eventChannel.send(PreferenceEvents.CurrencySelected)
        }

    }

}