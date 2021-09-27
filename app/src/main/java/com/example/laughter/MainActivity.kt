package com.example.laughter

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.JsonRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.bumptech.glide.Glide



class MainActivity : AppCompatActivity() {

    var currentimageurl: String?= null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        loadmeme();
    }
    private fun loadmeme(){

        val queue = Volley.newRequestQueue(this)
        val url = "https://meme-api.herokuapp.com/gimme"

        val jsonObjectRequest = JsonObjectRequest(
            Request.Method.GET, url, null,
            Response.Listener { response ->
                // Display the first 500 characters of the response string.
             currentimageurl=response.getString("url");


                val imagePath= findViewById<ImageView>(R.id.memeimageView)
                Glide.with(this).load(currentimageurl).into(imagePath)
            },
            Response.ErrorListener {
                Toast.makeText(this,"Something went wrong",Toast.LENGTH_LONG).show()
            })

// Add the request to the RequestQueue.
        queue.add(jsonObjectRequest)
    }

    fun share(view: View){
    val intent=Intent(Intent.ACTION_SEND)
        intent.type = "text/plain"
        intent.putExtra(Intent.EXTRA_TEXT,"Hey Checkout this cool meme from Reddit $currentimageurl")
        val chooser =Intent.createChooser(intent,"Share this meme Using.......")
        startActivity(chooser)
    }
    fun next(view: View){
     loadmeme()
    }
}