package com.example.myfood.rest

import okhttp3.MediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody

class RESTRequest {
    companion object {
        fun request(url: String, callback: (String?) -> Unit) {
            Thread {
                val client = OkHttpClient()
                val mediaType = MediaType.parse("application/json; charset=utf-8")
                val body = RequestBody.create(
                    mediaType,
                    "urls=https%3A%2F%2Fupload.wikimedia.org%2Fwikipedia%2Fcommons%2F5%2F5a%2FPovray_hello_world.png"
                )
                val request = Request.Builder()
                    .url(url)
                    .post(body)
                    .build()

                val response = client.newCall(request).execute()
                callback(response.body()?.string())
            }.start()
        }
    }
}