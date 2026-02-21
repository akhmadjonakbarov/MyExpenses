package uz.akbarovdev.myexpenses.features.preference.presentation.components

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import uz.akbarovdev.myexpenses.core.design_system.common_components.DropDownMenu
import uz.akbarovdev.myexpenses.features.preference.domain.models.CurrencyUi


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CurrencyDropDownMenu(
    onSelectCurrency: (CurrencyUi) -> Unit,
    modifier: Modifier = Modifier,
) {

    DropDownMenu(
        hintText = "Select Currenry",
        items = CurrencyUi.entries,
        onSelectValue = {
            onSelectCurrency(it)
        },
        selectedValue = null,
        labelMapper = {
            "${it.label} (${it.code})"
        }
    )
}
