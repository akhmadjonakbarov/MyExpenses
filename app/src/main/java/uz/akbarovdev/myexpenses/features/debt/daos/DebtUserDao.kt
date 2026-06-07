package uz.akbarovdev.myexpenses.features.debt.daos

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface DebtUserDao {
    @Query("SELECT * FROM debt_users ORDER BY totalAmount DESC")
    fun getAllUsers(): Flow<List<DebtUserEntity>>

    @Query("SELECT * FROM debt_users WHERE id = :id")
    suspend fun getUserById(id: Int): DebtUserEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUser(user: DebtUserEntity)

    @Update
    suspend fun updateUser(user: DebtUserEntity)

    @Delete
    suspend fun deleteUser(user: DebtUserEntity)
}
