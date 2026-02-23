package uz.akbarovdev.myexpenses.core.design_system.drop_down

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import uz.akbarovdev.myexpenses.core.design_system.common_components.DropDownMenu


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun <T> GenericDropDownMenu(
    items: List<T>,
    onSelectValue: (T) -> Unit,
    modifier: Modifier = Modifier,
    selectedValue: T? = null,
    hintText: String,
    labelMapper: (T) -> String = { it.toString() }
) {
    DropDownMenu(
        modifier = modifier,
        hintText = hintText,
        items = items,
        onSelectValue = onSelectValue,
        selectedValue = selectedValue,
        labelMapper = labelMapper
    )
}