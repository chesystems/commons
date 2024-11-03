package com.chesystemsdev.uibits

import android.graphics.Rect
import android.view.ViewTreeObserver
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.ime
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalView
import androidx.navigation.NavHostController
import kotlinx.coroutines.CoroutineScope
import java.util.UUID

/** Gets random elements from a collection */
fun <T> Collection<T>.preList(
    iterations: Int,
    allowRepeat: Boolean = true
) = when {
    isEmpty() -> emptyList()
    allowRepeat -> List(iterations) { random() }
    iterations > size -> shuffled()
    else -> shuffled().take(iterations)
}

/** Runs composable if collection is not empty */
@Composable
fun NotEmpty(a: Collection<*>, action: @Composable () -> Unit) {
    if (a.isNotEmpty()) action()
}

/** Runs composable if value exists */
@Composable
fun <T> NonNull(a: Any?, action: @Composable (T) -> Unit) {
    a?.let { action(it as T) }
}

/** Formats numbers with truncation at 99+ */
fun truncateFormat(n: Int) = when {
    n == 0 -> null
    n <= 99 -> "$n"
    else -> "+99"
}

/** Navigates with pop-up destination */
fun NavHostController.navigatePop(screen: String) {
    navigate(screen, screen)
}

/** Navigates with optional pop-up */
fun NavHostController.navigate(screen: String, popTo: String? = null) {
    navigate(screen) {
        popTo?.let { popUpTo(it) { inclusive = false } }
    }
}

/** Adds keyboard insets padding */
@Composable
fun Modifier.imeInsets() = windowInsetsPadding(WindowInsets.ime)

/** Gets alternating colors for list items */
@Composable
fun colorByIndex(l: List<Any>, li: Any, swap: Boolean = false): Color {
    val isOdd = l.indexOf(li) % 2 != 0
    return if (isOdd && !swap) MaterialTheme.colorScheme.surface
    else MaterialTheme.colorScheme.primary.copy(alpha = 0.033f)
}

/** Runs once at composition start */
@Composable
fun RunOnce(action: suspend CoroutineScope.() -> Unit) = 
    LaunchedEffect(Unit) { action() }

enum class Keyboard {
    Opened, Closed
}

/** Tracks keyboard visibility state */
@Composable
fun keyboardAsState(): State<Keyboard> {
    val keyboardState = remember { mutableStateOf(Keyboard.Closed) }
    val view = LocalView.current

    DisposableEffect(view) {
        val onGlobalListener = ViewTreeObserver.OnGlobalLayoutListener {
            val rect = Rect()
            view.getWindowVisibleDisplayFrame(rect)
            val screenHeight = view.rootView.height
            val keypadHeight = screenHeight - rect.bottom
            keyboardState.value = if (keypadHeight > screenHeight * 0.15) {
                Keyboard.Opened
            } else {
                Keyboard.Closed
            }
        }
        view.viewTreeObserver.addOnGlobalLayoutListener(onGlobalListener)
        onDispose {
            view.viewTreeObserver.removeOnGlobalLayoutListener(onGlobalListener)
        }
    }
    return keyboardState
}

/** Generates UUID with optional shortening */
fun newUUID(short: Boolean = true) = 
    UUID.randomUUID().toString().let { if(short) it.take(5) else it }