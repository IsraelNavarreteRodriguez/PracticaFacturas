package com.example.practicafacturas.data.model

import java.time.LocalDate

/**
 * Es la clase modelo de factura
 */
data class Factura(val descEstado : String, val importeOrdenacion : Float,val fecha : LocalDate)