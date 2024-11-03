package com.chesystemsdev.uibits

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import kotlinx.coroutines.delay

/** Displays large title text with customizable alignment */
@Composable
fun Title(text: String, align: TextAlign? = null) {
    Text(
        text = text,
        fontWeight = FontWeight.Medium,
        style = MaterialTheme.typography.titleLarge,
        textAlign = align
    )
}

/**
 * Creates a typewriter animation effect by displaying text one character at a time
 * with randomized delays between characters for a natural feel
 */
@Composable
fun WriterText(
    fullText: String,
    minDelay: Int = 50,
    maxDelay: Int = 150,
    returnChar: (Char) -> Unit,
    atCompletion: () -> Unit = {}
) {
    var randomDelay by remember { mutableIntStateOf((minDelay..maxDelay).random()) }
    // Delay between characters is randomized between minDelay and maxDelay
    // to create a more natural typing effect
    RunOnce {
        fullText.forEach { char ->
            randomDelay = (minDelay..maxDelay).random()
            delay(randomDelay.toLong())
            returnChar(char)
        }
        atCompletion()
    }
}
