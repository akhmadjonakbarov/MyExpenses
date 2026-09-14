package uz.akbarovdev.myexpenses.features.dashboard.data.sync

import uz.akbarovdev.myexpenses.features.dashboard.daos.balance.BalanceEntity
import uz.akbarovdev.myexpenses.features.dashboard.daos.transaction.TransactionEntity

const val FIELD_AMOUNT = "amount"
const val FIELD_TYPE = "type"
const val FIELD_NOTE = "note"
const val FIELD_RECEIVER = "receiver"
const val FIELD_CATEGORY = "category"
const val FIELD_CREATED_AT = "createdAt"

fun balanceToMap(balance: BalanceEntity): Map<String, Any> = mapOf(
    FIELD_AMOUNT to balance.amount,
    FIELD_CREATED_AT to balance.createdAt,
)

fun balanceFromMap(id: String, values: Map<String, Any?>): BalanceEntity? {
    val numericId = id.toIntOrNull() ?: return null
    return BalanceEntity(
        id = numericId,
        amount = (values[FIELD_AMOUNT] as? Number)?.toDouble() ?: 0.0,
        createdAt = (values[FIELD_CREATED_AT] as? Number)?.toLong() ?: 0L,
    )
}

fun transactionToMap(transaction: TransactionEntity): Map<String, Any?> = mapOf(
    FIELD_AMOUNT to transaction.amount,
    FIELD_TYPE to transaction.type,
    FIELD_NOTE to transaction.note,
    FIELD_RECEIVER to transaction.receiver,
    FIELD_CATEGORY to transaction.category,
    FIELD_CREATED_AT to transaction.createdAt,
)

fun transactionFromMap(id: String, values: Map<String, Any?>): TransactionEntity? {
    val numericId = id.toIntOrNull() ?: return null
    return TransactionEntity(
        id = numericId,
        amount = (values[FIELD_AMOUNT] as? Number)?.toDouble() ?: 0.0,
        type = values[FIELD_TYPE] as? String ?: "",
        note = values[FIELD_NOTE] as? String,
        receiver = values[FIELD_RECEIVER] as? String,
        category = values[FIELD_CATEGORY] as? String,
        createdAt = (values[FIELD_CREATED_AT] as? Number)?.toLong() ?: 0L,
    )
}