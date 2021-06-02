package com.example.practicafacturas.domain.usecase.repositoryconnection

import com.example.practicafacturas.domain.model.Bill
import java.time.LocalDate

interface InterfaceFilterBillRepository {

    /**
     * Metodo encargado de recoger todas las bills filtradas de la base de datos
     */
    fun getListBillFilteredFromDatabase(
        pagado: String,
        pendiente: String,
        importeOrdenacion: Float,
        desde: LocalDate?,
        hasta: LocalDate?) : List<Bill>
}