package com.example.miaplicacionmovil

import android.Manifest
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.maps.model.PolylineOptions
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RouteFacultySelectedActivity : AppCompatActivity(), OnMapReadyCallback {
    private lateinit var latitud: String
    private lateinit var  longitud: String
    private lateinit var nombre_facultad: String
    private lateinit var map: GoogleMap

    companion object{
        const val REQUEST_CODE_LOCATION = 0
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_route_faculty_selected)

        //inicializamos la latitud/longitud de la facultad
        latitud = intent.extras?.getString("LATITUD").orEmpty()
        longitud = intent.extras?.getString("LONGITUD").orEmpty()
        nombre_facultad = intent.extras?.getString("NOMBRE").orEmpty()
        //iniciamos el mapa
        createFragment()
    }

    /*
    Objeto Retrofit para realizar solicitud HTTP, indicando URL base y la herramienta
    de conversion del JSON de respuesta en un objeto de clase
     */
    fun getRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://api.openrouteservice.org/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }


    private fun drawRoute(routeResponse: RouteResponse?) {
        //pintamos en el mapa una polilinea usando las coordenadas devueltas por el servicio en uso
        val polyLineOptions = PolylineOptions()
        routeResponse?.features?.first()?.geometry?.coordinates?.forEach {
            //la API me devuelve las coordenadas al contrario
            polyLineOptions.add(LatLng(it[1], it[0]))
        }

        //volvemos al hilo principal para pintar el camino
        runOnUiThread{
            map.addPolyline(polyLineOptions)
        }
    }

    private fun crearRuta() {
        //comprobar si tenemos acceso a la ubcacion en tiempo real
        if(map.isMyLocationEnabled){
            //dibujamos la ruta
            val fusedLocationCliente = LocationServices.getFusedLocationProviderClient(this)
            fusedLocationCliente.lastLocation.addOnSuccessListener {
                if(it!=null){
                    //una vez que ha terminado la tarea asincrona (obtencion de mi posicion actual)
                    val start: String = "${it.longitude},${it.latitude}"
                    val end: String = "$longitud,$latitud"

                    //ya conocemos  nuestras coordenadas, dibujamos ruta
                    CoroutineScope(Dispatchers.IO).launch{
                        //creamos la interfaz ApiService, invocando la funcion asociada al metodo GET a realizar
                        val call = getRetrofit().create(ApiService::class.java).getRoute("5b3ce3597851110001cf6248a775c0cddc9a49c8996f2c4f32fd14b8",start,end)
                        if(call.isSuccessful){
                            //respuesta correcta
                            drawRoute(call.body())
                        }
                        else{
                            Log.i("guille","No he recibido una respuesta")
                        }
                    }
                }
            }
        }
    }

    private fun createFragment() {
        val mapFragment = supportFragmentManager.findFragmentById(R.id.mapRouteFaculty) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    override fun onMapReady(map: GoogleMap) {
        this.map = map
        //pedimos permisos de acceso a ubicacion en tiempo real
        createMarker()
        enableLocation()
        crearRuta()

    }

    private fun createMarker() {
        val origen = LatLng(37.184885, -3.603454);
        map.animateCamera(CameraUpdateFactory.newLatLngZoom(origen,13.0f));
        //añadimos marcador de la facultad a visitar
        val coordinates = LatLng(latitud.toDouble(),longitud.toDouble())
        val marker = MarkerOptions().position(coordinates).title(nombre_facultad);
        map.addMarker(marker)
    }

    private fun isLocationPermissionGranted() = ContextCompat.checkSelfPermission(this,
        Manifest.permission.ACCESS_FINE_LOCATION)== PackageManager.PERMISSION_GRANTED

    private fun enableLocation() {
        if(!::map.isInitialized) return
        //comprobamos si ya tenemos los permisos de acceso a ubicacion de tiempo real
        if(isLocationPermissionGranted()){
            map.isMyLocationEnabled = true
        }
        else{
            //pedimos permiso
            requestLocationPermission()
        }
    }

    private fun requestLocationPermission() {
        if(ActivityCompat.shouldShowRequestPermissionRationale(this,Manifest.permission.ACCESS_FINE_LOCATION)){
            //si el usuario ha rechazado inicialmente el permiso pero no ha marcado la
            //opcion de 'No volver a preguntar', le volvemos a solicitar el permiso
            //pero explicandole ahora por qué necesitamos ese permiso

            //le mostramos un mensaje o aviso
            Toast.makeText(this,"Ve a ajustes y acepta los permisos", Toast.LENGTH_SHORT).show()
        }
        else{
            //primera vez que solicitamos el permiso al usuario
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                REQUEST_CODE_LOCATION)
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        when(requestCode){
            REQUEST_CODE_LOCATION -> if(grantResults.isNotEmpty() && grantResults[0]==PackageManager.PERMISSION_GRANTED) {
                map.isMyLocationEnabled = true
            }
            else{
                //si no nos ha concedido ese permiso, le enviamos una pequeña notificacion
                Toast.makeText(this,"Para activar la localización ve a ajustes y acepta los permisos",Toast.LENGTH_SHORT).show()
            }
            //si se hubiese respondido a otra solicitud de permiso...
            //No deberia de pasar
            else -> {}
        }
    }

    //cada vez que el usuario vuelve a la aplicacion, comprobamos que los permisos sigan activos
    override fun onResumeFragments() {
        super.onResumeFragments()
        if(!::map.isInitialized) return
        //cuando la activity con ese fragmento vuelva a estar en primer plano,
        //comprobamos si seguimos teniendo el permiso de localizacion
        if(!isLocationPermissionGranted()){
            map.isMyLocationEnabled=false
            Toast.makeText(this,"Vuelva a activar la localización",Toast.LENGTH_SHORT).show()

        }
    }
}