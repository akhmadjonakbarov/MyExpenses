package uz.akbarovdev.myexpenses.features.debt.daos

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface DebtTransactionDao {
    @Query("SELECT * FROM debt_transactions WHERE userId = :userId ORDER BY createdAt DESC")
    fun getTransactionsByUserId(userId: Int): Flow<List<DebtTransactionEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTransaction(transaction: DebtTransactionEntity)

    @Update
    suspend fun updateTransaction(transaction: DebtTransactionEntity)

    @Delete
    suspend fun deleteTransaction(transaction: DebtTransactionEntity)
}
