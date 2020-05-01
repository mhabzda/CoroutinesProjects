package com.devtides.imageprocessingcoroutines

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import java.net.URL

class MainActivity : AppCompatActivity() {

    private val IMAGE_URL =
        "https://raw.githubusercontent.com/DevTides/JetpackDogsApp/master/app/src/main/res/drawable/dog.png"

    private val coroutineScope = CoroutineScope(Dispatchers.Main)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        coroutineScope.launch {
            val originalDeferred = async(Dispatchers.IO) { getOriginalBitmap() }
            val originalBitmap = originalDeferred.await()

            val transformedDeferred = async(Dispatchers.Default) { Filter.apply(originalBitmap) }
            val transformedBitmap = transformedDeferred.await()

            loadImage(transformedBitmap)
        }
    }

    private fun getOriginalBitmap(): Bitmap {
        return URL(IMAGE_URL).openStream().use {
            BitmapFactory.decodeStream(it)
        }
    }

    private fun loadImage(bitmap: Bitmap) {
        progressBar.visibility = View.GONE
        imageView.setImageBitmap(bitmap)
        imageView.visibility = View.VISIBLE
    }
}
