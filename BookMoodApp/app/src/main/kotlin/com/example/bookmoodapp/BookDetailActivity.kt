package com.example.bookmoodapp
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.example.bookmoodapp.network.RetrofitClient
import com.example.bookmoodapp.model.UsuarioLibroRequest
import kotlinx.coroutines.launch
import java.lang.Exception




class BookDetailActivity : AppCompatActivity() {
    private lateinit var textBookTitle: TextView
    private lateinit var textBookAuthor: TextView
    private lateinit var textBookDescription: TextView
    private lateinit var imageBookCover: ImageView
    private lateinit var btnFavorite: Button
    private lateinit var btnRead: Button
    private lateinit var btnWantToRead: Button

    private var bookId: Long = -1
    private var userId: Long = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_book_detail)

        textBookTitle = findViewById(R.id.textBookTitle)
        textBookAuthor = findViewById(R.id.textBookAuthor)
        textBookDescription = findViewById(R.id.textBookDescription)
        imageBookCover = findViewById(R.id.imageBookCover)
        btnFavorite = findViewById(R.id.btnFavorite)
        btnRead = findViewById(R.id.btnRead)
        btnWantToRead = findViewById(R.id.btnWantToRead)
        val sharedPref = getSharedPreferences("bookmood", MODE_PRIVATE)
        userId = sharedPref.getLong("userId", -1)

        bookId = intent.getLongExtra("bookId", -1)
        val bookTitle = intent.getStringExtra("bookTitle")?: "Sin título"
        val bookAuthor = intent.getStringExtra("bookAuthor")?: "Autor desconocido"
        val bookDescription = intent.getStringExtra("bookDescription")?: "Sin sinopsis"
        val bookImageUrl = intent.getStringExtra("bookImageUrl")?: ""

        if (bookId == -1L){
            Toast.makeText(this, "Error al cargar el libro", Toast.LENGTH_SHORT).show()
            finish()
            return
        }

        textBookTitle.text = bookTitle
        textBookAuthor.text = bookAuthor
        textBookDescription.text = bookDescription
        Glide.with(this)
            .load(bookImageUrl)
            .into(imageBookCover)
        btnFavorite.setOnClickListener {
            saveUserBook(favorito = true, leido = false, quieroLeer = false)
        }
        btnWantToRead.setOnClickListener {
            saveUserBook(quieroLeer = true, leido = false, favorito = false)
        }
        btnRead.setOnClickListener {
            saveUserBook(leido = true, favorito = false, quieroLeer = false)
        }
    }

    private fun saveUserBook(favorito: Boolean, leido: Boolean, quieroLeer: Boolean) {
        if (userId == -1L) {
            Toast.makeText(this, "Usuario no autenticado",
                Toast.LENGTH_SHORT).show()
            return
        }
        lifecycleScope.launch {
            try {
                val request = UsuarioLibroRequest(
                    idUsuario = userId,
                    idLibro = bookId,
                    favorito = favorito,
                    leido = leido,
                    quieroLeer = quieroLeer
                )
                val response = RetrofitClient.api.saveUserBook(request)
                if (response.isSuccessful) {
                    Toast.makeText(
                        this@BookDetailActivity,
                        "Libro guardado exitosamente", Toast.LENGTH_SHORT
                    ).show()
                } else {
                    Toast.makeText(
                        this@BookDetailActivity,
                        "Error al guardar el libro: ${response.code()}",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            } catch (e: Exception) {
                Toast.makeText(
                    this@BookDetailActivity,
                    "Fallo: ${e.message}",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }
}