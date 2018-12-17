package com.example.jorge.dameltiempo.Adapters

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.jorge.dameltiempo.Models.Day
import com.example.jorge.dameltiempo.R
import kotlinx.android.synthetic.main.daily_item.view.*

class AdaptadorDias(val dias:ArrayList<Day>, val context: Context): RecyclerView.Adapter<AdaptadorDias.DiasViewHolder> () {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DiasViewHolder =
        DiasViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.daily_item, parent,false))

    override fun getItemCount(): Int = dias.size

    override fun onBindViewHolder(holder: DiasViewHolder, position: Int) =
        holder.bind(dias[position])

    class DiasViewHolder(diasItemView: View) : RecyclerView.ViewHolder(diasItemView) {
        fun bind(day: Day) = with(itemView) {
                textViewDia.text = day.getFormattedTime()
                textViewTemMin.text = " Min ${day.minTemp.toInt()} Cº"
                textViewTempMax.text = "Max ${day.maxTemp.toInt()} Cº"
        }
    }
}


