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
import androidx.compose.ui.unit.dp
import uz.akbarovdev.myexpenses.ui.theme.PrimaryFixed
import uz.akbarovdev.myexpenses.ui.theme.SecondaryFixed

@Composable
fun TransactionInfo(modifier: Modifier = Modifier) {
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
                        "Adobe Yearly",
                        style = MaterialTheme.typography.titleLarge,
                    )
                    Text(
                        "-\$59.99",
                        style = MaterialTheme.typography.titleLarge,
                    )
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
                10.dp,
            )
        )
        Box(
            modifier = Modifier
                .fillMaxHeight()
                .weight(0.3f)
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
                Text(
                    "-\$765.20",
                    style = MaterialTheme.typography.titleLarge,
                )

                Text(
                    "Previous week",
                    style = MaterialTheme.typography.bodySmall,
                )
            }
        }
    }
}