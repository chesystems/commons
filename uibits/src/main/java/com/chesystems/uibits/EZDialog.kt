package com.chesystems.uibits

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ListItem
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import kotlinx.coroutines.delay

/** Shows a loading indicator for 3s then failure message for 2s */
@Composable
fun EZTestLoadingFailedDialog(setLoading: (Boolean) -> Unit) {
    var text by remember { mutableStateOf("Loading...") }
    EZDialog(noTitle = true, onDismiss = {}, onAccept = {}, noButton = true) {
        FullScreenText(
            text = text,
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(1f)
        ) {
            if (text == "Loading...") CircularProgressIndicator()
            RunOnce {
                delay(3000)
                text = "Failed!"
                delay(2000)
                setLoading(false)
            }
        }
    }
}

/** Simple dialog with optional title, content and accept/dismiss buttons */
@Composable
fun EZDialog(
    modifier: Modifier = Modifier,
    noButton: Boolean = false,
    defaultWidth: Boolean = true,
    title: String = "",
    noTitle: Boolean = false,
    subtitle: String? = null,
    onDismiss: () -> Unit,
    onAccept: () -> Unit = {},
    acceptEnabled: Boolean = true,
    content: @Composable (() -> Unit)? = null
) {
    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(
            usePlatformDefaultWidth = defaultWidth,
            decorFitsSystemWindows = false
        )
    ) {
        DialogSurface(modifier) {
            Column {
                if (!noTitle)
                    EZItem(title = title, subtitle = subtitle, size = 18.sp)
                content?.invoke()
                if (!noButton)
                    Column {
                        EZDivider()
                        Clickable(onClick = {
                            if(acceptEnabled)
                                onAccept()
                            onDismiss()
                        }) {
                            ListItem(
                                headlineContent = { Text("Accept", modifier = Modifier.fillMaxWidth(), textAlign = TextAlign.End) }
                            )
                        }
                    }
            }
        }
    }
}

/** Full screen dialog with customizable title bar and navigation */
@Composable
fun FullScreenDialog(
    modifier: Modifier = Modifier,
    title: String = "",
    onDismiss: () -> Unit,
    navIcon: ImageVector? = Icons.Default.Close,
    navAction: (() -> Unit)? = onDismiss,
    actions: @Composable (RowScope.() -> Unit) = {},
    content: @Composable () -> Unit
) {
    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(usePlatformDefaultWidth = false, decorFitsSystemWindows = false)
    ) {
        Surface(modifier.fillMaxSize()) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                EZTitleTextBar(
                    navigationIcon = {
                        if (navIcon != null && navAction != null) {
                            EZIconButton(navIcon) { navAction() }
                        }
                    },
                    title = title,
                    actions = actions
                )
                content()
            }
        }
    }
}