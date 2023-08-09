package com.cd.utility.permissions

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.provider.Settings
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat

/**
 * Returns true if user granted all permissions else false
 */

fun Context.hasPermissions(vararg permissions: String): Boolean {
    //We do not need WRITE_EXTERNAL_STORAGE permission in RED VELVET Cake and higher version devices.
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
        val mPermissions = permissions.filterNot { it == android.Manifest.permission.WRITE_EXTERNAL_STORAGE }
        return mPermissions.all {
            ActivityCompat.checkSelfPermission(this, it) == PackageManager.PERMISSION_GRANTED
        }
    }
    return permissions.all {
        ActivityCompat.checkSelfPermission(this, it) == PackageManager.PERMISSION_GRANTED
    }
}

/**
 * Requests user required permissions
 */
fun Context.requestPermissions(requestCode: Int, vararg permissions: String) {
    ActivityCompat.requestPermissions(this as Activity, permissions, requestCode)
}

/**
 * Requests user required permissions if user has not granted yet
 */
fun Context.requestPermissionsIfNotGranted(requestCode: Int, vararg permissions: String)  {
    var permissionText = ""
    val dontAskAgain = permissions.any {
        permissionText = it
        ActivityCompat.shouldShowRequestPermissionRationale(this as AppCompatActivity, it)
    }
    if (dontAskAgain && permissions.none { it == android.Manifest.permission.ACCESS_BACKGROUND_LOCATION }){
        showPermRationale(this as AppCompatActivity,permissionText)
        return
    }

    if (!hasPermissions(*permissions))
        ActivityCompat.requestPermissions(this as Activity, permissions, requestCode)

}

private fun showPermRationale(appCompatActivity: AppCompatActivity, permissionText: String) {
    var locationMessage = ""
    when(permissionText){
        android.Manifest.permission.ACCESS_BACKGROUND_LOCATION ->
            locationMessage = "Allow Background Location all the time, Please go to app settings. "
        android.Manifest.permission.ACCESS_FINE_LOCATION ->
            locationMessage = "Allow Location while you are using the app, Please go to app settings. "
        android.Manifest.permission.ACCESS_COARSE_LOCATION ->
            locationMessage = "Allow Location while you are using the app, Please go to app settings. "
        android.Manifest.permission.READ_PHONE_STATE ->
            locationMessage = "Allow Read phone while you are using the app, Please go to app settings. "
        android.Manifest.permission.CALL_PHONE ->
            locationMessage = "Allow Call phone while you are using the app, Please go to app settings. "
        android.Manifest.permission.CAMERA ->
            locationMessage = "Allow Camera while you are using the app, Please go to app settings. "
        android.Manifest.permission.READ_EXTERNAL_STORAGE ->
            locationMessage = "Allow Read Storage while you are using the app, Please go to app settings. "
        android.Manifest.permission.WRITE_EXTERNAL_STORAGE ->
            locationMessage = "Allow Write Storage while you are using the app, Please go to app settings. "
        else ->
            locationMessage = "Allow Permissions while you are using the app, Please go to app settings. "


    }

   AlertDialog.Builder(appCompatActivity)
        .setTitle("Permission Needed")
        .setMessage(locationMessage)
        .setPositiveButton(
            "OK"
        ) { _, _ ->
            //Prompt the user once explanation has been shown
            val intent = Intent()
            intent.action = Settings.ACTION_APPLICATION_DETAILS_SETTINGS
            val uri = Uri.fromParts(
                "package",appCompatActivity.packageName, null)
            intent.data = uri
            appCompatActivity.startActivity(intent)
        }
        .setCancelable(false)
        .create()
        .show()
}
