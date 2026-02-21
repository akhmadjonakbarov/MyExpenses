package uz.akbarovdev.myexpenses.features.preference

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import org.koin.androidx.compose.koinViewModel
import uz.akbarovdev.myexpenses.core.design_system.buttons.BackButton
import uz.akbarovdev.myexpenses.core.design_system.top_bar.Title
import uz.akbarovdev.myexpenses.features.preference.presentation.components.CurrencyDropDownMenu
import uz.akbarovdev.myexpenses.features.preference.presentation.view_model.PreferenceAction
import uz.akbarovdev.myexpenses.features.preference.presentation.view_model.PreferenceEvents
import uz.akbarovdev.myexpenses.features.preference.presentation.view_model.PreferenceState
import uz.akbarovdev.myexpenses.features.preference.presentation.view_model.PreferenceViewModel
import uz.akbarovdev.myexpenses.ui.theme.MyExpensesTheme

@Composable
fun PreferenceRoot(
    navController: NavController,
    viewModel: PreferenceViewModel = koinViewModel(), isInitial: Boolean = false
) {
    val state by viewModel.state.collectAsStateWithLifecycle()


    val snackBarHostState = remember { SnackbarHostState() }

    LaunchedEffect(viewModel.events) {
        viewModel.events.collect { event ->
            when (event) {
                PreferenceEvents.CurrencySelected -> {
                    snackBarHostState.showSnackbar("Currency selected")
                }
            }
        }
    }

    PreferenceScreen(
        state = state, onAction = viewModel::onAction, isInitial = isInitial,
        navController =
            navController,
        snackBarHostState = snackBarHostState,

        )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PreferenceScreen(
    snackBarHostState: SnackbarHostState,

    navController: NavController,
    state: PreferenceState, onAction: (PreferenceAction) -> Unit, isInitial: Boolean = false,
) {

    Scaffold(
        topBar = {
            TopAppBar(
                navigationIcon = {
                    BackButton(onClick = navController::navigateUp)
                },
                title = {
                    if (!isInitial) {
                        Title(
                            title = "Preference",
                        )
                    }
                },
            )
        },
        snackbarHost = {
            SnackbarHost(
                snackBarHostState
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .padding(16.dp)
        ) {
            if (isInitial) Column {
                Text(
                    "Set SpendLess\n" + "to your preferences",
                    style = MaterialTheme.typography.headlineMedium.copy(
                        fontWeight = FontWeight.W600,
                    ),
                )
                Spacer(
                    Modifier.height(8.dp)
                )
                Text(
                    "You can change it at any time in Settings",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
            Text("Currency")
            CurrencyDropDownMenu(
                { currencyUi ->
                    onAction(PreferenceAction.OnSelectCurrency(currencyUi))
                }
            )

        }
    }
}


@Preview
@Composable
private fun Preview() {
    val snackBarHostState = remember { SnackbarHostState() }
    MyExpensesTheme {
        PreferenceScreen(
            state = PreferenceState(),
            onAction = {},
            navController = rememberNavController(),
            snackBarHostState = snackBarHostState
        )
    }
}