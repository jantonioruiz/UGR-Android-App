package com.example.miaplicacionmovil

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.miaplicacionmovil.databinding.ActivityListFacultyBinding

class ListFacultyActivity : AppCompatActivity() {
    private lateinit var binding: ActivityListFacultyBinding
    //private lateinit var giroscopio: GyroscopicManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //giroscopio = GyroscopicManager(this)
        binding = ActivityListFacultyBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initRecyclerView()
    }

    private fun initRecyclerView() {
        val manager = LinearLayoutManager(this)
        binding.rvListFac.layoutManager=manager
        binding.rvListFac.adapter=FacultyAdapter(FacultyProvider.facultyList) { faculty ->
            onItemSelected(
                faculty
            )
        }
    }

    private fun onItemSelected(faculty: Faculty){
        val intent = Intent(this,VisualizarFacultadActivity::class.java)
        intent.putExtra("FACULTAD",faculty)
        startActivity(intent)
    }
}