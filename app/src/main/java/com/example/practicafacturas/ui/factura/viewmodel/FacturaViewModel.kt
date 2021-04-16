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

    val liveData: MutableLiveData<List<Factura>> = MutableLiveData()
    var actualFilter = InvoiceFilter()
    lateinit var defaultFilter: InvoiceFilter
    private val service = DownloadService()
    var listFactura: List<Factura> = listOf()

    /**
     * Descarga el json y lo guarda
     */
    fun downloadJson() {
        if (listFactura.isNullOrEmpty()) {
            val jsonFactura = service.returnJsonArray()
            listFactura = JsonToFactura.parseToList(jsonFactura)
            liveData.postValue(listFactura)
            defaultFilter = InvoiceFilter(
                listFactura.first().fecha,
                listFactura.last().fecha,
                JsonToFactura.getMaxImporte(listFactura)
            )
            this.actualFilter = defaultFilter
        }
    }


    /**
     * Recoge la lista del json recogido en el activity luego de filtrarla
     */
    fun getList() {
        this.actualFilter.filter(liveData, listFactura)
    }

    fun setFilter(filtro: InvoiceFilter) {
        this.actualFilter = filtro
    }


}