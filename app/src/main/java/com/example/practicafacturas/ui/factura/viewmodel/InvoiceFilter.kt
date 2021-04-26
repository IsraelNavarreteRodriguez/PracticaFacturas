package com.example.practicafacturas.ui.factura.viewmodel

import androidx.lifecycle.MutableLiveData
import com.example.practicafacturas.data.model.Bill
import java.time.LocalDate

data class InvoiceFilter(
    var desde: LocalDate? = null,
    var hasta: LocalDate? = null,
    var importe: Float = 0.0F,
    var pagado: Boolean = true,
    var pendiente: Boolean = true
) {

    /**
     * Filtra las facturas segun los datos dados
     */
    fun filter(
        liveData: MutableLiveData<List<Bill>>,
        list: List<Bill>
    ) {
        val result = list.filter {
            checkEstado(it) &&
                    checkFecha(it) &&
                    checkImporte(it)
        }

        liveData.postValue(result)

    }

    /**
     * Comprueba si el importe es diferente de 0 (al no serlo se sobreentiende que quiere todos los resultados sin importar el importe)
     * y si es diferente recoge todos los importes menores
     */
    private fun checkImporte(it: Bill): Boolean {
        return it.importeOrdenacion <= importe
    }

    /**
     * Comprueba si las fechas son distintas de null (Se sobreentiende que al serlo quiere todos los resultados sin importar la fecha)
     * y si es diferente recoge todas las facturas entre esas dos fechas
     */
    private fun checkFecha(it: Bill): Boolean {
        return if (desde != null && hasta != null)
            if (it.fecha == hasta || it.fecha == desde)
                true
            else
                it.fecha.isBefore(hasta) && it.fecha.isAfter(desde)
        else
            true
    }

    /**
     * Comprueba si los estados son negativo (Se sobreentiende que si no marca ninguno quiere todos los resultados si importar el estado)
     * si alguno de ellos no fuera falso entonces recoge solo los verdaderos y que el estado sea igual al correspondiente
     * si ambos son verdaderos recoge todas las facturas
     */
    private fun checkEstado(it: Bill): Boolean {
        return if ((pagado && pendiente) || (!pagado && !pendiente))
            true
        else
            checkPagado(it) || checkPendiente(it)
    }

    /**
     * Comprueba si la factura esta pagada
     */
   private fun checkPagado(it: Bill): Boolean {
        return pagado && it.descEstado == "Pagada"
    }

    /**
     * Comprueba si la factura esta pendiente de pago
     */
    private fun checkPendiente(it: Bill): Boolean {
        return pendiente && it.descEstado == "Pendiente de pago"
    }

}