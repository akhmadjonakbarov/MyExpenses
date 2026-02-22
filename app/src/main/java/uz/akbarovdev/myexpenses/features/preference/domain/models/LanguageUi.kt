package uz.akbarovdev.myexpenses.features.preference.domain.models


enum class LanguageUi(
    val code: String,
    val label: String,
) {
    English(
        code = "en",
        label = "English"
    ),
    Russian(
        code = "ru",
        label = "Русский"
    ),
    Uzbek(
        "uz",
        "O'zbekcha"
    )
}