package uz.akbarovdev.myexpenses.core.design_system.common_components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import uz.akbarovdev.myexpenses.ui.theme.PrimaryFixed


@Composable
fun IconBox(
    modifier: Modifier = Modifier,
    containerColor: Color = PrimaryFixed,
    icon: ImageVector,
    iconColor: Color = LocalContentColor.current,
    iconContentDescription: String? = null,
    iconSize: Dp = 35.dp // 👈 Add this
) {
    Box(
        modifier = modifier
            .defaultMinSize(50.dp)
            .background(
                color = containerColor,
                shape = RoundedCornerShape(12.dp)
            ),
        contentAlignment = Alignment.Center
    ) {
        Icon(
            imageVector = icon,
            contentDescription = iconContentDescription,
            tint = iconColor,
            modifier = Modifier.size(iconSize) // ✅ flexible
        )
    }
}



@Preview
@Composable
private fun IconBoxPreview() {

}