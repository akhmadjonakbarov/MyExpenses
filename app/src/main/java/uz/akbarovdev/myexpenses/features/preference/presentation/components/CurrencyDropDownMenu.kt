package uz.akbarovdev.myexpenses.features.preference.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import uz.akbarovdev.myexpenses.core.design_system.common_components.DropDownMenu
import uz.akbarovdev.myexpenses.features.dashboard.domain.models.CategoryUi
import uz.akbarovdev.myexpenses.features.preference.domain.models.CurrencyUi


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CurrencyDropDownMenu(modifier: Modifier = Modifier) {
//    var expanded by remember { mutableStateOf(false) }
//    ExposedDropdownMenuBox(
//        expanded = expanded,
//        onExpandedChange = { expanded = !expanded }) {
//        OutlinedTextField(
//            readOnly = true,
//            value = "Select Currency",
////            value = "${CurrencyUi.UZS.label} ${CurrencyUi.UZS.label}",
//            onValueChange = {},
//            trailingIcon = {
//                ExposedDropdownMenuDefaults.TrailingIcon(expanded)
//            },
//            modifier = Modifier
//                .border(
//                    width = 0.dp, color = Color.Transparent,
//                )
//                .menuAnchor()
//                .fillMaxWidth(),
//            colors = OutlinedTextFieldDefaults.colors(
//                focusedBorderColor = Color.Transparent,
//                unfocusedBorderColor = Color.Transparent,
//                disabledBorderColor = Color.Transparent,
//                errorBorderColor = Color.Transparent,
//                focusedContainerColor = Color.White,
//                unfocusedContainerColor = Color.White
//            ),
//            shape = RoundedCornerShape(16.dp),
//        )
//
//        ExposedDropdownMenu(
//            expanded = expanded, onDismissRequest = {
//                expanded = false
//            }, modifier = Modifier
//                .background(MaterialTheme.colorScheme.surface, RoundedCornerShape(16.dp))
//                .padding(vertical = 4.dp)
//        ) {
//            CurrencyUi.entries.forEach { currency ->
//                DropdownMenuItem(
//                    text = {
//                        Text("${currency.label}   (${currency.shorter})")
//                    },
//                    onClick = {
//                        expanded = false
//                    },
//                    contentPadding = PaddingValues(horizontal = 12.dp, vertical = 6.dp)
//                )
//            }
//        }
//    }
//    var selectedCurrency by remember { mutableStateOf(CurrencyUi.UZS) }
    DropDownMenu(
        hintText = "Select Currenry",
        items = CurrencyUi.entries,
        onSelectValue = {
//            selectedCurrency = it
        },
        selectedValue = null,
        labelMapper = {
            "${it.label} (${it.shorter})"
        }
    )
}
