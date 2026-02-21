package uz.akbarovdev.myexpenses.features.dashboard.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import uz.akbarovdev.myexpenses.features.dashboard.presentation.view_model.DashboardState

@Composable
fun AmountInputTextField(
    modifier: Modifier = Modifier,
    state: DashboardState,
    onValueChange: (String) -> Unit
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(80.dp), // Adjust height as needed
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {

        Box(
            modifier = Modifier
                .weight(1f)
                .fillMaxHeight(),
            contentAlignment = Alignment.CenterEnd
        ) {
            USDSymbol(
                transactionType = state.transactionType
            )
        }
        Spacer(Modifier.width(5.dp))

        Box(
            modifier = Modifier
                .weight(1.5f)
                .fillMaxHeight(),

            contentAlignment = Alignment.CenterStart
        ) {
            if (state.amountText.isEmpty()) {
                Text(
                    text = "00.00",
                    style = MaterialTheme.typography.displaySmall,
                    color = Color.Gray
                )
            }
            BasicTextField(
                value = state.amountText,
                onValueChange = onValueChange,
                textStyle = MaterialTheme.typography.displaySmall.copy(
                    color = MaterialTheme.colorScheme.onSurface
                ),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
            )
        }
    }
}