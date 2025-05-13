package com.example.network1.old

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.json.JSONObject
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL

class DogViewModel : ViewModel() {

    var imageUrl: StateFlow<String>? = null
    fun fetchRandomDogImage(onResult: (String?) -> Unit) {
        viewModelScope.launch {
            val imageUrl = withContext(Dispatchers.IO) {
                val url = URL("https://dog.ceo/api/breeds/image/random")
                val connection = url.openConnection() as HttpURLConnection
                try {
                    connection.requestMethod = "GET"
                    connection.connectTimeout = 5000
                    connection.readTimeout = 5000
                    val responseCode = connection.responseCode
                    if (responseCode == HttpURLConnection.HTTP_OK) {
                        val reader = BufferedReader(InputStreamReader(connection.inputStream))
                        val response = reader.use { it.readText() }

                        val jsonObject = JSONObject(response)
                        jsonObject.getJSONArray("")
                        jsonObject.getString("message")
                    } else {
                        null
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                    null
                } finally {
                    connection.disconnect()
                }
            }
            onResult(imageUrl)
        }
    }
}
