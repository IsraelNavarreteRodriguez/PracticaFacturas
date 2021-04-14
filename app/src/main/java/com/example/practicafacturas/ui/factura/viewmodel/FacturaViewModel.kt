package com.example.practicafacturas.ui.factura.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.practicafacturas.data.model.Factura
import com.example.practicafacturas.ui.factura.service.DownloadService
import com.example.practicafacturas.ui.factura.utils.FilterFactura
import com.example.practicafacturas.ui.factura.utils.JsonToFactura
import java.time.LocalDate
import java.util.*

class FacturaViewModel : ViewModel(){

    val lista :  MutableLiveData<List<Factura>> = MutableLiveData()
    private val service = DownloadService()


    fun getList(desde: LocalDate?,
                hasta: LocalDate?,
                importe: Double,
                estado:BooleanArray?){
        val filter = FilterFactura()
        filter.filter(lista,JsonToFactura.parseToList(service.returnJsonArray()),desde, hasta, importe, estado)
    }



}