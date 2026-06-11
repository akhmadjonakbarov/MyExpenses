package uz.akbarovdev.myexpenses.ui

import uz.akbarovdev.myexpenses.features.preference.domain.models.CurrencyUi

 fun currencySymbol(currencyUi: CurrencyUi): String = when (currencyUi) {
    CurrencyUi.USD -> "$"
    CurrencyUi.UZS -> ""
    CurrencyUi.RUB -> "₽"
}
