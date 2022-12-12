package cl.tobar.mapv304

import android.Manifest
import android.content.pm.PackageManager
import android.location.Address
import android.location.Geocoder
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

import androidx.core.app.ActivityCompat


import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.*
import com.google.android.gms.maps.model.*

class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private lateinit var fusedLocation: FusedLocationProviderClient //Obtiene informacion de distiuntas fuentes
    private var ultimoMarcador : Marker? = null



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)

        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        fusedLocation = LocationServices.getFusedLocationProviderClient(this)
    }


    //Comprobar servicios
    override fun onMapReady(googleMap: GoogleMap) { //Agrega un marcador
        mMap = googleMap

        if (ActivityCompat.checkSelfPermission( //Si el permiso de ubicacion no se acepto
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            )
            != PackageManager.PERMISSION_GRANTED
        ){
            ActivityCompat.requestPermissions(  //Se le pide al usuario
                this,
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                1
            )
            return  //Y salgo
        }

        mMap.isMyLocationEnabled = true
        mMap.uiSettings.isZoomControlsEnabled = true    //Controles de + y - de la pantallla
        mMap.uiSettings.isCompassEnabled = true     //Activacion de el compas
        mMap.uiSettings.isMapToolbarEnabled = false //Desactivaciones de redireccion a maps
        mMap.mapType = GoogleMap.MAP_TYPE_NORMAL //Tipo de mapa











        fusedLocation.lastLocation.addOnSuccessListener { location ->
            if (location != null) {
                val ubicacion = LatLng(location.latitude, location.longitude)   //Coordenadas long y lat
                mMap.animateCamera( //Animacion de la Camara
                    CameraUpdateFactory.newLatLngZoom(ubicacion, 12f))  //Coords + zoom del mapa
            }

        }

        mMap.setOnMapLongClickListener {    //Al mantener pulsado
            val markerOptions = MarkerOptions().position(it)
            markerOptions.icon(
                BitmapDescriptorFactory.defaultMarker(  //Icono del marcador
                    BitmapDescriptorFactory.HUE_BLUE  //Color marcador
                )
            )
            val nombreUbicacion = obtenerDireccion(it)
            markerOptions.title(nombreUbicacion)
            if(ultimoMarcador != null)
                ultimoMarcador!!.remove()
            ultimoMarcador= mMap.addMarker(markerOptions)
            mMap.animateCamera(CameraUpdateFactory.newLatLng(it))
        }

    }
    fun obtenerDireccion(latLng: LatLng) : String {
        val geocoder = Geocoder(this)
        val direcciones : List<Address>?
        val primeraDireccion : Address
        var textoDireccion = ""

        try{
            direcciones = geocoder.getFromLocation(
                latLng.latitude, latLng.longitude, 1)
            if (direcciones != null && direcciones.isNotEmpty()) {
                primeraDireccion = direcciones[0]

                //Si la direccion tiene varias lineas
                if (primeraDireccion.maxAddressLineIndex > 0) {
                    for (i in 0 .. primeraDireccion.maxAddressLineIndex){
                        textoDireccion += primeraDireccion.getAddressLine(i) + "\n"
                    }
                }
                //Si hay principal y secundario
                else {
                    textoDireccion += primeraDireccion.thoroughfare + "," +
                            primeraDireccion.subThoroughfare + "\n"
                }
            }
        } catch (e : Exception) {
            textoDireccion = "Direccion no encontrada"
        }

        return textoDireccion
    }


}