package com.example.practicafacturas.ui.factura.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.practicafacturas.data.model.Factura
import com.example.practicafacturas.ui.factura.service.DownloadService
import com.example.practicafacturas.ui.factura.utils.FilterFactura
import com.example.practicafacturas.ui.factura.utils.JsonToFactura
import com.google.gson.JsonArray
import java.time.LocalDate

/**
 * ViewModel de factura
 */
class FacturaViewModel : ViewModel(){

    val lista :  MutableLiveData<List<Factura>> = MutableLiveData()

    companion object{
        var json : JsonArray? = null
        private val service = DownloadService()

        /**
         * Descarga el json y lo guarda
         */
        fun getJson(){
            json = service.returnJsonArray()
        }
    }

    /**
     * Recoge la lista del json recogido en el activity luego de filtarla
     */
    fun getList(desde: LocalDate?,
                hasta: LocalDate?,
                importe: Double,
                estado:BooleanArray?){
        val filter = FilterFactura()
        filter.filter(lista,JsonToFactura.parseToList(json),desde, hasta, importe, estado)
    }



}