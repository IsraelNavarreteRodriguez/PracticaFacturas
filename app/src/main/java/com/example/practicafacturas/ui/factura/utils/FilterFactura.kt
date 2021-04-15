package com.example.practicafacturas.ui.factura.utils

import androidx.lifecycle.MutableLiveData
import com.example.practicafacturas.data.model.Factura
import java.time.LocalDate

/**
 * La funcion de esta clase es filtrar los resultados del json
 */
class FilterFactura {


    /**
     * Filtra las facturas segun los datos dados
     */
    fun filter(
        liveData: MutableLiveData<List<Factura>>,
        list: List<Factura>,
        desde: LocalDate?,
        hasta: LocalDate?,
        importe: Double,
        estado: BooleanArray?
    ) {
        val result = list.filter {
            checkEstado(it, estado) &&
                    checkFecha(it, desde, hasta) &&
                    checkImporte(it, importe)
        }
        liveData.postValue(result)
    }

    /**
     * Comprueba si el importe es diferente de 0 (al no serlo se sobreentiende que quiere todos los resultados sin importar el importe)
     * y si es diferente recoge todos los importes menores
     */
    private fun checkImporte(it: Factura, importe: Double): Boolean {
        return if (importe != 0.0)
            it.importeOrdenacion < importe
        else
            true
    }

    /**
     * Comprueba si las fechas son distintas de null (Se sobreentiende que al serlo quiere todos los resultados sin importar la fecha)
     * y si es diferente recoge todas las facturas entre esas dos fechas
     */
    private fun checkFecha(it: Factura, desde: LocalDate?, hasta: LocalDate?): Boolean {
        return if (desde != null && hasta != null)
            it.fecha.isBefore(hasta) && it.fecha.isAfter(desde)
        else
            true
    }

    /**
     * Comprueba si los estados son negativo (Se sobreentiende que si no marca ninguno quiere todos los resultados si importar el estado)
     * si alguno de ellos no fuera falso entonces recoge solo los verdaderos y que el estado sea igual al correspondiente
     * si ambos son verdaderos recoge todas las facturas
     */
    private fun checkEstado(it: Factura, estado: BooleanArray?): Boolean {
        if (!estado?.get(0)!! && !estado[1])
            return true
        else
            if (estado[0]) {
                if (it.descEstado == "Pagada")
                    return true
            } else
                if (it.descEstado == "Pendiente de pago")
                    return true

        if (estado[0] && estado[1])
            return true

        return false
    }


}