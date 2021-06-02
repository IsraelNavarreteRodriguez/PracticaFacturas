package com.example.practicafacturas.domain.usecase.repositoryconnection

import com.example.practicafacturas.data.repository.BillsRepository
import com.example.practicafacturas.domain.model.Bill
import com.example.practicafacturas.domain.model.InvoiceFilter

class InstanceGetAllBillsRepository: InterfaceGetAllBillsRepository {

    override fun returnAllBillsFromDatabaseOrRetrofit(): List<Bill> {
        return BillsRepository.returnAllBillsFromDatabaseOrRetrofit()
    }

    override fun returnDefaultFilter(defaultFilter: InvoiceFilter): InvoiceFilter {
        return BillsRepository.returnDefaultFilter(defaultFilter)
    }
}