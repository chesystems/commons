package com.chesystems.uibits

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.FileOpen
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.scale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp

@Composable
fun OpenImage(
    aspectRatio: Float = 2f,
    resource: Int,
    onClick: () -> Unit
) {
    Surface(
        color = MaterialTheme.colorScheme.primary
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(aspectRatio)
        ) {
            Image(
                modifier = Modifier
                    .fillMaxSize()
                    .blur(Spacing.small),
                painter = painterResource(resource),
                contentDescription = ""
            )
            Surface(
                modifier = Modifier
                    .fillMaxSize()
                    .alpha(.15f),
                color = MaterialTheme.colorScheme.surface
            ) { }
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Surface(
                    shape = RoundedCornerShape(Spacing.big),
                    border = BorderStroke(1.dp, MaterialTheme.colorScheme.onSurface),
                    color = MaterialTheme.colorScheme.surface.copy(alpha = .5f)
                ) {
                    Clickable(onClick = onClick) {
                        Icon(
                            modifier = Modifier
                                .scale(2f)
                                .padding(Spacing.big * 2),
                            imageVector = Icons.Outlined.FileOpen,
                            contentDescription = "",
                            tint = MaterialTheme.colorScheme.onSurface
                        )
                    }
                }
            }
        }
    }
}