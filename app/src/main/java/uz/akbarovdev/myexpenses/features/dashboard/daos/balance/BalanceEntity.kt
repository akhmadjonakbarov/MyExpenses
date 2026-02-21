package uz.akbarovdev.myexpenses.features.dashboard.daos.balance

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(
    "balances"
)
data class BalanceEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val amount: Double,
    val createdAt: Long = System.currentTimeMillis(),
)