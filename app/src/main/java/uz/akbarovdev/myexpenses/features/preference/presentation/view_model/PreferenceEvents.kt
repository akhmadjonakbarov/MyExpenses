package uz.akbarovdev.myexpenses.features.preference.presentation.view_model

interface PreferenceEvents {
    data object CurrencySelected : PreferenceEvents
    data object LanguageSelected : PreferenceEvents
    data object ThemeSelected : PreferenceEvents
}

