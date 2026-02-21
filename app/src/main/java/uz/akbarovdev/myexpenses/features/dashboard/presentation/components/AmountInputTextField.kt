package uz.akbarovdev.myexpenses.features.dashboard.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import uz.akbarovdev.myexpenses.core.enums.TransactionType
import uz.akbarovdev.myexpenses.features.dashboard.presentation.view_model.DashboardState
import uz.akbarovdev.myexpenses.ui.theme.Success

@Composable
fun AmountInputTextField(
    modifier: Modifier = Modifier,
    state: DashboardState,
    onValueChange: (String) -> Unit
) {
    TextField(
        value = state.amountText,
        onValueChange = onValueChange,
        placeholder = {
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentSize(Alignment.Center),
                textAlign = TextAlign.Center,
                text = "0.00",
                style = MaterialTheme.typography.displaySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.3f)
            )
        },
        suffix = {
            Text(
                text = state.selectedCurrencyUi.code,
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.primary, // Using your brand purple
                modifier = Modifier.padding(top = 8.dp)
            )
        },
        textStyle = MaterialTheme.typography.displaySmall.copy(
            textAlign = TextAlign.Center,
            color = if (state.transactionType == TransactionType.Expense) MaterialTheme.colorScheme.error else Success
        ),
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
        modifier = Modifier.fillMaxWidth(),
        singleLine = true,
        colors = TextFieldDefaults.colors(
            focusedContainerColor = Color.Transparent,
            unfocusedContainerColor = Color.Transparent,
            disabledContainerColor = Color.Transparent,
            focusedIndicatorColor = Color.Transparent,    // Removes the underline when clicked
            unfocusedIndicatorColor = Color.Transparent,  // Removes the underline when idle
            cursorColor = MaterialTheme.colorScheme.primary
        )
    )
//    Row(
//        modifier = modifier
//            .fillMaxWidth()
//            .height(80.dp), // Adjust height as needed
//        verticalAlignment = Alignment.CenterVertically,
//        horizontalArrangement = Arrangement.Center
//    ) {
//
//
//        Box(
//            modifier = Modifier
//                .fillMaxHeight(),
//
//            contentAlignment = Alignment.Center
//        ) {
////            if (state.amountText.isEmpty()) {
////                Row {
////                    Text(
////                        text = "00.00",
////                        style = MaterialTheme.typography.displaySmall,
////                        color = Color.Gray
////                    )
////                    Text(
////                        state.selectedCurrencyUi.code, style = MaterialTheme.typography.labelLarge,
////                    )
////                }
////            }
//
//
//        }
//
//
//    }
}

@Preview(showBackground = true)
@Composable
private fun AmountTextFieldPreview() {
    AmountInputTextField(state = DashboardState()) { }
}