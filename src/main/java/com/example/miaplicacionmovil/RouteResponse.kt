package com.example.miaplicacionmovil

import com.google.gson.annotations.SerializedName

//clase de datos, que pide parametros
//no tengo por qué tomar todos los campos del JSON de respuesta, solo el campo que me interesa
data class RouteResponse(@SerializedName("features") val features:List<Feature>)
data class Feature(@SerializedName("geometry") val geometry:Geometry)
data class Geometry(@SerializedName("coordinates") val coordinates:List<List<Double>>)