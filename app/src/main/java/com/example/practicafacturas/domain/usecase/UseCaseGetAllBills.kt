package com.example.practicafacturas.domain.usecase

import com.example.practicafacturas.domain.model.Bill
import com.example.practicafacturas.domain.model.InvoiceFilter
import com.example.practicafacturas.domain.usecase.repositoryconnection.InterfaceGetAllBillsRepository

class UseCaseGetAllBills(private val interfaceGetAllBillsRepository: InterfaceGetAllBillsRepository) {

    fun getListBill(): List<Bill> {
        return interfaceGetAllBillsRepository.returnAllBillsFromDatabaseOrRetrofit()
    }

    fun getDefaultFilter(defaultFilter: InvoiceFilter): InvoiceFilter {
        return interfaceGetAllBillsRepository.returnDefaultFilter(defaultFilter)
    }


}