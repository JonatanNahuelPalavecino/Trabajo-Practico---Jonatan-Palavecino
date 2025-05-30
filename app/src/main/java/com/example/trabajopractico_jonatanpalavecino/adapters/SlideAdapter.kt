package com.example.trabajopractico_jonatanpalavecino.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.trabajopractico_jonatanpalavecino.R

class SlideAdapter(private val layoutIds: List<Int>) :
    RecyclerView.Adapter<SlideAdapter.SlideViewHolder>() {

    inner class SlideViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val textView: TextView = view.findViewById(R.id.textView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SlideViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(layoutIds[viewType], parent, false)
        return SlideViewHolder(view)
    }

    override fun onBindViewHolder(holder: SlideViewHolder, position: Int) {
        // Animación: Fade in desde abajo
        holder.textView.alpha = 0f
        holder.textView.translationY = 20f
        holder.textView.animate()
            .alpha(1f)
            .translationY(0f)
            .setDuration(800)
            .start()
    }

    override fun getItemCount(): Int = layoutIds.size

    override fun getItemViewType(position: Int): Int = position
}
