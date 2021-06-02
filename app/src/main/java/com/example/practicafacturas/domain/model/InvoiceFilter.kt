package com.example.practicafacturas.domain.model

import java.time.LocalDate

data class InvoiceFilter(
    var desde: LocalDate? = null,
    var hasta: LocalDate? = null,
    var importe: Float = 0.0F,
    var pagado: Boolean = true,
    var pendiente: Boolean = true
) {



}