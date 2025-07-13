package uz.akbarovdev.myexpenses

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.compose.rememberNavController
import uz.akbarovdev.myexpenses.app.navigation.NavigationRoot
import uz.akbarovdev.myexpenses.features.dashboard.presentation.DashboardRoot
import uz.akbarovdev.myexpenses.ui.theme.MyExpensesTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MyExpensesTheme {
                NavigationRoot(
                    rememberNavController()
                )
            }
        }
    }
}

