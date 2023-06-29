package com.cd.utility.cache_dir

import android.content.Context
import android.graphics.Bitmap
import com.cd.utility.R
import java.io.File
import java.text.SimpleDateFormat
import java.util.*

 fun File.createFileAndDirs() = apply {
    parentFile?.mkdirs()
    createNewFile()
}

fun File.write(
    bitmap: Bitmap,
    format: Bitmap.CompressFormat = Bitmap.CompressFormat.JPEG,
    quality: Int = 80
) = apply {
    createFileAndDirs()
    outputStream().use {
        bitmap.compress(format, quality, it)
        it.flush()
    }
}

 fun getOutputDirectory(context: Context,fileName : String): File {
    val mediaDir = context.externalCacheDir?.let {
        File(it, fileName).apply { mkdirs() }
    }
    return if (mediaDir != null && mediaDir.exists()) mediaDir else context.filesDir
}

fun getImageFileName(context: Context,fileName : String,imageExtension : String = ".jpg"): File {
    return File( getOutputDirectory(context = context,fileName = fileName), SimpleDateFormat("yyyy-MM-dd-HH-mm-ss-SSS", Locale.US).format(System.currentTimeMillis()) + imageExtension)
}

/**
 * Recursively deletes all the contents of a directory except the directory itself.
 * Throws IllegalStateException is called on a file.
 */
fun File.deleteDirContents() {
    if (!isDirectory) throw IllegalStateException()
    walk().filterNot { it.absolutePath == absolutePath }.forEach { it.deleteRecursively() }
}