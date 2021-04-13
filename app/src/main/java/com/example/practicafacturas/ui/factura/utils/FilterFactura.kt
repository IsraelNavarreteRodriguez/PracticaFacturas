package com.example.practicafacturas.ui.factura.utils

import androidx.lifecycle.MutableLiveData
import com.example.practicafacturas.data.model.Factura
import java.util.*

/**
 * La funcion de esta clase es filtrar los resultados del json
 */
class FilterFactura {

    lateinit var liveData: MutableLiveData<List<Factura>>

    /**
     * Filtra las facturas segun los datos dados
     */
    fun filter(
        list: List<Factura>,
        desde: Date?,
        hasta: Date?,
        importe: Double,
        estado: BooleanArray?
    ): MutableLiveData<List<Factura>> {
        //todo hacer esto funcional
        val result: List<Factura>
        liveData = MutableLiveData()
        if (desde == null && hasta == null && importe == 0.0)
         result = list
        else
            result = list.filter {
                estado?.get(0) ?: false || estado?.get(1) ?: false
                it.fecha.after(desde) && it.fecha.before(hasta)
                it.importeOrdenacion < importe
            }
        liveData.postValue(result)
        return liveData
    }
}