package uz.akbarovdev.myexpenses.features.dashboard.domain.models

import uz.akbarovdev.myexpenses.core.enums.TransactionType
import java.util.Date

data class TransactionGroup(
    val date: String,
    val transactions: List<TransactionUi> = emptyList()
)

data class TransactionUi(
    val id: Int,
    val icon: CategoryUi,
    val amount: Double = 0.0,
    val type: TransactionType,
    val note: String = "",
    val receiver: String = "",
)

