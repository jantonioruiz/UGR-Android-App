package com.example.miaplicacionmovil

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

//el segundo parametro que se le pasa es una funcion lambda que se llamará cada vez que se cliquee un elemento de la lista
class FacultyAdapter(private val facultyList:List<Faculty>,private val onClickListener:(Faculty)->Unit): RecyclerView.Adapter<FacultyViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FacultyViewHolder {
        //creamos un viewHolder pasandole como instancia el diseño con el que pintamos cada elemento de la lista
        val layoutInflater = LayoutInflater.from(parent.context)
        return FacultyViewHolder(layoutInflater.inflate(R.layout.item_faculty,parent,false))
    }

    override fun getItemCount(): Int = facultyList.size

    //pinta el item situado en la posicion indicada de la lista
    override fun onBindViewHolder(holder: FacultyViewHolder, position: Int) {
        //usaremos una funcion del viewHolder que nos permite pintar dicho elemento, enlazar el item con el diseño especifico
        val item = facultyList[position]
        holder.render(item,onClickListener)
    }
}