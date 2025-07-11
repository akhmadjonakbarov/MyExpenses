package uz.akbarovdev.myexpenses.features.settings

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import uz.akbarovdev.myexpenses.ui.theme.MyExpensesTheme

@Composable
fun SettingsRoot(
    viewModel: SettingsViewModel = viewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    SettingsScreen(
        state = state,
        onAction = viewModel::onAction
    )
}

@Composable
fun SettingsScreen(
    state: SettingsState,
    onAction: (SettingsAction) -> Unit,
) {

}

@Preview
@Composable
private fun Preview() {
    MyExpensesTheme {
        SettingsScreen(
            state = SettingsState(),
            onAction = {}
        )
    }
}