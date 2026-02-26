package uz.akbarovdev.myexpenses.core.formatters

import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.Date
import java.util.Locale

object DateFormatter {
    fun format(milliseconds: Long, pattern: String = "dd.MM.yyyy"): String {
        // 1. Create a SimpleDateFormat object with the desired pattern and locale
        val formatter = SimpleDateFormat(pattern, Locale.getDefault())

        // 2. Create a Date object from the milliseconds
        val date = Date(milliseconds)

        // 3. Format the Date object into a string
        return formatter.format(date)

    }
}