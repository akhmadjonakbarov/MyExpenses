package uz.akbarovdev.myexpenses.features.dashboard.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import uz.akbarovdev.myexpenses.features.preference.domain.models.CurrencyUi

@Composable
fun AccountBalance(
    balance: Double = 0.0,
    currencyUi: CurrencyUi,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.Bottom,
            horizontalArrangement = Arrangement.Center
        ) {
            Text(
                "$balance", style = MaterialTheme.typography.displayMedium,
                color = MaterialTheme.colorScheme.onPrimary,

                )
            Spacer(
                Modifier.width(5.dp)
            )
            Text(
                modifier = Modifier.padding(bottom = 5.dp),
                text = currencyUi.code, style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onPrimary,
            )
        }
        Text(
            "Account Balance",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onPrimary,
        )
    }
}


@Preview(
    showBackground = false,
)
@Composable
private fun AccountBalancePreview() {
    AccountBalance(
        balance = 10000.0,
        currencyUi = CurrencyUi.UZS
    )

}