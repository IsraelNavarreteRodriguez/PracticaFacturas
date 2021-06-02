package com.example.practicafacturas.domain.usecase.`interface`

import com.example.practicafacturas.domain.model.Bill
import com.example.practicafacturas.domain.usecase.repositoryconnection.InterfaceFilterBillRepository
import com.example.practicafacturas.domain.usecase.repositoryconnection.InterfaceGetAllBillsRepository
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Test
import org.mockito.Mockito
import org.powermock.api.mockito.PowerMockito
import java.time.LocalDate

class InterfacesTest {
    private val list = listOf(
        Bill("Pagada", 22.690000534057617F, LocalDate.parse("2019-01-08")),
        Bill("Pagada", 35.15999984741211F, LocalDate.parse("2018-11-16")),
        Bill("Pagada", 18.270000457763672F, LocalDate.parse("2018-10-05")),
        Bill("Pendiente de pago", 61.16999816894531F, LocalDate.parse("2018-09-05")),
        Bill("Pagada", 37.18000030517578F, LocalDate.parse("2018-08-07")),
        Bill("Pendiente de pago", 12.84000015258789F, LocalDate.parse("2018-12-07")),
        Bill("Pagada", 25.139999389648438F, LocalDate.parse("2019-02-05")),
        Bill("Pendiente de pago", 1.559999942779541F, LocalDate.parse("2019-02-07"))
    )
    @Test
    fun returnUnfilteredListBillTest() {
        val mock = Mockito.mock(InterfaceGetAllBillsRepository::class.java)
        PowerMockito.`when`(
            mock.returnAllBillsFromDatabaseOrRetrofit()
        ).thenReturn(list)
        Assert.assertEquals(
            list,
            runBlocking {
                mock.returnAllBillsFromDatabaseOrRetrofit()
            }
        )
    }

    @Test
    fun returnListBillFilteredTest() {
        val mock = Mockito.mock(InterfaceFilterBillRepository::class.java)
        PowerMockito.`when`(
            mock.getListBillFilteredFromDatabase("Pagada", "Pendiente de pago", 62F,
                LocalDate.parse("2017-10-10"),
                LocalDate.parse("2020-10-10"))
        ).thenReturn(
            listOf()
        )
        Assert.assertEquals(
            listOf<Bill>(),
            runBlocking {
                mock.getListBillFilteredFromDatabase(
                    "Pagada", "Pendiente de pago", 62F,
                    LocalDate.parse("2017-10-10"),
                    LocalDate.parse("2020-10-10")
                )
            }
        )
    }
}