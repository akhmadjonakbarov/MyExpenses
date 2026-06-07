package uz.akbarovdev.myexpenses

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.rememberDrawerState
import androidx.navigation.compose.rememberNavController
import uz.akbarovdev.myexpenses.app.navigation.DrawerNavigationRoot
import uz.akbarovdev.myexpenses.ui.theme.MyExpensesTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MyExpensesTheme {
                val drawerState = rememberDrawerState(DrawerValue.Closed)
                DrawerNavigationRoot(
                    navController = rememberNavController(),
                    drawerState = drawerState
                )
            }
        }
    }
}

