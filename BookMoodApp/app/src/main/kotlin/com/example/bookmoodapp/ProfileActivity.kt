package com.example.bookmoodapp
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

import com.example.bookmoodapp.network.RetrofitClient
import com.example.bookmoodapp.ui.BookAdapter
import kotlinx.coroutines.launch


class ProfileActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var btnFavoritos: Button
    private lateinit var btnLeidos: Button
    private lateinit var btnQuieroLeer: Button

    private var userId: Long = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        recyclerView = findViewById(R.id.recyclerViewProfile)
        btnFavoritos = findViewById(R.id.btnFavoritos)
        btnLeidos = findViewById(R.id.btnLeidos)
        btnQuieroLeer = findViewById(R.id.btnQuieroLeer)

        recyclerView.layoutManager = LinearLayoutManager(this)

        val sharedPref = getSharedPreferences("bookmood", MODE_PRIVATE)
        userId = sharedPref.getLong("userId", -1)

        btnFavoritos.setOnClickListener { cargarFavoritos() }
        btnLeidos.setOnClickListener { cargarLeidos() }
        btnQuieroLeer.setOnClickListener { cargarQuieroLeer() }
    }

    private fun cargarFavoritos() {
        lifecycleScope.launch {
            val response = RetrofitClient.api.getFavoritos(userId)
            if (response.isSuccessful) {
                recyclerView.adapter = BookAdapter(response.body() ?: emptyList()) {}
            }
        }
    }

    private fun cargarLeidos() {
        lifecycleScope.launch {
            val response = RetrofitClient.api.getLeidos(userId)
            if (response.isSuccessful) {
                recyclerView.adapter = BookAdapter(response.body() ?: emptyList()) {}
            }
        }
    }

    private fun cargarQuieroLeer() {
        lifecycleScope.launch {
            val response = RetrofitClient.api.getQuieroLeer(userId)
            if (response.isSuccessful) {
                recyclerView.adapter = BookAdapter(response.body() ?: emptyList()) {}
            }
        }
    }
}