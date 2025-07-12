package uz.akbarovdev.myexpenses.core.design_system.common_components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import uz.akbarovdev.myexpenses.ui.theme.PrimaryFixed

@Composable
fun TextBox(
    modifier: Modifier = Modifier,
    containerColor: Color = PrimaryFixed,
    text: String,
    textColor: Color = LocalContentColor.current,
    textStyle: TextStyle = MaterialTheme.typography.labelLarge,
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
        Text(
            text,
            style = textStyle,
            color = textColor
        )
    }
}


@Preview
@Composable
private fun TextBoxPreview() {
    TextBox(
        text = "😊",
        modifier = Modifier.size(40.dp),
        containerColor = Color.Red,
    )
}