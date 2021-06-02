package com.example.practicafacturas.domain.usecase

import com.example.practicafacturas.domain.usecase.repositoryconnection.InstanceFilterBillRepository
import org.junit.Assert
import org.junit.Test

class UseCaseButtonFilterTest {

    private val useCaseButtonFilter = UseCaseButtonFilter(InstanceFilterBillRepository())


    @Test
    fun getStringEstadoPagado() {
        val result = "Pagada"
        Assert.assertEquals(result,useCaseButtonFilter.getStringEstadoPagado(true))
    }
    @Test
    fun getStringEstadoPagadoEmpty() {
        val result = ""
        Assert.assertEquals(result,useCaseButtonFilter.getStringEstadoPagado(false))
    }
    @Test
    fun getStringEstadoPendiente() {
        val result = "Pendiente de pago"
        Assert.assertEquals(result,useCaseButtonFilter.getStringEstadoPendiente(true))
    }
    @Test
    fun getStringEstadoPendienteEmpty() {
        val result = ""
        Assert.assertEquals(result,useCaseButtonFilter.getStringEstadoPendiente(false))
    }

}