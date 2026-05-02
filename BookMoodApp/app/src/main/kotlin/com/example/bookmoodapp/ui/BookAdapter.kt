package com.example.bookmoodapp.ui
import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.bookmoodapp.R
import com.example.bookmoodapp.model.Libro
import android.view.ViewGroup
import android.view.LayoutInflater
import android.widget.ImageView
import com.bumptech.glide.Glide








class BookAdapter (
    private val books: List<Libro>,
    private val onBookClick: (Libro) -> Unit
): RecyclerView.Adapter<BookAdapter.BookViewHolder>() {
    inner class BookViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textBookTitle: TextView = itemView.findViewById(R.id.textBookTitle)
        val textBookAuthor: TextView = itemView.findViewById(R.id.textBookAuthor)
        val imageBookCover: ImageView = itemView.findViewById(R.id.imageBookCover)
        val textBookDescription: TextView = itemView.findViewById(R.id.textBookDescription)
        val textBookGender: TextView = itemView.findViewById(R.id.textBookGender)

    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_book, parent, false)
        return BookViewHolder(view)
    }
    override fun onBindViewHolder(holder: BookViewHolder, position: Int) {
        val book = books[position]
        holder.textBookTitle.text = book.titulo
        holder.textBookAuthor.text = book.autor ?:"Autor no disponible"
        holder.textBookDescription.text = book.sinopsis ?: "Descripción no disponible"
        holder.textBookGender.text = book.genero ?: "Género no disponible"
        Glide.with(holder.itemView.context)
            .load(book.portadaUrl)
            .placeholder(R.drawable.ic_launcher_background)
            .error(R.drawable.ic_launcher_background)
            .into(holder.imageBookCover)
        holder.itemView.setOnClickListener { onBookClick(book) }
    }
    override fun getItemCount(): Int = books.size

}

