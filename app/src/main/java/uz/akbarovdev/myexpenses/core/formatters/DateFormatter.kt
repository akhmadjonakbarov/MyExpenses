package uz.akbarovdev.myexpenses.core.formatters

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

object DateFormatter {
    fun format(milliseconds: Long, pattern: String = "dd.MM.yyyy"): String {
        val formatter = SimpleDateFormat(pattern, Locale.getDefault())

        val date = Date(milliseconds)

        return formatter.format(date)

    }
}