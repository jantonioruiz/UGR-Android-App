package com.example.miaplicacionmovil

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.miaplicacionmovil.databinding.ActivityVisualizarFacultadBinding
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions


class VisualizarFacultadActivity : AppCompatActivity() {
    private lateinit var binding: ActivityVisualizarFacultadBinding
    private lateinit var currentFaculty: Faculty

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityVisualizarFacultadBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ConfigurarVista()
        InicializarListeners()
    }

    private fun InicializarListeners() {
        //listener del botton
        binding.btAccesoMapa.setOnClickListener {
            //creamos un Intent
            val intent = Intent(this,RouteFacultySelectedActivity::class.java)
            intent.putExtra("LATITUD",currentFaculty.latitud)
            intent.putExtra("LONGITUD",currentFaculty.longitud)
            intent.putExtra("NOMBRE",currentFaculty.nombre)
            startActivity(intent)
        }

    }

    private fun ConfigurarVista() {
        //tomamos el intent con la info de la facultad seleccionada
        val faculty = intent.getSerializableExtra("FACULTAD") as? Faculty
        //configuramos la vista usando la informacion correspondiente
        if (faculty != null) {
            currentFaculty=faculty
            binding.tvTitle.text = faculty.nombre
            binding.tvDireccion.text = "Direccion: " + faculty.direccion
            binding.tvTelefono.text = "Telefono:" + faculty.telefono
            binding.tvHorario.text = "Horario: " + faculty.aperturacierre

            val imageResId = resources.getIdentifier(faculty.imagen, "drawable", packageName)
            //binding.ivPlano.setImageResource(imageResId)

            val targetWidth = 1000 // Ancho deseado en píxeles
            val targetHeight = 1000 // Altura deseada en píxeles
            Glide.with(this)
                .load(imageResId)
                .apply(RequestOptions().override(targetWidth, targetHeight))
                .into(binding.ivPlano)
        }
    }

        //funcionalidad del boton, que recibirá
}