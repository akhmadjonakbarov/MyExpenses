package uz.akbarovdev.myexpenses.features.preference.domain.models

enum class CurrencyUi(
    val code: String,
    val label: String,
) {
    USD("USD", "Dollar"),
    UZS("UZS", "So'm"),
    RUB("RUB", "Ruble")
}