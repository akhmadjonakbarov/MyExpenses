package uz.akbarovdev.myexpenses.features.preference.presentation.view_model

data class PreferenceState(
    val paramOne: String = "default",
    val paramTwo: List<String> = emptyList(),
)