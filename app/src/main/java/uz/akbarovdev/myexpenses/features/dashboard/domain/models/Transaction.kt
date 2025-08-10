package uz.akbarovdev.myexpenses.features.dashboard.domain.models

import java.util.Date

data class TransactionGroup(
    val date: Date,
    val transactions: List<Transaction> = emptyList()
)

data class Transaction(
    val icon: String = "✅",
    val name: String = "",
    val price: Double = 0.0,
)