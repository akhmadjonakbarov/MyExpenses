package uz.akbarovdev.myexpenses.features.transactions.components

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import uz.akbarovdev.myexpenses.R
import uz.akbarovdev.myexpenses.core.enums.TransactionType
import uz.akbarovdev.myexpenses.core.formatters.CurrencyFormatter
import uz.akbarovdev.myexpenses.features.dashboard.domain.models.TransactionGroup
import uz.akbarovdev.myexpenses.features.preference.domain.models.CurrencyUi
import uz.akbarovdev.myexpenses.ui.currencySymbol


@Composable
 fun DailySpendingBarChart(
    transactionGroups: List<TransactionGroup>,
    currencyUi: CurrencyUi,
) {
    val dailyTotals = remember(transactionGroups) {
        transactionGroups.map { group ->
            val total = group.transactions
                .filter { it.type == TransactionType.Expense }
                .sumOf { it.amount }
            Pair(group.date, total)
        }.takeLast(7)
    }

    val maxAmount = remember(dailyTotals) {
        dailyTotals.maxOfOrNull { it.second } ?: 1.0
    }

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceContainerLow,
        ),
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp),
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(
                    text = stringResource(R.string.daily_spending),
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.SemiBold,
                )
                Text(
                    text = "${currencySymbol(currencyUi)}${CurrencyFormatter.format(dailyTotals.sumOf { it.second })}",
                    style = MaterialTheme.typography.titleSmall.copy(
                        fontWeight = FontWeight.SemiBold,
                    ),
                    color = MaterialTheme.colorScheme.primary,
                )
            }
            Spacer(Modifier.height(20.dp))
            if (dailyTotals.isNotEmpty()) {
                val animatedProgress by animateFloatAsState(
                    targetValue = 1f,
                    animationSpec = tween(durationMillis = 800),
                    label = "barProgress",
                )

                val primary = MaterialTheme.colorScheme.primary
                val onSurface = MaterialTheme.colorScheme.onSurface
                val onSurfaceVariant = MaterialTheme.colorScheme.onSurfaceVariant

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.Bottom,
                ) {
                    dailyTotals.forEachIndexed { index, (date, amount) ->
                        val barHeightFraction = ((amount / maxAmount) * animatedProgress).toFloat().coerceAtLeast(0.01f)
                        val isHighest = amount == maxAmount
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            modifier = Modifier.weight(1f),
                        ) {
                            Text(
                                text = CurrencyFormatter.format(amount),
                                style = MaterialTheme.typography.labelSmall,
                                color = if (isHighest) primary else onSurfaceVariant,
                                maxLines = 1,
                                fontWeight = if (isHighest) FontWeight.SemiBold else FontWeight.Normal,
                            )
                            Spacer(Modifier.height(4.dp))
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(horizontal = 3.dp)
                                    .height(100.dp),
                                contentAlignment = Alignment.BottomCenter,
                            ) {
                                Box(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .fillMaxHeight(fraction = barHeightFraction)
                                        .background(
                                            brush = Brush.verticalGradient(
                                                colors = if (isHighest)
                                                    listOf(primary, primary.copy(alpha = 0.3f))
                                                else
                                                    listOf(
                                                        primary.copy(alpha = 0.6f),
                                                        primary.copy(alpha = 0.15f),
                                                    ),
                                            ),
                                            shape = RoundedCornerShape(
                                                topStartPercent = 50,
                                                topEndPercent = 50,
                                            ),
                                        ),
                                )
                            }
                            Spacer(Modifier.height(6.dp))
                            Text(
                                text = date.takeLast(5),
                                style = MaterialTheme.typography.labelSmall.copy(
                                    fontWeight = if (isHighest) FontWeight.SemiBold else FontWeight.Normal,
                                ),
                                color = if (isHighest) primary else onSurfaceVariant,
                            )
                        }
                    }
                }
            } else {
                Text(
                    text = stringResource(R.string.no_transactions),
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                )
            }
        }
    }
}
