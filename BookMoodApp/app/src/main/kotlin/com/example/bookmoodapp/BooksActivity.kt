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
import android.content.Intent



class BooksActivity : AppCompatActivity() {
    private lateinit var textTitle: TextView
    private lateinit var recyclerViewBooks: RecyclerView
    private var moodId: Long = -1


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_books)


        recyclerViewBooks = findViewById(R.id.recyclerViewBooks)
        textTitle = findViewById(R.id.textTitle)
        recyclerViewBooks.layoutManager = LinearLayoutManager(this)

        moodId = intent.getLongExtra("moodId", -1)
        if(moodId == -1L){
            Toast.makeText(this, "Mood no válido",
                Toast.LENGTH_SHORT).show()
            finish()
            return
        }
        Log.d("BOOKS_DEBUG","MoodId recibido $moodId")
        val moodName = intent.getStringExtra("moodName") ?: "Recomendaciones"

        textTitle.text = "Recomendaciones para $moodName"

        lifecycleScope.launch {
            try {
                Log.d("BOOKS_DEBUG","MoodId recibido $moodId")
                val response = RetrofitClient.api.getRecommendations(moodId)
                Log.d("BOOKS_DEBUG","código respuesta: ${response.code()}")
                Log.d("BOOKS_DEBUG","Body: ${response.body()}")
                if (response.isSuccessful) {
                    val books = response.body() ?: emptyList()
                    Log.d("BOOKS_DEBUG","Libros obtenidos: $books.size")
                    recyclerViewBooks.adapter = BookAdapter(books){ book ->
                        val intent = Intent(this@BooksActivity, BookDetailActivity::class.java)
                        intent.putExtra("bookId", book.idLibro)
                        intent.putExtra("bookTitle", book.titulo)
                        intent.putExtra("bookAuthor", book.autor ?: "Autor desconocido")
                        intent.putExtra("bookDescription", book.sinopsis ?: "Sin sinopsis")
                        intent.putExtra("bookImageUrl", book.portadaUrl ?: "")
                        startActivity(intent)
                    }
                    } else {
                        val errorMessage = response.errorBody()?.string()
                        Log.e("BOOKS_DEBUG", "Error en la respuesta: $errorMessage")
                        Toast.makeText(
                        this@BooksActivity,
                        "Error: ${response.code()}",
                        Toast.LENGTH_SHORT).show()
                }
            } catch (e: Exception) {
                Log.e("BOOKS_DEBUG", "Error al obtener las recomendaciones", e)
                Toast.makeText(this@BooksActivity,
                    "Fallo: ${e.message}", Toast.LENGTH_SHORT).show()

            }
        }
    }
}