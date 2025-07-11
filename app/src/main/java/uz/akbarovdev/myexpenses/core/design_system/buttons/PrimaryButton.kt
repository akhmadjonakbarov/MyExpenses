package uz.akbarovdev.myexpenses.core.design_system.buttons

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp

@Composable
fun PrimaryButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    content: @Composable () -> Unit,
) {
    Button(
        modifier = modifier,
        shape = RoundedCornerShape(10.dp), // 👈 Rounded corners properly applied here
        colors = ButtonDefaults.buttonColors(
            containerColor = MaterialTheme.colorScheme.primary // 👈 Correct background color
        ),
        contentPadding = PaddingValues(0.dp),
        onClick = onClick
    ) {
        content()
    }
}
