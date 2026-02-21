package uz.akbarovdev.myexpenses.app

import android.app.Application
import android.util.Log
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import uz.akbarovdev.myexpenses.app.di.appModule
import uz.akbarovdev.myexpenses.features.dashboard.dashboardModule
import uz.akbarovdev.myexpenses.features.preference.preferenceModule
import uz.akbarovdev.myexpenses.features.settings.settingsModule
import uz.akbarovdev.myexpenses.core.databases.databaseModule

class MyExpensesApp : Application() {
    val applicationScope = CoroutineScope(Dispatchers.Default + SupervisorJob())

    override fun onCreate() {
        super.onCreate()
        Log.d("MyExpensesApp", "onCreate: Application started")
        startKoin {
            androidContext(this@MyExpensesApp)
            modules(
                appModule, databaseModule, dashboardModule, settingsModule, preferenceModule,
            )
        }

    }
}