package uz.akbarovdev.myexpenses.features.preference.presentation.view_model

import uz.akbarovdev.myexpenses.features.preference.domain.models.CurrencyUi

sealed interface PreferenceAction {
    data class OnSelectCurrency(val selectedCurrencyUi: CurrencyUi) : PreferenceAction

}