package uz.akbarovdev.myexpenses.features.debt.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import uz.akbarovdev.myexpenses.R
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.input.OffsetMapping
import androidx.compose.ui.text.input.TransformedText
import androidx.compose.ui.text.input.VisualTransformation

@Composable
fun DebtUserDialog(
    name: String,
    phone: String,
    isEditing: Boolean,
    onNameChange: (String) -> Unit,
    onPhoneChange: (String) -> Unit,
    onSave: () -> Unit,
    onDismiss: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        containerColor = MaterialTheme.colorScheme.surfaceContainerLowest,
        shape = RoundedCornerShape(28.dp),
        title = {
            Row(
                Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    if (isEditing) stringResource(R.string.edit) else stringResource(R.string.add),
                    fontWeight = FontWeight.Bold
                )
                IconButton(onClick = onDismiss) { Icon(Icons.Default.Close, null) }
            }
        },
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                OutlinedTextField(
                    value = name, onValueChange = onNameChange,
                    label = { Text(stringResource(R.string.name)) },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp)
                )
                OutlinedTextField(
                    value = phone,
                    onValueChange = onPhoneChange,
                    label = { Text(stringResource(R.string.phone_number)) },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
                    visualTransformation = PhoneVisualTransformation()
                )
            }
        },
        confirmButton = {
            Button(
                onClick = onSave,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp),
                shape = RoundedCornerShape(16.dp)
            ) {
                Text(
                    if (isEditing) stringResource(R.string.save) else stringResource(R.string.add),
                    fontWeight = FontWeight.Bold
                )
            }
        }
    )
}


class PhoneVisualTransformation : VisualTransformation {
    override fun filter(text: AnnotatedString): TransformedText {
        val trimmed = if (text.text.length >= 9) text.text.substring(0, 9) else text.text
        val originalLength = trimmed.length

        val groups = listOf(2, 3, 2, 2)
        val out = StringBuilder()
        var pos = 0
        for (len in groups) {
            if (pos >= originalLength) break
            val end = (pos + len).coerceAtMost(originalLength)
            if (out.isNotEmpty()) out.append(" ")
            out.append(trimmed.substring(pos, end))
            pos = end
        }

        val phoneNumberOffsetMapping = object : OffsetMapping {
            override fun originalToTransformed(offset: Int): Int {
                if (offset <= 0) return 0
                var ti = 0
                var oi = 0
                for (len in groups) {
                    for (j in 0 until len) {
                        if (oi == offset) return ti.coerceAtMost(out.length)
                        oi++
                        ti++
                        if (oi >= originalLength) break
                    }
                    if (oi >= originalLength) break
                    ti++
                }
                return out.length
            }

            override fun transformedToOriginal(offset: Int): Int {
                if (offset <= 0) return 0
                var ti = 0
                var oi = 0
                for (len in groups) {
                    for (j in 0 until len) {
                        if (ti == offset) return oi.coerceAtMost(originalLength)
                        oi++
                        ti++
                        if (oi >= originalLength) break
                    }
                    if (oi >= originalLength) break
                    if (ti == offset) return oi.coerceAtMost(originalLength)
                    ti++
                }
                return originalLength
            }
        }

        return TransformedText(AnnotatedString(out.toString()), phoneNumberOffsetMapping)
    }
}