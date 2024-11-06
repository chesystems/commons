package com.chesystems.uibits

import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.platform.LocalContext

/** Recursively traverses context hierarchy to find activity */
private fun Context.findActivity(): Activity? = when (this) {
    is Activity -> this
    is ContextWrapper -> baseContext.findActivity()
    else -> null
}

/** Temporarily sets screen orientation, restoring original on dispose */
@Composable
fun LockScreenOrientation(orientation: Int) {
    val context = LocalContext.current
    DisposableEffect(orientation) {
        val activity = context.findActivity() ?: return@DisposableEffect onDispose {}
        val originalOrientation = activity.requestedOrientation
        activity.requestedOrientation = orientation
        onDispose {
            activity.requestedOrientation = originalOrientation
        }
    }
}

/** Sample code showing orientation-specific UI handling */
/*
@Composable
fun LandscapeTest() {
    val conf = LocalConfiguration.current
    conf.orientation = Configuration.ORIENTATION_LANDSCAPE
    when(conf.orientation) {
        Configuration.ORIENTATION_LANDSCAPE -> {

        }
        Configuration.ORIENTATION_PORTRAIT -> {

        }
        Configuration.ORIENTATION_UNDEFINED -> {

        }
    }
}
*/