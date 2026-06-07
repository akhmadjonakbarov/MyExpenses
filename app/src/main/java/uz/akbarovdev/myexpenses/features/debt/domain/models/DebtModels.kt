package uz.akbarovdev.myexpenses.features.debt.domain.models

data class DebtUserUi(
    val id: Int,
    val name: String,
    val phone: String = "",
    val totalAmount: Double = 0.0,
    val createdAt: Long = 0L
)

data class DebtTransactionUi(
    val id: Int,
    val userId: Int,
    val amount: Double,
    val type: DebtTransactionType,
    val note: String = "",
    val isPaid: Boolean = false,
    val createdAt: Long = 0L
)

enum class DebtTransactionType {
    GAVE, TOOK
}
