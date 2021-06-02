package com.example.practicafacturas.presentation

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.practicafacturas.domain.model.Bill
import com.example.practicafacturas.domain.model.InvoiceFilter
import com.example.practicafacturas.domain.usecase.UseCaseButtonFilter
import com.example.practicafacturas.domain.usecase.UseCaseGetAllBills
import com.example.practicafacturas.domain.usecase.repositoryconnection.InstanceFilterBillRepository
import com.example.practicafacturas.domain.usecase.repositoryconnection.InstanceGetAllBillsRepository
import com.example.practicafacturas.domain.utils.MapperBill

/**
 * ViewModel de factura
 */
class BillViewModel : ViewModel() {

    val liveDataBill: MutableLiveData<List<Bill>> = MutableLiveData()
    lateinit var actualFilter: InvoiceFilter
    lateinit var defaultFilter: InvoiceFilter
    private val useCaseButtonFilter = UseCaseButtonFilter(InstanceFilterBillRepository())
    private val useCaseGetAllBills = UseCaseGetAllBills(InstanceGetAllBillsRepository())
    private var listBill: List<Bill> = listOf()

    /**
     * Recoge todas las bills la primera vez que se ejecuta la app
     */
    fun getAllBills() {
        if (listBill.isEmpty()) {
            this.listBill = useCaseGetAllBills.getListBill()
            liveDataBill.postValue(listBill)
            setDefaultFilter()
        }
    }

    /**
     * Setea el defaultfilter
     */
    fun setDefaultFilter() {
        this.defaultFilter = InvoiceFilter()
        this.defaultFilter = useCaseGetAllBills.getDefaultFilter(defaultFilter)
        this.actualFilter = defaultFilter
    }

    /**
     * Recoge la lista del json recogido en el activity luego de filtrarla
     */
    fun filterList() {
        liveDataBill.postValue(useCaseButtonFilter.getListBill(filter = actualFilter))
    }

    /**
     * Devuelve el importe minimo de la lista de facturas
     */
    fun getMinImporte(): Float {
        return MapperBill.getMinImporte(listBill)
    }


    /**
     * Cambia el filtro utilizado y actualiza la lista con esos parametros
     */
    fun setFilter(filtro: InvoiceFilter) {
        this.actualFilter = filtro
        filterList()
    }



}