package com.example.miaplicacionmovil

class FacultyProvider {
    //me proporciona el listado de facultades a visualizar
    companion object{
        val facultyList = listOf<Faculty>(
            Faculty("Facultad de Ciencias","f_ciencias","958 24 06 98/99","Fuente Nueva s/n  18071 Granada","7:00-22:00","37.17896789502398", "-3.6086361648996936"),
            Faculty("ETSIIT","f_etsiit","958 242802","Calle Periodista Daniel Saucedo Aranda s/n","9:00-14:00","37.196829806174925", "-3.6247859123731105"),
            Faculty("Ingenieria de Caminos","f_caminos","958 24 41 46","C/ Dr. Severo Ochoa s/n","9:00-14:00","37.18136148766823", "-3.607887885286628"),
            Faculty("Facultad de Edificacion","f_edificacion","958 24 31 06","C/ Severo Ochoa S/N","9:00-14:00","37.18129514881096", "-3.606898017794373"),
            Faculty("V Centenario","f_centenario","958249858","Avd. de Madrid S/N ","9:00-14:00","37.18688512643659", "-3.604611469696824"),
            Faculty("Facultad de Derecho","f_derecho","958249760","Plaza de la Universidad, 1, Centro","9:00-14:00","37.178191087840965", "-3.601983632338776")
        )
    }
}