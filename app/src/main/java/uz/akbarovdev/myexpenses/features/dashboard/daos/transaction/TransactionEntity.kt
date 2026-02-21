package uz.akbarovdev.myexpenses.features.dashboard.daos.transaction

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(
    tableName = "transactions"
)
data class TransactionEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val amount: Double,
    val type: String,
    val note: String?,
    val receiver: String?,
    val category: String?,
    val createdAt: Long = System.currentTimeMillis()
)