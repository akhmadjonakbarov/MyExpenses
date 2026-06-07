package uz.akbarovdev.myexpenses.core.databases

import androidx.room.Room
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import uz.akbarovdev.myexpenses.features.dashboard.daos.balance.BalanceDao
import uz.akbarovdev.myexpenses.features.dashboard.daos.transaction.TransactionDao
import uz.akbarovdev.myexpenses.features.debt.daos.DebtTransactionDao
import uz.akbarovdev.myexpenses.features.debt.daos.DebtUserDao

private val MIGRATION_1_2 = object : Migration(1, 2) {
    override fun migrate(db: SupportSQLiteDatabase) {
        db.execSQL(
            """CREATE TABLE IF NOT EXISTS `debt_users` (
                `id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
                `name` TEXT NOT NULL,
                `phone` TEXT,
                `totalAmount` REAL NOT NULL DEFAULT 0.0,
                `createdAt` INTEGER NOT NULL
            )"""
        )
        db.execSQL(
            """CREATE TABLE IF NOT EXISTS `debt_transactions` (
                `id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
                `userId` INTEGER NOT NULL,
                `amount` REAL NOT NULL,
                `type` TEXT NOT NULL,
                `note` TEXT,
                `isPaid` INTEGER NOT NULL DEFAULT 0,
                `createdAt` INTEGER NOT NULL,
                FOREIGN KEY (`userId`) REFERENCES `debt_users` (`id`) ON DELETE CASCADE
            )"""
        )
        db.execSQL("CREATE INDEX IF NOT EXISTS `index_debt_transactions_userId` ON `debt_transactions` (`userId`)")
    }
}

private val MIGRATION_2_3 = object : Migration(2, 3) {
    override fun migrate(db: SupportSQLiteDatabase) {
        db.execSQL("ALTER TABLE `debt_transactions` ADD COLUMN `isPaid` INTEGER NOT NULL DEFAULT 0")
    }
}

val databaseModule = module {
    single<MyExpensesDatabase> {
        Room.databaseBuilder(
            androidContext(),
            MyExpensesDatabase::class.java,
            "my_expenses.db"
        )
            .addMigrations(MIGRATION_1_2, MIGRATION_2_3)
            .build()
    }
    single<TransactionDao> {
        get<MyExpensesDatabase>().transactionDao()
    }
    single<BalanceDao> {
        get<MyExpensesDatabase>().balanceDao()
    }
    single<DebtUserDao> {
        get<MyExpensesDatabase>().debtUserDao()
    }
    single<DebtTransactionDao> {
        get<MyExpensesDatabase>().debtTransactionDao()
    }

}