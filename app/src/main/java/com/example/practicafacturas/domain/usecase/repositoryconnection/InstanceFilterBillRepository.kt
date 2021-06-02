package com.example.practicafacturas.domain.usecase.repositoryconnection

import com.example.practicafacturas.data.repository.BillsRepository
import com.example.practicafacturas.domain.model.Bill
import java.time.LocalDate

class InstanceFilterBillRepository : InterfaceFilterBillRepository {
    override fun getListBillFilteredFromDatabase(
        pagado: String,
        pendiente: String,
        importeOrdenacion: Float,
        desde: LocalDate?,
        hasta: LocalDate?
    ): List<Bill> {
        return BillsRepository.getListBillFilteredFromDatabase(pagado, pendiente, importeOrdenacion, desde, hasta)
    }
}