package com.example.bookmoodapp
import androidx.recyclerview.widget.RecyclerView
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.bookmoodapp.network.RetrofitClient
import kotlinx.coroutines.launch
import android.content.Intent
import com.example.bookmoodapp.ui.MoodAdapter
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import android.widget.Toast
import android.util.Log
import java.lang.Exception







class MainActivity : AppCompatActivity() {
    private lateinit var recyclerViewMoods: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerViewMoods = findViewById(R.id.recyclerViewMoods)
        recyclerViewMoods.layoutManager = LinearLayoutManager(this)

        lifecycleScope.launch {
            try {
                val response = RetrofitClient.api.getMoods()
                if (response.isSuccessful) {
                    val moods = response.body() ?: emptyList()
                    recyclerViewMoods.adapter = MoodAdapter(moods) { mood ->
                        val intent = Intent(this@MainActivity, BooksActivity::class.java)
                        intent.putExtra("moodId", mood.idEstadoAnimo)
                        intent.putExtra("moodName", mood.nombre)
                        startActivity(intent)
                    }
                } else {
                    Toast.makeText(
                        this@MainActivity,
                        "Error: ${response.code()}",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            } catch (e: Exception) {
                Toast.makeText(this@MainActivity, "Fallo: ${e.message}", Toast.LENGTH_SHORT).show()
                Log.e("API_ERROR", "Error al obtener los estados de ánimo", e)
            }
        }
    }
}