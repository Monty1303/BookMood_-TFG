package com.example.bookmoodapp

import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.bookmoodapp.network.RetrofitClient
import com.example.bookmoodapp.ui.BookAdapter
import kotlinx.coroutines.launch
import java.lang.Exception
import android.view.View
import android.widget.TextView
import com.example.bookmoodapp.model.Libro


class ProfileActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var btnFavoritos: Button
    private lateinit var btnLeidos: Button
    private lateinit var btnQuieroLeer: Button
    private lateinit var textEmptyProfile: TextView


    private var userId: Long = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        recyclerView = findViewById(R.id.recyclerViewProfile)
        btnFavoritos = findViewById(R.id.btnFavoritos)
        btnLeidos = findViewById(R.id.btnLeidos)
        btnQuieroLeer = findViewById(R.id.btnQuieroLeer)
        textEmptyProfile = findViewById(R.id.textEmptyProfile)


        recyclerView.layoutManager = LinearLayoutManager(this)

        val sharedPref = getSharedPreferences("bookmood", MODE_PRIVATE)
        userId = sharedPref.getLong("userId", -1)

        if (userId == -1L) {
            Toast.makeText(this, "Usuario no autenticado", Toast.LENGTH_SHORT).show()
            finish()
            return
        }

        btnFavoritos.setOnClickListener { cargarFavoritos() }
        btnLeidos.setOnClickListener { cargarLeidos() }
        btnQuieroLeer.setOnClickListener { cargarQuieroLeer() }

        cargarFavoritos()
    }

    private fun cargarFavoritos() {
        lifecycleScope.launch {
            try {
                val response = RetrofitClient.api.getFavoritos(userId)
                if (response.isSuccessful) {
                    mostrarLibros(response.body() ?: emptyList())
                } else {
                    Toast.makeText(
                        this@ProfileActivity,
                        "Error al cargar favoritos",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            } catch (e: Exception) {
                Toast.makeText(
                    this@ProfileActivity,
                    "Fallo: ${e.message}",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    private fun cargarLeidos() {
        lifecycleScope.launch {
            try {
                val response = RetrofitClient.api.getLeidos(userId)
                if (response.isSuccessful) {
                    mostrarLibros(response.body() ?: emptyList())
                } else {
                    Toast.makeText(
                        this@ProfileActivity,
                        "Error al cargar leídos",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            } catch (e: Exception) {
                Toast.makeText(
                    this@ProfileActivity,
                    "Fallo: ${e.message}",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    private fun cargarQuieroLeer() {
        lifecycleScope.launch {
            try {
                val response = RetrofitClient.api.getQuieroLeer(userId)
                if (response.isSuccessful) {
                    mostrarLibros(response.body() ?: emptyList())
                } else {
                    Toast.makeText(
                        this@ProfileActivity,
                        "Error al cargar quiero leer",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            } catch (e: Exception) {
                Toast.makeText(
                    this@ProfileActivity,
                    "Fallo: ${e.message}",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    private fun mostrarLibros(libros: List<Libro>) {
        if (libros.isEmpty()) {
            textEmptyProfile.visibility = View.VISIBLE
            recyclerView.visibility = View.GONE
        } else {
            textEmptyProfile.visibility = View.GONE
            recyclerView.visibility = View.VISIBLE
            recyclerView.adapter = BookAdapter(libros) { }


        }
    }
}