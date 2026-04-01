package com.example.bookmoodapp.ui
import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.bookmoodapp.R
import com.example.bookmoodapp.model.Libro
import android.view.ViewGroup
import android.view.LayoutInflater




class BookAdapter (
    private val books: List<Libro>
): RecyclerView.Adapter<BookAdapter.BookViewHolder>() {
    class BookViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textBookTitle: TextView = itemView.findViewById(R.id.textBookTitle)
        val textBookAuthor: TextView = itemView.findViewById(R.id.textBookAuthor)
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
    }
    override fun getItemCount(): Int = books.size

}

