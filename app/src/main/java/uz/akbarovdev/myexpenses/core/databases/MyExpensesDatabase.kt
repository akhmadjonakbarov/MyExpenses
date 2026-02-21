package uz.akbarovdev.myexpenses.core.databases

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import uz.akbarovdev.myexpenses.core.convertors.MapTypeConverter
import uz.akbarovdev.myexpenses.features.dashboard.daos.balance.BalanceDao
import uz.akbarovdev.myexpenses.features.dashboard.daos.balance.BalanceEntity
import uz.akbarovdev.myexpenses.features.dashboard.daos.transaction.TransactionDao
import uz.akbarovdev.myexpenses.features.dashboard.daos.transaction.TransactionEntity

@Database(
    entities = [
        TransactionEntity::class,
        BalanceEntity::class
    ],
    version = 1
)
@TypeConverters(MapTypeConverter::class)
abstract class MyExpensesDatabase : RoomDatabase() {
    abstract fun transactionDao(): TransactionDao
    abstract fun balanceDao(): BalanceDao

}