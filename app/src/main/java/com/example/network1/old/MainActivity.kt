package com.example.network1.old

import android.graphics.BitmapFactory
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.network1.databinding.ActivityMainBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.net.URL

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: DogViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this).get(DogViewModel::class.java)

        binding.buttonFetch.setOnClickListener {
            viewModel.fetchRandomDogImage { imageUrl ->
                if (imageUrl != null) {
                    loadImage(imageUrl)
                } else {
                    Toast.makeText(this, "Ошибка загрузки", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun loadImage(url: String) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val input = URL(url).openStream()
                val bitmap = BitmapFactory.decodeStream(input)
                withContext(Dispatchers.Main) {
                    binding.imageView.setImageBitmap(bitmap)
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}
