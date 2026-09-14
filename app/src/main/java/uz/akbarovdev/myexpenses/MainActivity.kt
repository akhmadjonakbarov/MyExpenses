package uz.akbarovdev.myexpenses

import android.content.Context
import android.content.res.Configuration
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.getValue
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.rememberNavController
import org.koin.androidx.compose.koinViewModel
import uz.akbarovdev.myexpenses.app.navigation.DrawerNavigationRoot
import uz.akbarovdev.myexpenses.app.navigation.LogoutConfirmationDialog
import uz.akbarovdev.myexpenses.core.constants.PrefKeys
import uz.akbarovdev.myexpenses.core.extension.AppLocale
import uz.akbarovdev.myexpenses.features.auth.AuthScreen
import uz.akbarovdev.myexpenses.features.auth.AuthViewModel
import uz.akbarovdev.myexpenses.ui.theme.MyExpensesTheme
import java.util.Locale

class MainActivity : ComponentActivity() {

    override fun attachBaseContext(newBase: Context) {
        val prefs = newBase.getSharedPreferences(PrefKeys.PREFS_NAME, Context.MODE_PRIVATE)
        val languageCode = prefs.getString(PrefKeys.LANGUAGE, "uz") ?: "uz"
        val locale = Locale(languageCode)
        Locale.setDefault(locale)
        val config = Configuration(newBase.resources.configuration)
        config.setLocale(locale)
        super.attachBaseContext(newBase.createConfigurationContext(config))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MyExpensesTheme {
                AppLocale.Wrapper {
                    val authViewModel = koinViewModel<AuthViewModel>()
                    val authState by authViewModel.state.collectAsStateWithLifecycle()

                    if (authState.isAuthenticated) {
                        val drawerState = rememberDrawerState(DrawerValue.Closed)
                        DrawerNavigationRoot(
                            navController = rememberNavController(),
                            drawerState = drawerState,
                            onLogout = authViewModel::requestLogout,
                        )
                        if (authState.showLogoutConfirmation) {
                            LogoutConfirmationDialog(
                                onConfirm = authViewModel::confirmLogout,
                                onDismiss = authViewModel::dismissLogout,
                            )
                        }
                    } else {
                        AuthScreen(viewModel = authViewModel)
                    }
                }
            }
        }
    }
}

