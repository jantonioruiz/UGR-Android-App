package com.example.miaplicacionmovil

import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.Response


//interfaz con las solicitudes que usaremos en nuestra API
interface ApiService {
    //la parte final de la URL necesita una serie de parametros: api_key, start y end
    //Por ende, usamos la etiqueta 'Query' indicando el nombre del parametro a aportar
    @GET("/v2/directions/driving-car")
    suspend fun getRoute(
        @Query("api_key") key:String,
        //es importante recalcar que no podemos enviar comas por parametro en una URL
        @Query("start", encoded = true) start:String,
        @Query("end", encoded = true) end:String
    ):Response<RouteResponse>
    //el tipo pasado en Response representa la clase que usaremos para parsear los datos
    // es decir, el json que obtenemos como respuesta se transformara en un objeto de esa clase
}