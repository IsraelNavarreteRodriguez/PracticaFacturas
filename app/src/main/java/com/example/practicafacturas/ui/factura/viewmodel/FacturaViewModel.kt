package com.example.practicafacturas.ui.factura.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.practicafacturas.data.model.Factura
import com.example.practicafacturas.data.repository.FacturaRepository
import com.example.practicafacturas.ui.factura.utils.FilterFactura
import com.example.practicafacturas.ui.factura.utils.JsonToFactura
import java.util.*

class FacturaViewModel : ViewModel(){

    lateinit var lista :  MutableLiveData<List<Factura>>
    lateinit var json : List<Factura>

    fun getJson(){
        json = JsonToFactura.parseToList(FacturaRepository.repository.returnJsonArray())
    }


    fun getList(desde: Date?,
                      hasta: Date?,
                      importe: Double,
                      estado:BooleanArray?) : MutableLiveData<List<Factura>>{
        val filter = FilterFactura()
        lista = filter.filter(json,desde, hasta, importe, estado)
        return lista
    }

}