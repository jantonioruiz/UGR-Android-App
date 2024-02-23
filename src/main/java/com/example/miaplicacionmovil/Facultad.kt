package com.example.miaplicacionmovil

import java.io.Serializable

data class Faculty(
    val nombre:String,
    val imagen:String,
    val telefono:String,
    val direccion: String,
    val aperturacierre: String,
    val latitud: String,
    val longitud: String
) :Serializable