package uz.akbarovdev.myexpenses.features.preference.presentation.view_model

interface PreferenceEvents {
    data object CurrencySelected : PreferenceEvents
    data class LanguageSelected(val code: String) : PreferenceEvents
    data object ThemeSelected : PreferenceEvents
}

