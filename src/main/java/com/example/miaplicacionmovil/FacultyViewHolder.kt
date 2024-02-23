package com.example.miaplicacionmovil

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.miaplicacionmovil.databinding.ItemFacultyBinding


class FacultyViewHolder(view: View):RecyclerView.ViewHolder(view) {
    val binding = ItemFacultyBinding.bind(view)

    fun render(facultyModel: Faculty,onClickListener:(Faculty) -> Unit){
        binding.tvName.text = facultyModel.nombre
        val imageName = facultyModel.imagen
        val imageResId = itemView.context.resources.getIdentifier(imageName, "drawable", itemView.context.packageName)
        binding.ivFaculty.setImageResource(imageResId)
        itemView.setOnClickListener{ onClickListener(facultyModel)}
    }
}