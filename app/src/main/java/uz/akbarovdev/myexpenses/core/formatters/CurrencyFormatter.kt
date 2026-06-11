package uz.akbarovdev.myexpenses.core.formatters

import java.text.NumberFormat
import java.util.Locale

object CurrencyFormatter {
    fun format(amount: Double?): String {
        return NumberFormat.getNumberInstance(Locale.getDefault()).apply {
            maximumFractionDigits = 3
        }.format(amount ?: 0.0)
    }
}