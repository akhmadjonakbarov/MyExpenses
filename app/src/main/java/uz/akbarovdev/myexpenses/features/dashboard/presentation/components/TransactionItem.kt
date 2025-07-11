package uz.akbarovdev.myexpenses.features.dashboard.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import uz.akbarovdev.myexpenses.ui.theme.PrimaryFixed

@Composable
fun TransactionItem(modifier: Modifier = Modifier) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Box(
                Modifier
                    .size(50.dp)
                    .background(
                        color = PrimaryFixed, shape = RoundedCornerShape(
                            12.dp
                        )
                    ), contentAlignment = Alignment.Center
            ) {
                Icon(
                    modifier = Modifier.size(35.dp),
                    imageVector = Icons.Default.Home,
                    contentDescription = null,
                )
            }
            Column(
                verticalArrangement = Arrangement.Center
            ) {
                Text("Amazon", style = MaterialTheme.typography.titleMedium)
                Text(
                    "Home", style = MaterialTheme.typography.bodyMedium
                )
            }
        }
        Text("-\$45.99", style = MaterialTheme.typography.titleLarge)
    }
}