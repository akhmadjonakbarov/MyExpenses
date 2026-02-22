package uz.akbarovdev.myexpenses.features.preference.presentation.components

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import uz.akbarovdev.myexpenses.core.design_system.drop_down.GenericDropDownMenu
import uz.akbarovdev.myexpenses.features.preference.domain.models.CurrencyUi
import uz.akbarovdev.myexpenses.features.preference.domain.models.LanguageUi


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CurrencyDropDownMenu(
    onSelectCurrency: (CurrencyUi) -> Unit,
    selectedCurrency: CurrencyUi? = null,
    hintText: String,
) {
    GenericDropDownMenu(
        items = CurrencyUi.entries,
        selectedValue = selectedCurrency,
        hintText = hintText,
        onSelectValue = onSelectCurrency,
        labelMapper = { "${it.label} (${it.code})" }
    )
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LanguageDropDownMenu(
    onSelectLanguage: (LanguageUi) -> Unit,
    selectedLanguage: LanguageUi? = null,
    hintText: String,
) {
    GenericDropDownMenu(
        items = LanguageUi.entries,
        selectedValue = selectedLanguage,
        hintText = hintText,
        onSelectValue = onSelectLanguage,
        labelMapper = { "${it.label} (${it.code})" }
    )
}
