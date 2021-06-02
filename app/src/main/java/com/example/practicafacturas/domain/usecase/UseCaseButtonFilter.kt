package com.example.practicafacturas.domain.usecase

import com.example.practicafacturas.domain.model.Bill
import com.example.practicafacturas.domain.model.InvoiceFilter
import com.example.practicafacturas.domain.usecase.repositoryconnection.InterfaceFilterBillRepository

class UseCaseButtonFilter(private val interfaceFilterBillRepository: InterfaceFilterBillRepository) {

    /**
     * Metodo de caso de uso del boton filter
     */
    fun getListBill(filter: InvoiceFilter): List<Bill> {
        var pagado = getStringEstadoPagado(filter.pagado)
        var pendiente = getStringEstadoPendiente(filter.pendiente)
        if (pagado == "" && pendiente == ""){
            pagado = "Pagada"
            pendiente = "Pendiente de pago"
        }
        return interfaceFilterBillRepository.getListBillFilteredFromDatabase(
            pagado,
            pendiente,
            filter.importe,
            filter.desde,
            filter.hasta
        )
    }

    /**
     * Devuelve el valor para el filtrado segun si el checkbox es verdadero o falso
     */
    fun getStringEstadoPagado(pagado: Boolean): String {
        return if (pagado)
            "Pagada"
        else
            ""

    }
    /**
     * Devuelve el valor para el filtrado segun si el checkbox es verdadero o falso
     */
    fun getStringEstadoPendiente(pendiente: Boolean): String {
        return if (pendiente)
            "Pendiente de pago"
        else
            ""
    }
}