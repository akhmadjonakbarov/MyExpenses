package uz.akbarovdev.myexpenses.features.preference

import androidx.compose.foundation.background
import androidx.compose.foundation.border

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import uz.akbarovdev.myexpenses.core.design_system.buttons.BackButton
import uz.akbarovdev.myexpenses.core.design_system.top_bar.Title
import uz.akbarovdev.myexpenses.features.preference.domain.models.CurrencyUi
import uz.akbarovdev.myexpenses.features.preference.presentation.components.CurrencyDropDownMenu
import uz.akbarovdev.myexpenses.features.preference.presentation.view_model.PreferenceAction
import uz.akbarovdev.myexpenses.features.preference.presentation.view_model.PreferenceState
import uz.akbarovdev.myexpenses.features.preference.presentation.view_model.PreferenceViewModel
import uz.akbarovdev.myexpenses.ui.theme.MyExpensesTheme

@Composable
fun PreferenceRoot(
    viewModel: PreferenceViewModel = viewModel(), isInitial: Boolean = false
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    PreferenceScreen(
        state = state, onAction = viewModel::onAction, isInitial = isInitial

    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PreferenceScreen(
    state: PreferenceState, onAction: (PreferenceAction) -> Unit, isInitial: Boolean = false
) {

    Scaffold(
        topBar = {
            TopAppBar(
                navigationIcon = {
                    BackButton(onClick = {})
                },
                title = {
                    if (!isInitial) {
                        Title(
                            title = "Preference",
                        )
                    }
                },
            )
        }) { innerPadding ->
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
            CurrencyDropDownMenu()

        }
    }
}


@Preview
@Composable
private fun Preview() {
    MyExpensesTheme {
        PreferenceScreen(
            state = PreferenceState(), onAction = {})
    }
}