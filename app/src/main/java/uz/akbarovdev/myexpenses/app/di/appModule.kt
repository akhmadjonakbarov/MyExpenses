package uz.akbarovdev.myexpenses.app.di

import kotlinx.coroutines.CoroutineScope
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module
import uz.akbarovdev.myexpenses.app.MyExpensesApp

val appModule = module {
    single<CoroutineScope> {
        (androidApplication() as MyExpensesApp).applicationScope
    }
}