package uz.akbarovdev.myexpenses.features.dashboard.daos.balance

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query


@Dao
interface BalanceDao {
    @Query("select * from balances")
    suspend fun getAllBalances(): List<BalanceEntity>
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertBalance(balance: BalanceEntity)
    @Delete
    suspend fun deleteBalance(balance: BalanceEntity)


}