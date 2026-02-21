package uz.akbarovdev.myexpenses.features.dashboard.presentation.components

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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import uz.akbarovdev.myexpenses.features.dashboard.domain.models.CategoryUi
import uz.akbarovdev.myexpenses.features.dashboard.domain.models.TransactionUi
import uz.akbarovdev.myexpenses.features.dashboard.presentation.view_model.DashboardState
import uz.akbarovdev.myexpenses.ui.theme.PrimaryFixed
import uz.akbarovdev.myexpenses.ui.theme.SecondaryFixed

@Composable
fun TransactionInfo(
    state: DashboardState,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(80.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {

        Box(
            modifier = Modifier
                .fillMaxHeight()
                .background(
                    color = PrimaryFixed, shape = RoundedCornerShape(
                        15.dp,
                    )
                )
                .weight(0.6f)
                .padding(10.dp), contentAlignment = Alignment.Center
        ) {
            Column(
                verticalArrangement = Arrangement.spacedBy(5.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        state.largestTransactionUi?.receiver ?: "",
                        style = MaterialTheme.typography.titleMedium,
                    )
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(2.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        if (state.largestTransactionUi != null)
                            Text(
                                "${state.largestTransactionUi?.amount}",
                                style = MaterialTheme.typography.titleMedium,
                            )
                        else
                            Text(
                                "${0.0}",
                                style = MaterialTheme.typography.titleMedium,
                            )
                        Text(
                            "${state.selectedCurrencyUi.code}",
                            style = MaterialTheme.typography.titleSmall,
                        )
                    }
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        "Largest transaction",
                        style = MaterialTheme.typography.bodySmall,
                    )
                    Text(
                        "Jan 7, 2025",
                        style = MaterialTheme.typography.bodySmall,
                    )
                }
            }
        }
        Spacer(
            Modifier.width(
                5.dp,
            )
        )
        Box(
            modifier = Modifier
                .fillMaxHeight()
                .weight(0.4f)
                .background(
                    color = SecondaryFixed, shape = RoundedCornerShape(
                        15.dp,
                    )
                )
                .padding(start = 10.dp),

            contentAlignment = Alignment.CenterStart


        ) {
            Column(
                verticalArrangement = Arrangement.spacedBy(5.dp)
            ) {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(2.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {


                    Text(
                        "${state.totalPreviewWeekTransaction}",
                        style = MaterialTheme.typography.titleMedium,
                    )
                    Text(
                        "${state.selectedCurrencyUi.code}",
                        style = MaterialTheme.typography.titleSmall,
                    )
                }

                Text(
                    "Previous week",
                    style = MaterialTheme.typography.bodySmall,
                )
            }
        }
    }
}

@Preview
@Composable
private fun TransactionInfoPreview() {
    TransactionInfo(
        state = DashboardState(
            largestTransactionUi = TransactionUi(
                amount = 2500000.0,
                icon = CategoryUi.ENTERTAINMENT,
                note = "for debt",
                receiver = "Akmal",
                id = 1,

                )
        )
    )
}