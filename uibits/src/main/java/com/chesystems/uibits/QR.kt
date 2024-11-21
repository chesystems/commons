package com.chesystems.uibits

import android.content.ContentValues
import android.content.Context
import android.graphics.Bitmap
import android.graphics.Color
import android.os.Environment
import android.provider.MediaStore
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
import kotlinx.coroutines.withContext
import java.io.File
import java.io.FileOutputStream

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





















fun genQR(
    content: String,
    sizePx: Int = 512,
    paddingPx: Int = 0
): Bitmap {
    val qrCodeWriter = QRCodeWriter()
    val encodeHints = mapOf(EncodeHintType.MARGIN to paddingPx)

    // Generate the QR code matrix
    val bitmapMatrix: BitMatrix = qrCodeWriter.encode(content, BarcodeFormat.QR_CODE, sizePx, sizePx, encodeHints)
    val bitmap = Bitmap.createBitmap(sizePx, sizePx, Bitmap.Config.ARGB_8888)

    // Create the bitmap from the matrix
    for (x in 0 until sizePx) {
        for (y in 0 until sizePx) {
            bitmap.setPixel(x, y, if (bitmapMatrix[x, y]) Color.BLACK else Color.WHITE)
        }
    }

    return bitmap
}

/** OLD */
fun saveBitmapToLocalStorage(bitmap: Bitmap, fileName: String) {
    val file = File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), fileName)
    FileOutputStream(file).use { out ->
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, out) // Save as PNG
    }
}

/** NEW - USES MEDIA STORE */
suspend fun saveQR(
    bitmap: Bitmap,
    context: Context,
    filename: String
) {
    withContext(Dispatchers.IO) {
        val resolver = context.contentResolver
        val imageCollection = MediaStore.Images.Media.getContentUri(
            MediaStore.VOLUME_EXTERNAL_PRIMARY
        )
        val timeInMillis = System.currentTimeMillis()
        val imageContentValues = ContentValues().apply {
            put(MediaStore.Images.Media.DISPLAY_NAME, "${filename}_${timeInMillis}_qr.jpg")
            put(MediaStore.MediaColumns.RELATIVE_PATH, Environment.DIRECTORY_PICTURES)
            put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg")
            put(MediaStore.Images.Media.DATE_TAKEN, timeInMillis)
            put(MediaStore.Images.Media.IS_PENDING, 1)
        }

        val imageMediaStoreUri = resolver.insert(imageCollection, imageContentValues)
        imageMediaStoreUri?.let { uri ->
            try {
                resolver.openOutputStream(uri)?.let { outputStream ->
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream)
                }

                imageContentValues.clear()
                imageContentValues.put(MediaStore.MediaColumns.IS_PENDING, 0)

                resolver.update(uri, imageContentValues, null, null)
            } catch(e: Exception) {
                e.printStackTrace()
                resolver.delete(uri, null, null)
            }
        }
    }
}