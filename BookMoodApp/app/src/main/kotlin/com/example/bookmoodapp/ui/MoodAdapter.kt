package com.example.bookmoodapp.ui

import com.example.bookmoodapp.model.EstadoAnimo
import androidx.recyclerview.widget.RecyclerView
import android.view.View
import android.widget.TextView
import com.example.bookmoodapp.R
import android.view.ViewGroup
import android.view.LayoutInflater





class MoodAdapter (
    private val moods: List<EstadoAnimo>,
    private val onMoodClick: (EstadoAnimo) -> Unit
): RecyclerView.Adapter<MoodAdapter.MoodViewHolder>() {
    class MoodViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val textMoodName: TextView = itemView.findViewById(R.id.textMoodName)
        val textMoodDescription: TextView = itemView.findViewById(R.id.textMoodDescription)

    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MoodViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_mood, parent, false)
        return MoodViewHolder(view)
    }
    override fun onBindViewHolder(holder: MoodViewHolder, position: Int) {
        val mood = moods[position]
        holder.textMoodName.text = mood.nombre
        holder.textMoodDescription.text = mood.descripcion
        holder.itemView.setOnClickListener { onMoodClick(mood) }
    }
    override fun getItemCount(): Int = moods.size

}