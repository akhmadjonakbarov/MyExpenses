package uz.akbarovdev.myexpenses.features.preference.presentation.view_model

import uz.akbarovdev.myexpenses.features.preference.domain.models.CurrencyUi
import uz.akbarovdev.myexpenses.features.preference.domain.models.LanguageUi

sealed interface PreferenceAction {
    data class OnSelectCurrency(val selectedCurrencyUi: CurrencyUi) : PreferenceAction
    data class OnSelectLanguage(val selectedLanguageUi: LanguageUi) : PreferenceAction


}