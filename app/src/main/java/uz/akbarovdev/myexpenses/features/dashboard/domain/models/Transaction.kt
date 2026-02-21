package uz.akbarovdev.myexpenses.features.dashboard.domain.models

import java.util.Date

data class TransactionGroup(
    val date: Date,
    val transactions: List<Transaction> = emptyList()
)

data class TransactionUi(
    val id:Int,
    val icon: CategoryUi,
    val amount: Double = 0.0,
    val type: String = "",
    val note: String = "",
    val receiver: String = "",
)

data class Transaction(
    val amount: Double = 0.0,
    val type: String = "",
    val note: String = "",
    val receiver: String = "",
    val category: String = ""
)