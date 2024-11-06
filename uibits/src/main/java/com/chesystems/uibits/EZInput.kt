package com.chesystems.uibits

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Clear
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

/**
 * A beautiful text input field with character limit and error handling.
 * Features a clean design with rounded corners and optional trailing component.
 */
@Composable
fun EZInput(
    singleLine: Boolean = true,
    name: String,
    setName: (String) -> Unit,
    maxLength: Int = Int.MAX_VALUE,
    label: String,
    textAlign: TextAlign = TextAlign.Center,
    trailing: @Composable (() -> Unit)? = null
) {
    val shape = remember { RoundedCornerShape(Corner.big) }
    var isError by remember { mutableStateOf(false) }
    val scope = rememberCoroutineScope()

    // Main container with optional trailing component
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(Spacing.small)
    ) {
        // Input field container with border
        Surface(
            border = BorderStroke(1.dp, MaterialTheme.colorScheme.primary),
            shape = shape,
            modifier = Modifier.weight(1f)
        ) {
            // Customized text field with character limit and error handling
            TextField(
                value = name,
                onValueChange = { input ->
                    if (input.length < maxLength) setName(input)
                    else if (!isError) scope.launch {
                        isError = true
                        delay(500)
                        isError = false
                    }
                },
                placeholder = { Text(label, textAlign = textAlign, modifier = Modifier.fillMaxWidth()) },
                shape = shape,
                colors = TextFieldDefaults.colors(
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    focusedContainerColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.067f),
                    unfocusedContainerColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.033f)
                ),
                singleLine = singleLine,
                maxLines = if (singleLine) 1 else 3,
                textStyle = LocalTextStyle.current.copy(textAlign = textAlign),
                trailingIcon = if (name.isNotEmpty()) {
                    { NullableTrailingIcon(name, setName) }
                } else null,
                isError = isError
            )
        }
        trailing?.invoke()
    }
}

/**
 * A sleek clear button that appears when text is present.
 * Provides a simple way to reset the input field.
 */
@Composable
private fun NullableTrailingIcon(name: String, setName: (String) -> Unit) {
    Row {
        EZIconButton(icon = Icons.Outlined.Clear) { setName("") }
        Spacer(modifier = Modifier.width(Spacing.extraSmall))
    }
}