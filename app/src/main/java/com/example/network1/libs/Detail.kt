package com.example.network1.libs
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.example.network1.R
import com.example.network1.libs.network.RetrofitClient
import kotlinx.coroutines.launch

class ProductDetailActivity : AppCompatActivity() {

    private val api = RetrofitClient.api

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.details)

        val id = intent.getIntExtra("id", -1)
        if (id != -1) {
            lifecycleScope.launch {
                val product = api.getProduct(id)
                findViewById<TextView>(R.id.titleTextView).text = product.title
                findViewById<TextView>(R.id.priceTextView).text = "$${product.price}"
                findViewById<TextView>(R.id.descriptionTextView).text = product.description
                Glide.with(this@ProductDetailActivity)
                    .load(product.image)
                    .into(findViewById(R.id.imageView))
            }
        }
    }
}
