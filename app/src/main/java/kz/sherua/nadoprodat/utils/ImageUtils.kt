package kz.sherua.nadoprodat.utils

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import java.io.File

class ImageUtils {
    companion object {
        fun getBitmapFromPath(imagePath: String): Bitmap? {
            val imageFile = File(imagePath)
            return if (imageFile.exists()) {
                BitmapFactory.decodeFile(imageFile.absolutePath)
            } else {
                null
            }
        }
    }
}