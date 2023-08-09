package com.cd.utility.utils

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.exifinterface.media.ExifInterface
import android.graphics.Matrix
import android.net.Uri
import com.cd.utility.R
import com.cd.utility.toast_model.showToast
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import kotlin.math.floor

fun getCompressedImageFile(context: Context, fileName: String, uri: Uri): File? {
    return convertByteArrayToFile(context, fileName, compressImageByteArray(context, uri))
}

fun convertByteArrayToFile(context: Context, fileName: String, imageBytes: ByteArray): File? {
    if (fileName.isEmpty()) {
        context.showToast(context.getString(R.string.file_name_must_not_be_empty))
        return null
    }
    return try {
        val file = File(
            context.externalCacheDir,
            fileName.replace(".jpeg", "_compressed.jpeg")
        )
        file.createNewFile()

        val out = FileOutputStream(file)
        out.write(imageBytes)
        out.close()
        file

    } catch (e: IOException) {
        e.printStackTrace()
        context.showToast(context.getString(R.string.something_went_wrong_try_again_later))
        null
    }
}

fun compressImageByteArray(context: Context, uri: Uri): ByteArray {
    val maxAttachmentSize = 1024 * 1024 // max final file size
    val scaleSize = 1024
    var bmpPic: Bitmap?
    var bmpPicByteArray: ByteArray? = null
    try {
        bmpPic = getThumbnail(context, uri)
        if (bmpPic != null) {
            val originalWidth = bmpPic.width
            val originalHeight = bmpPic.height

            var newWidth = -1
            var newHeight = -1
            val multiFactor: Float
            if (originalHeight > scaleSize || originalWidth > scaleSize) {
                when {
                    originalHeight > originalWidth -> {
                        newHeight = scaleSize
                        multiFactor = originalWidth.toFloat() / originalHeight.toFloat()
                        newWidth = (newHeight * multiFactor).toInt()
                    }
                    originalWidth > originalHeight -> {
                        newWidth = scaleSize
                        multiFactor = originalHeight.toFloat() / originalWidth.toFloat()
                        newHeight = (newWidth * multiFactor).toInt()
                    }
                    originalHeight == originalWidth -> {
                        newHeight = scaleSize
                        newWidth = scaleSize
                    }
                }
                bmpPic = Bitmap.createScaledBitmap(bmpPic, newWidth, newHeight, false)
            }
            var compressQuality =
                104 // decreasing quality by 5% on each loop. (starting from 99%)
            var streamLength = maxAttachmentSize + 1
            while (streamLength > maxAttachmentSize) {
                val bmpStream = ByteArrayOutputStream()
                compressQuality -= 5

                bmpPic!!.compress(Bitmap.CompressFormat.JPEG, compressQuality, bmpStream)
                bmpPicByteArray = bmpStream.toByteArray()
                streamLength = bmpPicByteArray.size
            }
        }
    } catch (e: IOException) {
        e.printStackTrace()
        context.showToast(context.getString(R.string.image_compression_failed))
    }
    return bmpPicByteArray ?: ByteArray(1)
}

fun getThumbnail(context: Context, uri: Uri, thumbnailSIze: Int = 512): Bitmap? {
    var input = context.contentResolver.openInputStream(uri)
    val onlyBoundsOptions = BitmapFactory.Options()
    onlyBoundsOptions.inJustDecodeBounds = true
    onlyBoundsOptions.inDither = true //optional
    onlyBoundsOptions.inPreferredConfig = Bitmap.Config.ARGB_8888 //optional
    BitmapFactory.decodeStream(input, null, onlyBoundsOptions)
    input!!.close()
    if (onlyBoundsOptions.outWidth == -1 || onlyBoundsOptions.outHeight == -1) return null
    val originalSize =
        if (onlyBoundsOptions.outHeight > onlyBoundsOptions.outWidth) onlyBoundsOptions.outHeight else onlyBoundsOptions.outWidth
    val ratio =
        if (originalSize > thumbnailSIze) originalSize / thumbnailSIze.toDouble() else 1.0
    val bitmapOptions = BitmapFactory.Options()
    bitmapOptions.inSampleSize = getPowerOfTwoForSampleRatio(ratio)
    bitmapOptions.inDither = true //optional
    bitmapOptions.inPreferredConfig = Bitmap.Config.ARGB_8888 //optional
    input = context.contentResolver.openInputStream(uri)
    var bitmap = BitmapFactory.decodeStream(input, null, bitmapOptions)
    bitmap = rotateBitmap(uri, bitmap ?: return null)
    input!!.close()
    return bitmap
}

fun getPowerOfTwoForSampleRatio(ratio: Double): Int {
    val k = Integer.highestOneBit(floor(ratio).toInt())
    return if (k == 0) 1 else k
}

fun rotateBitmap(uri: Uri, bitmap: Bitmap) : Bitmap? {
    try {
        val exifInterface = ExifInterface(uri.path!!)
        val orientation: Int = exifInterface.getAttributeInt(
            ExifInterface.TAG_ORIENTATION,
            ExifInterface.ORIENTATION_UNDEFINED
        )
        return when (orientation) {
            ExifInterface.ORIENTATION_TRANSPOSE, ExifInterface.ORIENTATION_ROTATE_90 -> rotateBitmap(bitmap, 90f)
            ExifInterface.ORIENTATION_ROTATE_180 -> rotateBitmap(bitmap, 180f)
            ExifInterface.ORIENTATION_ROTATE_270 -> rotateBitmap(bitmap, 270f)
            ExifInterface.ORIENTATION_NORMAL -> bitmap
            else -> bitmap
        }

        // Use the rotatedBitmap for further processing or display
    } catch (e: Exception) {
        e.printStackTrace()
    }
    return null
}

// Method to rotate the bitmap
fun rotateBitmap(bitmap: Bitmap, degrees: Float): Bitmap? {
    val matrix = Matrix()
    matrix.postRotate(degrees)
    return Bitmap.createBitmap(bitmap, 0, 0, bitmap.width, bitmap.height, matrix, true)
}
