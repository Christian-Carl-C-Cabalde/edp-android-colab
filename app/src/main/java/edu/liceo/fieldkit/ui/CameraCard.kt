package edu.liceo.fieldkit.ui

import android.Manifest
import android.util.Log
import androidx.camera.core.Camera
import androidx.camera.core.ImageCapture
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import edu.liceo.fieldkit.hardware.*
import edu.liceo.fieldkit.permissions.rememberPermission
import java.io.File

@Composable
fun CameraCard() {
    val context = LocalContext.current
    val camera = rememberPermission(Manifest.permission.CAMERA)
    val capture = remember { ImageCapture.Builder().build() }
    var photo by remember { mutableStateOf<File?>(null) }

    // Bonus (TODO 14): the open camera, so we can drive the torch with it
    var cam by remember { mutableStateOf<Camera?>(null) }
    var torchOn by remember { mutableStateOf(false) }

    Card(Modifier.fillMaxWidth()) {
        Column(
            Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text("Field photo", style = MaterialTheme.typography.titleMedium)
            PermissionGate(                                      // GIVEN
                state = camera,
                feature = "Camera",
                reason = "We need the camera to photograph the issue you report."
            ) {
                // TODO 9a: the live preview, only while we hold the permission
                CameraPreview(capture, Modifier.fillMaxWidth().height(240.dp)) { cam = it }

                // TODO 9b: the shutter button
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    Button(onClick = { takePhoto(context, capture) { saved -> photo = saved } }) {
                        Text("Take photo")
                    }
                    // TODO 14a: the emulator has no flash, so this stays hidden there
                    if (cam?.cameraInfo?.hasFlashUnit() == true) {
                        Button(onClick = {
                            // TODO 14b: drive the torch through the open camera
                            torchOn = !torchOn
                            cam?.cameraControl?.enableTorch(torchOn)
                        }) {
                            // TODO 14c
                            Text(if (torchOn) "Torch off" else "Torch on")
                        }
                    }
                }

                // TODO 9c: confirm what was written, only after a real capture
                photo?.let { Text("Saved: ${it.name}") }

                // Bonus (TODO 13): shake to take a photo
                val shake = rememberAccelerometer()
                var lastShot by remember { mutableLongStateOf(0L) }
                LaunchedEffect(shake) {
                    val now = System.currentTimeMillis()
                    // TODO 13a: a shake, but not more than once every 1.5 s
                    if (isShake(shake) && now - lastShot > 1500) {
                        // TODO 13b: same capture as the button, plus a buzz and a log line
                        lastShot = now
                        takePhoto(context, capture) { photo = it }
                        context.buzz()
                        Log.d("FieldKit", "Shake capture")
                    }
                }
            }
            // GIVEN (read it, do not change it): thumbnail of the last photo
            photo?.let { f ->
                val thumb = remember(f) { loadThumb(f) }
                thumb?.let { Image(bitmap = it, contentDescription = "Last photo",
                    modifier = Modifier.size(96.dp)) }
            }
        }
    }
}
