package com.example.practicafacturas.ui.factura.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.practicafacturas.data.model.Bill
import com.example.practicafacturas.ui.factura.service.DownloadService
import com.example.practicafacturas.ui.factura.utils.JsonToBill

/**
 * ViewModel de factura
 */
class BillViewModel : ViewModel() {

    val liveDataBill: MutableLiveData<List<Bill>> = MutableLiveData()
    lateinit var actualFilter : InvoiceFilter
    lateinit var defaultFilter: InvoiceFilter
    private val service = DownloadService()
    private var listBill: List<Bill> = listOf()

    /**
     * Descarga el json y lo guarda
     */
    fun downloadJson() {
        if (listBill.isEmpty()) {
            val jsonFactura = service.returnJsonArray()
            listBill = JsonToBill.jsonBillToListBill(jsonFactura)
            liveDataBill.postValue(listBill)
            defaultFilter = InvoiceFilter(
                listBill.last().fecha,
                listBill.first().fecha,
                JsonToBill.getMaxImporte(listBill)
            )
            this.actualFilter = defaultFilter
        }
    }


    /**
     * Recoge la lista del json recogido en el activity luego de filtrarla
     */
    fun filterList() {
        this.actualFilter.filter(liveDataBill, listBill)
    }

    /**
     * Devuelve el importe minimo de la lista de facturas
     */
    fun getMinImporte() : Float{
        return JsonToBill.getMinImporte(listBill)
    }


    /**
     * Cambia el filtro utilizado y actualiza la lista con esos parametros
     */
    fun setFilter(filtro: InvoiceFilter) {
        this.actualFilter = filtro
        filterList()
    }


}