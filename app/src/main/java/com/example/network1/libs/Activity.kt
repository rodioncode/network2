package com.example.network1.libs

import com.example.network1.R


import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.transform.CircleCropTransformation
import com.bumptech.glide.Glide
import com.example.network1.libs.network.RetrofitClient
import com.google.gson.Gson
import com.squareup.moshi.Json
import com.squareup.moshi.Moshi
import kotlinx.coroutines.launch
import kotlinx.serialization.Serializable

class ActivityProducts : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private val api = RetrofitClient.api

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_products)
        recyclerView = findViewById(R.id.productRecyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)

        lifecycleScope.launch {
            val products = api.getProducts()
            recyclerView.adapter = ProductAdapter(products) { product ->
                val intent = Intent(this@ActivityProducts, ProductDetailActivity::class.java)
                intent.putExtra("id", product.id)
                startActivity(intent)
            }
        }
        val jsonString = "{\"id\": \"1\", \"name\": \"John\", \"age\": 25}"

        val gson = Gson()
        val user: User = gson.fromJson(jsonString, User::class.java)
        val json: String = gson.toJson(user)

        data class User(val id: Int, val name: String)

        val moshi = Moshi.Builder().build()
        val adapter = moshi.adapter(User::class.java)
        val json2 = """{"id":1,"name":"Alex"}"""
        val user2 = adapter.fromJson(json2) // User(1, "Alex")
        val jsonBack = adapter.toJson(user2) // {"id":1,"name":"Alex"}

        val imageView: ImageView = findViewById(R.id.imageView)

        Glide.with(this)
            .load("https://example.com/image.jpg")
            .placeholder(R.drawable.ic_launcher_background)
            .error(R.drawable.ic_launcher_background)
            .circleCrop()
            .into(imageView)

        imageView.load("https://example.com/image.jpg") {
            placeholder(R.drawable.ic_launcher_background)
            error(R.drawable.ic_launcher_background)
            transformations(CircleCropTransformation())
        }
    }

    fun toJson() {
        val json = kotlinx.serialization.json.Json.encodeToString(User2(1, "Alex"))
        val user =
            kotlinx.serialization.json.Json.decodeFromString<User2>("""{"id":1,"name":"Alex"}""")
    }

}

@Serializable
data class User2(val id: Int, val name: String)
data class User(val id: String, val name: String, val age: Int)

val client = HttpClient(Android) {
    install(ContentNegotiation) {
        json()
    }
    install(Logging) {
        level = LogLevel.BODY
    }
}

val products: List<Product> = client.get("https://fakestoreapi.com/products")
