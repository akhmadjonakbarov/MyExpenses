package uz.akbarovdev.myexpenses.core.formatters

import java.text.NumberFormat
import java.util.Locale

object CurrencyFormatter {
    private val numberInstance = NumberFormat.getNumberInstance(Locale.getDefault()).apply {
//        minimumFractionDigits = 2
        maximumFractionDigits = 3
    }

    fun format(amount: Double?): String {
        return numberInstance.format(amount ?: 0.0)
    }
}