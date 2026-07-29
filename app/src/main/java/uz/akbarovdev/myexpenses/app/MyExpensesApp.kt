package uz.akbarovdev.myexpenses.app

import android.app.Application
import android.util.Log
import com.google.firebase.FirebaseApp
import com.google.firebase.crashlytics.FirebaseCrashlytics
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import uz.akbarovdev.myexpenses.app.di.appModule
import uz.akbarovdev.myexpenses.core.databases.databaseModule
import uz.akbarovdev.myexpenses.core.extension.AppLocale
import uz.akbarovdev.myexpenses.features.auth.authModule
import uz.akbarovdev.myexpenses.features.dashboard.dashboardModule
import uz.akbarovdev.myexpenses.features.debt.debtModule
import uz.akbarovdev.myexpenses.features.preference.preferenceModule
import uz.akbarovdev.myexpenses.features.settings.settingsModule

class MyExpensesApp : Application() {
    val applicationScope = CoroutineScope(Dispatchers.Default + SupervisorJob())

    override fun onCreate() {
        super.onCreate()
        AppLocale.init(this)
        FirebaseApp.initializeApp(this)
        FirebaseCrashlytics.getInstance().setCrashlyticsCollectionEnabled(true)
        Log.d("MyExpensesApp", "onCreate: Application started")
        startKoin {
            androidContext(this@MyExpensesApp)
            modules(
                appModule, databaseModule, dashboardModule, settingsModule, preferenceModule, debtModule, authModule,
            )
        }

    }
}