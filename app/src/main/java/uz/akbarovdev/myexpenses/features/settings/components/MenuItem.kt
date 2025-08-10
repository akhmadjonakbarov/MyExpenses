package uz.akbarovdev.myexpenses.features.settings.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import uz.akbarovdev.myexpenses.core.design_system.common_components.IconBox

@Composable
fun MenuItem(
    modifier: Modifier = Modifier,
    text: String,
    icon: ImageVector,
    iconColor: Color = LocalContentColor.current,
    containerColor: Color = MaterialTheme.colorScheme.surfaceContainerLowest,
    style: TextStyle = MaterialTheme.typography.labelLarge,
    textColor: Color = MaterialTheme.colorScheme.onSurface,
    onClick: () -> Unit,
) {
    Row(
        modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconBox(
            modifier = Modifier.size(40.dp),
            icon = icon,
            iconSize = 25.dp,
            containerColor = containerColor,
            iconColor = iconColor,
        )
        Spacer(
            Modifier.width(5.dp)
        )
        Text(
            text,
            style = style,
            color = textColor
        )
    }
}
