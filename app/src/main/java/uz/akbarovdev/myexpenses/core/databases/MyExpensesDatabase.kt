package uz.akbarovdev.myexpenses.core.databases

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import uz.akbarovdev.myexpenses.core.convertors.MapTypeConverter
import uz.akbarovdev.myexpenses.features.dashboard.daos.balance.BalanceDao
import uz.akbarovdev.myexpenses.features.dashboard.daos.balance.BalanceEntity
import uz.akbarovdev.myexpenses.features.dashboard.daos.transaction.TransactionDao
import uz.akbarovdev.myexpenses.features.dashboard.daos.transaction.TransactionEntity
import uz.akbarovdev.myexpenses.features.debt.daos.DebtTransactionDao
import uz.akbarovdev.myexpenses.features.debt.daos.DebtTransactionEntity
import uz.akbarovdev.myexpenses.features.debt.daos.DebtUserDao
import uz.akbarovdev.myexpenses.features.debt.daos.DebtUserEntity

@Database(
    entities = [
        TransactionEntity::class,
        BalanceEntity::class,
        DebtUserEntity::class,
        DebtTransactionEntity::class
    ],
    version = 3
)
@TypeConverters(MapTypeConverter::class)
abstract class MyExpensesDatabase : RoomDatabase() {
    abstract fun transactionDao(): TransactionDao
    abstract fun balanceDao(): BalanceDao
    abstract fun debtUserDao(): DebtUserDao
    abstract fun debtTransactionDao(): DebtTransactionDao

}