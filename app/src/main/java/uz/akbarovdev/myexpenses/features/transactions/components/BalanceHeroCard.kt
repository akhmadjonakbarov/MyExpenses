package uz.akbarovdev.myexpenses.features.transactions.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import uz.akbarovdev.myexpenses.R
import uz.akbarovdev.myexpenses.core.formatters.CurrencyFormatter
import uz.akbarovdev.myexpenses.features.preference.domain.models.CurrencyUi

import uz.akbarovdev.myexpenses.ui.currencySymbol

@Composable
fun BalanceHeroCard(
    balance: Double,
    totalIncome: Double,
    totalExpense: Double,
    currencyUi: CurrencyUi,
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primary,
        ),
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Text(
                text = stringResource(R.string.balance),
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.8f),
            )
            Spacer(Modifier.height(8.dp))
            Text(
                text = "${currencySymbol(currencyUi)}${CurrencyFormatter.format(balance)}",
                style = MaterialTheme.typography.headlineLarge.copy(
                    fontWeight = FontWeight.Bold,
                ),
                color = MaterialTheme.colorScheme.onPrimary,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
            )
            Spacer(Modifier.height(16.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly,
            ) {
                IncomeExpenseLabel(
                    label = stringResource(R.string.income),
                    amount = totalIncome,
                    color = Color(0xFF81C784),
                    currencyUi = currencyUi,
                )
                IncomeExpenseLabel(
                    label = stringResource(R.string.expense),
                    amount = totalExpense,
                    color = Color(0xFFE57373),
                    currencyUi = currencyUi,
                )
            }
        }
    }
}

@Composable
private fun IncomeExpenseLabel(
    label: String,
    amount: Double,
    color: Color,
    currencyUi: CurrencyUi,
) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(
            text = label,
            style = MaterialTheme.typography.labelMedium,
            color = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.7f),
        )
        Spacer(Modifier.height(4.dp))
        Text(
            text = "${currencySymbol(currencyUi)}${CurrencyFormatter.format(amount)}",
            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.SemiBold),
            color = color,
        )
    }
}