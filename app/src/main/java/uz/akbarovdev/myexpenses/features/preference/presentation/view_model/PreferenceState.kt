package uz.akbarovdev.myexpenses.features.preference.presentation.view_model

import uz.akbarovdev.myexpenses.features.preference.domain.models.CurrencyUi

data class PreferenceState(
    val selectedCurrencyUi: CurrencyUi = CurrencyUi.UZS,
)