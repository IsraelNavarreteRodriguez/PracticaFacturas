package com.example.practicafacturas.ui.factura.utils

import androidx.lifecycle.MutableLiveData
import com.example.practicafacturas.data.model.Factura
import java.time.LocalDate
import java.util.*

/**
 * La funcion de esta clase es filtrar los resultados del json
 */
class FilterFactura {


    /**
     * Filtra las facturas segun los datos dados
     */
    fun filter(
        liveData : MutableLiveData<List<Factura>>,
        list: List<Factura>,
        desde: LocalDate?,
        hasta: LocalDate?,
        importe: Double,
        estado: BooleanArray?
    ){
        val result = list.filter {
            checkEstado(it, estado) &&
                    checkFecha(it, desde, hasta) &&
                    checkImporte(it, importe)
        }
        liveData.postValue(result)
    }

    private fun checkImporte(it: Factura, importe: Double): Boolean {
        return if (importe != 0.0)
            it.importeOrdenacion < importe
        else
            true
    }

    private fun checkFecha(it: Factura, desde: LocalDate?, hasta: LocalDate?): Boolean {
        return if (desde != null && hasta != null)
            it.fecha.isBefore(hasta) && it.fecha.isAfter(desde)
        else
            true
    }

    private fun checkEstado(it: Factura, estado: BooleanArray?): Boolean {
        if (estado?.get(0)!!) {
            if (it.descEstado == "Pagada")
                return true
        } else
            if (it.descEstado == "Pendiente de pago")
                return true
        else return true
        return false
    }


}