package com.myfood.util

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import java.io.ByteArrayOutputStream
import java.util.*

class UtilTool {
    companion object{

        //Metodo static para pasar de bitmap a base64
        fun bitmapToBase64(bitmap: Bitmap): String{

            //Redimensionamos la imagen para que se adapte a las dimensiones del imageView y pese
            //menos
            val bitmapResized = getResizedBitmap(bitmap, 350)

            //Creamos un outputStream de tipo ByteArray
            val stream = ByteArrayOutputStream()

            //Llenamos el outputstream con el bitmap con calidad 100%
            bitmapResized.compress(Bitmap.CompressFormat.PNG, 100, stream)

            //Transformamos el outputstream a bytearray
            val byteArray = stream.toByteArray()

            //Transformamos el byteArray a String en codificación base64
            return Base64.getEncoder().encodeToString(byteArray)
        }


        //Metodo static para pasar de base64 a bitmap
        fun base64ToBitmap(base64: String): Bitmap{

            //Descodificamos el String en base 64 a byteArray
            val decoded = Base64.getDecoder().decode(base64)

            //Transformamos el byteArray a bitmap
            return BitmapFactory.decodeByteArray(decoded, 0, decoded.size)

        }

        fun getResizedBitmap(image: Bitmap, maxSize: Int): Bitmap {
            var width = image.width
            var height = image.height
            val bitmapRatio = width.toFloat() / height.toFloat()
            if (bitmapRatio > 1) {
                width = maxSize
                height = (width / bitmapRatio).toInt()
            } else {
                height = maxSize
                width = (height * bitmapRatio).toInt()
            }
            return Bitmap.createScaledBitmap(image, width, height, true)
        }
    }
}