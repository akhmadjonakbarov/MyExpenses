package uz.akbarovdev.myexpenses.features.dashboard.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch
import uz.akbarovdev.myexpenses.R
import uz.akbarovdev.myexpenses.app.navigation.LocalDrawerState
import uz.akbarovdev.myexpenses.core.design_system.buttons.PrimaryIconButton

@Composable
fun DashboardHeader(
    exportClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val drawerState = LocalDrawerState.current
    val scope = rememberCoroutineScope()

    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(onClick = { scope.launch { drawerState.open() } }) {
            Icon(
                Icons.Default.Menu,
                contentDescription = "Menu",
                tint = MaterialTheme.colorScheme.onPrimary
            )
        }
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

        }
    }
}
