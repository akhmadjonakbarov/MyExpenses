package uz.akbarovdev.myexpenses.features.dashboard.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import uz.akbarovdev.myexpenses.core.design_system.common_components.TextBox
import uz.akbarovdev.myexpenses.features.dashboard.domain.models.CategoryUi


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CategoryDropdown(
    selectedCategory: CategoryUi?,
    onCategorySelected: (CategoryUi) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }

    ExposedDropdownMenuBox(

        expanded = expanded,
        onExpandedChange = { expanded = !expanded }
    ) {
        // Selected Item View
        OutlinedTextField(
            readOnly = true,
            value = if (selectedCategory != null) stringResource(selectedCategory.code) else "Select Category",
            onValueChange = {},
            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded) },
            modifier = Modifier
                .border(
                    width = 0.dp, color = Color.Transparent,
                )
                .menuAnchor()
                .fillMaxWidth(),
            leadingIcon = {
                selectedCategory?.let {
                    TextBox(
                        text = it.emoji,
                        modifier = Modifier.size(40.dp),
                        textStyle = MaterialTheme.typography.titleLarge

                    )
                }
            },
            shape = RoundedCornerShape(16.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = Color.Transparent,
                unfocusedBorderColor = Color.Transparent,
                disabledBorderColor = Color.Transparent,
                errorBorderColor = Color.Transparent,
                focusedContainerColor = Color.White,
                unfocusedContainerColor = Color.White
            )

        )

        // Dropdown Items
        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier
                .background(MaterialTheme.colorScheme.surface, RoundedCornerShape(16.dp))
                .padding(vertical = 4.dp)
        ) {
            CategoryUi.entries.forEach { category ->
                DropdownMenuItem(
                    text = {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            TextBox(
                                text = category.emoji,
                                modifier = Modifier.size(40.dp),
                                textStyle = MaterialTheme.typography.titleLarge
                            )

                            Spacer(Modifier.width(12.dp))

                            Text(text = stringResource(category.code), fontSize = 16.sp)

                            Spacer(Modifier.weight(1f))

                            if (category == selectedCategory) {
                                Icon(
                                    imageVector = Icons.Default.Check,
                                    contentDescription = "Selected",
                                    tint = MaterialTheme.colorScheme.primary
                                )
                            }
                        }
                    },
                    onClick = {
                        onCategorySelected(category)
                        expanded = false
                    },
                    contentPadding = PaddingValues(horizontal = 12.dp, vertical = 6.dp)
                )
            }
        }
    }
}


@Preview(
    showBackground = true
)
@Composable
private fun DropDownPreview() {
    CategoryDropdown(
        CategoryUi.ENTERTAINMENT
    ) { }
}