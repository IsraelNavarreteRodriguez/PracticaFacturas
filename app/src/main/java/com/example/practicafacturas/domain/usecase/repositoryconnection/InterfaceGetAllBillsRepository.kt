package com.example.practicafacturas.domain.usecase.repositoryconnection

import com.example.practicafacturas.domain.model.Bill
import com.example.practicafacturas.domain.model.InvoiceFilter

interface InterfaceGetAllBillsRepository {

    /**
     * Metodo encargado de descargar el json de la red si hay conexion de no haberla recoger todas las bills de la base de datos
     */
    fun returnAllBillsFromDatabaseOrRetrofit(): List<Bill>

    /**
     * Metodo encargado de setear el defaultfilter
     */
    fun returnDefaultFilter(defaultFilter: InvoiceFilter): InvoiceFilter

}