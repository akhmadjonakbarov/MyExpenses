package uz.akbarovdev.myexpenses.features.debt.daos

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "debt_transactions",
    foreignKeys = [
        ForeignKey(
            entity = DebtUserEntity::class,
            parentColumns = ["id"],
            childColumns = ["userId"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index("userId")]
)
data class DebtTransactionEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val userId: Int,
    val amount: Double,
    val type: String,
    val note: String? = null,
    val isPaid: Boolean = false,
    val createdAt: Long = System.currentTimeMillis()
)
