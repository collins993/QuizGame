package io.github.collins993.quizgame.Model

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import java.io.BufferedInputStream
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import java.lang.StringBuilder
import java.net.HttpURLConnection
import java.net.URL
import kotlin.jvm.Throws

class DownloadingObject {

    @Throws(IOException::class)
    fun downloadJSONDataFromLink(link: String): String {


        val stringBuilder: StringBuilder = StringBuilder()

        val url: URL = URL(link)

        val urlConnection = url.openConnection() as HttpURLConnection

        try {

            val bufferedInputString: BufferedInputStream = BufferedInputStream(urlConnection.inputStream)

            val bufferedReader: BufferedReader = BufferedReader(InputStreamReader(bufferedInputString))

            //temporary string to hold each line read from the buffer reader
            var inputLineString: String?

            inputLineString = bufferedReader.readLine()

            while (inputLineString != null) {

                stringBuilder.append(inputLineString)

                inputLineString = bufferedReader.readLine()
            }

        } finally {
            urlConnection.disconnect()
        }

        return stringBuilder.toString()
    }

    fun downloadPlantPicture(pictureName: String?): Bitmap? {

        var bitmap: Bitmap? = null

        val pictureLink = PLANTPLACES_COM + "/photos/$pictureName"

        val pictureURL = URL(pictureLink)

        val inputStream = pictureURL.openConnection().getInputStream()

        if (inputStream != null) {

            bitmap = BitmapFactory.decodeStream(inputStream)
        }

        return bitmap;
    }

    companion object {
        val PLANTPLACES_COM = "http://www.plantplaces.com"
    }
}