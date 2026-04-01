package com.example.bookmoodapp
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.bookmoodapp.network.RetrofitClient
import kotlinx.coroutines.launch
import android.widget.Toast
import android.util.Log
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.bookmoodapp.ui.BookAdapter
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity


class BooksActivity : AppCompatActivity() {
    private lateinit var textTitle: TextView
    private lateinit var recyclerViewBooks: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_books)

        textTitle = findViewById(R.id.textTitle)
        recyclerViewBooks = findViewById(R.id.recyclerViewBooks)
        recyclerViewBooks.layoutManager = LinearLayoutManager(this)

        val moodId = intent.getLongExtra("moodId", -1)
        val moodName = intent.getStringExtra("moodName") ?: "Recomendaciones"

        textTitle.text = "Recomendaciones para $moodName"

        lifecycleScope.launch {
            try {
                val response = RetrofitClient.api.getRecomendations(moodId)
                if (response.isSuccessful) {
                    val books = response.body() ?: emptyList()
                    recyclerViewBooks.adapter = BookAdapter(books)
                    } else {
                    Toast.makeText(
                        this@BooksActivity,
                        "Error: ${response.code()}",Toast.LENGTH_SHORT
                    ).show()
                }
            } catch (e: Exception) {
                Toast.makeText(this@BooksActivity, "Fallo: ${e.message}", Toast.LENGTH_SHORT).show()
                Log.e("API_ERROR", "Error al cargar libros", e)
            }








        }
    }
}