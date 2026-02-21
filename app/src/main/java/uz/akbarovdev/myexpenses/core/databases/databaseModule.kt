package uz.akbarovdev.myexpenses.core.databases

import androidx.room.Room
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import uz.akbarovdev.myexpenses.features.dashboard.daos.balance.BalanceDao
import uz.akbarovdev.myexpenses.features.dashboard.daos.transaction.TransactionDao

val databaseModule = module {
    single<MyExpensesDatabase> {
        Room.databaseBuilder(
            androidContext(),
            MyExpensesDatabase::class.java,
            "my_expenses.db"
        )
            .build()
    }
    single<TransactionDao> {
        get<MyExpensesDatabase>().transactionDao()
    }
    single<BalanceDao> {
        get<MyExpensesDatabase>().balanceDao()
    }

}