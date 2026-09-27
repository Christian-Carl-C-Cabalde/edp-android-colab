package edu.liceo.fieldkit.hardware

import android.content.Context
import android.graphics.BitmapFactory
import android.util.Log
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.core.content.ContextCompat
import java.io.File

fun takePhoto(context: Context, capture: ImageCapture, onSaved: (File) -> Unit) {
    // TODO 8a: the app's own cache folder, so no storage permission is needed
    val file = File(context.cacheDir, "shot_${System.currentTimeMillis()}.jpg")
    // TODO 8b: write straight to that file as a JPEG
    val options = ImageCapture.OutputFileOptions.Builder(file).build()
    // TODO 8c: the callback must run on the main thread
    capture.takePicture(
        options,
        ContextCompat.getMainExecutor(context),
        object : ImageCapture.OnImageSavedCallback {
            override fun onImageSaved(r: ImageCapture.OutputFileResults) {
                onSaved(file)
            }

            override fun onError(e: ImageCaptureException) {
                Log.e("FieldKit", "Capture failed", e)
            }
        }
    )
}

// GIVEN (read it, do not change it): load a small version of the photo
fun loadThumb(file: File): ImageBitmap? {
    val opts = BitmapFactory.Options().apply { inSampleSize = 8 }
    return BitmapFactory.decodeFile(file.path, opts)?.asImageBitmap()
}
