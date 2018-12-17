package com.example.jorge.dameltiempo.Adapters

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.jorge.dameltiempo.Models.Hour
import kotlinx.android.synthetic.main.horas_item.view.*
import com.example.jorge.dameltiempo.R

class AdaptadorHoras(val hours:  ArrayList<Hour>, val contex: Context): RecyclerView.Adapter<AdaptadorHoras.HourViewHolder> (){

    override fun onBindViewHolder(holder: HourViewHolder, position: Int) =
         holder.bind(hours[position])


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HourViewHolder =
        HourViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.horas_item,parent,false))

    override fun getItemCount(): Int = hours.size

    class HourViewHolder(horasItemView: View):RecyclerView.ViewHolder(horasItemView){
        fun bind(hour: Hour) = with(itemView){
            textViewHora.text = hour.getFormattedTime()
            textViewGrados.text = "${hour.temp.toInt().toString()} Cº"
            textViewProv.text = "${hour.precip.toInt().toString()} %"
        }

    }
}
