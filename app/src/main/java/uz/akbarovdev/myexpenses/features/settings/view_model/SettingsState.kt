package uz.akbarovdev.myexpenses.features.settings.view_model

data class SettingsState(
    val paramOne: String = "default",
    val paramTwo: List<String> = emptyList(),
)