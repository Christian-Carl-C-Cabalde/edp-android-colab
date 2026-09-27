package edu.liceo.fieldkit.hardware

import android.annotation.SuppressLint
import android.content.Context
import android.location.Location
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import com.google.android.gms.tasks.CancellationTokenSource

@SuppressLint("MissingPermission")   // GIVEN: LocationCard checks the permission first
fun Context.currentLocation(onResult: (Location?) -> Unit) {
    val client = LocationServices.getFusedLocationProviderClient(this)
    // TODO 10a: one high accuracy fix, not a stream; a field report pays for accuracy once
    client.getCurrentLocation(Priority.PRIORITY_HIGH_ACCURACY, CancellationTokenSource().token)
        // TODO 10b: loc is null when Location is off or there is no fix yet
        .addOnSuccessListener { loc -> onResult(loc) }
        // TODO 10c: never crash, just report that we do not know where we are
        .addOnFailureListener { onResult(null) }
}
