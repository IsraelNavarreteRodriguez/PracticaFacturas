package com.example.practicafacturas.ui.factura.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.practicafacturas.data.model.Factura
import com.example.practicafacturas.ui.factura.service.DownloadService
import com.example.practicafacturas.ui.factura.utils.JsonToFactura

/**
 * ViewModel de factura
 */
class FacturaViewModel : ViewModel() {

    val liveDataFactura: MutableLiveData<List<Factura>> = MutableLiveData()
    lateinit var actualFilter : InvoiceFilter
    lateinit var defaultFilter: InvoiceFilter
    private val service = DownloadService()
    private var listFactura: List<Factura> = listOf()

    /**
     * Descarga el json y lo guarda
     */
    fun downloadJson() {
        if (listFactura.isEmpty()) {
            val jsonFactura = service.returnJsonArray()
            listFactura = JsonToFactura.parseToList(jsonFactura)
            liveDataFactura.postValue(listFactura)
            defaultFilter = InvoiceFilter(
                listFactura.last().fecha,
                listFactura.first().fecha,
                JsonToFactura.getMaxImporte(listFactura)
            )
            this.actualFilter = defaultFilter
        }
    }


    /**
     * Recoge la lista del json recogido en el activity luego de filtrarla
     */
    fun getList() {
        this.actualFilter.filter(liveDataFactura, listFactura)
    }

    fun getMinImporte() : Float{
        return JsonToFactura.getMinImporte(listFactura)
    }


    fun setFilter(filtro: InvoiceFilter) {
        this.actualFilter = filtro
        getList()
    }


}