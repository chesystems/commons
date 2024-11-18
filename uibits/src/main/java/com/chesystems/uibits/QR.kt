package com.chesystems.uibits

import android.graphics.Bitmap
import android.graphics.Color
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.painter.BitmapPainter
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import com.chesystems.uibits.ImageSize
import com.chesystems.uibits.Spacing
import com.google.zxing.BarcodeFormat
import com.google.zxing.EncodeHintType
import com.google.zxing.common.BitMatrix
import com.google.zxing.qrcode.QRCodeWriter
import com.journeyapps.barcodescanner.ScanContract
import com.journeyapps.barcodescanner.ScanOptions
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Composable
fun QR(
    modifier: Modifier,  //Size, etc.
    text: String
) {
    val mod = remember { modifier.then(Modifier.aspectRatio(1f)) }
    if(text.isNotEmpty())
        Image(
            painter = rememberQrBitmapPainter(text),
            contentDescription = null,
            contentScale = ContentScale.FillBounds,
            modifier = mod)
    else Box(mod) {
        Text(text = "Empty")
    }
}

/**
 * USE:
 * val qrScanner = rememberQRScanner { setCode(it) }
 * qrScanner.launch(portraitScannerOption())
 * */
@Composable
fun rememberQRScanner(setQrValue: (String) -> Unit) =
    rememberLauncherForActivityResult(
        contract = ScanContract(),
        onResult = { result -> setQrValue(result.contents ?: "") })
fun portraitScannerOption(): ScanOptions = ScanOptions().setOrientationLocked(false)

@Composable
private fun rememberQrBitmapPainter(
    content: String,
    size: Dp = ImageSize.extraBig,
    padding: Dp = Spacing.none
): BitmapPainter {

    val density = LocalDensity.current
    val sizePx = with(density) { size.roundToPx() }
    val paddingPx = with(density) { padding.roundToPx() }

    val onSurface = MaterialTheme.colorScheme.onSurface.toArgb()
    val surface = Color.TRANSPARENT

    var bitmap by remember(content) {
        mutableStateOf<Bitmap?>(null)
    }

    LaunchedEffect(content) {
        if (bitmap == null) {
            launch(Dispatchers.IO) {
                val qrCodeWriter = QRCodeWriter()
                val encodeHints = mapOf(EncodeHintType.MARGIN to paddingPx)

                val bitmapMatrix = runCatching {
                    qrCodeWriter.encode(content, BarcodeFormat.QR_CODE, sizePx, sizePx, encodeHints)
                }.getOrNull()

                bitmap = createBitmapFromMatrix(bitmapMatrix, sizePx, onSurface, surface)
            }
        }
    }

    return remember(bitmap) {
        val currentBitmap = bitmap ?: Bitmap.createBitmap(sizePx, sizePx, Bitmap.Config.ARGB_8888).apply {
            eraseColor(Color.TRANSPARENT)
        }
        BitmapPainter(currentBitmap.asImageBitmap())
    }
}

// New function to create bitmap from matrix
private fun createBitmapFromMatrix(
    bitmapMatrix: BitMatrix?,
    sizePx: Int,
    onSurface: Int,
    surface: Int
): Bitmap {
    val matrixWidth = bitmapMatrix?.width ?: sizePx
    val matrixHeight = bitmapMatrix?.height ?: sizePx

    return Bitmap.createBitmap(matrixWidth, matrixHeight, Bitmap.Config.ARGB_8888).apply {
        val pixels = IntArray(matrixWidth * matrixHeight)

        for (x in 0 until matrixWidth) {
            for (y in 0 until matrixHeight) {
                pixels[y * matrixWidth + x] = if (bitmapMatrix?.get(x, y) == true) onSurface else surface
            }
        }

        setPixels(pixels, 0, matrixWidth, 0, 0, matrixWidth, matrixHeight)
    }
}