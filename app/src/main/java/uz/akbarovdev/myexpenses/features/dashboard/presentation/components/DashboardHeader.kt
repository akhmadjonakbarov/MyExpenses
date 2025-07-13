package uz.akbarovdev.myexpenses.features.dashboard.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import uz.akbarovdev.myexpenses.R
import uz.akbarovdev.myexpenses.core.design_system.buttons.PrimaryIconButton

@Composable
fun DashboardHeader(
    goSettingsClick: () -> Unit,
    exportClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            "username",
            style = MaterialTheme.typography.titleLarge,
            color = MaterialTheme.colorScheme.onPrimary
        )
        Row(
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            PrimaryIconButton(
                imageVector = ImageVector.vectorResource(R.drawable.download_icon),
                contentDescription = "Download",
                onClick = exportClick,
            )
            PrimaryIconButton(
                imageVector = ImageVector.vectorResource(R.drawable.settings),
                contentDescription = "Settings",
                onClick = goSettingsClick,
            )
        }
    }
}