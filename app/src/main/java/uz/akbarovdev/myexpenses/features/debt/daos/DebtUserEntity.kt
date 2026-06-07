package uz.akbarovdev.myexpenses.features.debt.daos

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "debt_users")
data class DebtUserEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val name: String,
    val phone: String? = null,
    val totalAmount: Double = 0.0,
    val createdAt: Long = System.currentTimeMillis()
)
