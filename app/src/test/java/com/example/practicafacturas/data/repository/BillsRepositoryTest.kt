package com.example.practicafacturas.data.repository

import com.example.practicafacturas.data.dao.BillDao
import com.example.practicafacturas.data.room.AppDatabase
import com.example.practicafacturas.data.utils.CheckInternetConection
import com.example.practicafacturas.domain.model.Bill
import com.example.practicafacturas.domain.model.InvoiceFilter
import io.mockk.*
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import java.time.LocalDate

class BillsRepositoryTest {

    //No mockear lo que quiero testear
    private lateinit var repository: BillsRepository


    @MockK
    lateinit var database: AppDatabase

    @MockK
    lateinit var dao: BillDao

    private val defaultFilter = InvoiceFilter()


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
    private val filteredList = listOf(
        Bill("Pagada", 22.690000534057617F, LocalDate.parse("2019-01-08")),
        Bill("Pagada", 35.15999984741211F, LocalDate.parse("2018-11-16")),
        Bill("Pagada", 18.270000457763672F, LocalDate.parse("2018-10-05")),
        Bill("Pagada", 37.18000030517578F, LocalDate.parse("2018-08-07")),
        Bill("Pagada", 25.139999389648438F, LocalDate.parse("2019-02-05"))
    )

    @Before
    fun init() {
        MockKAnnotations.init(this)
        mockkObject(AppDatabase)
        dao = mockk()
        mockkObject(CheckInternetConection)
        every { AppDatabase.getDataBase() } returns database
        every { database.billDao() } returns dao
        repository = BillsRepository
    }

    @Test
    fun returnListBillUnfilteredFromDatabaseOrRetrofit() {
        every {
            runBlocking {
                dao.getUnfilteredBills()
            }
        } returns list
        assertEquals(
            list.size,
            repository.returnAllBillsFromDatabaseOrRetrofit().size
        )
    }

    @Test
    fun getListBillFilteredFromDatabase() {
        every {
            runBlocking {
                dao.getfilteredBills(
                    "",
                    "",
                    30F,
                    LocalDate.parse("2020-10-10"),
                    LocalDate.parse("2020-10-11")
                )
            }
        } returns filteredList
        assertEquals(
            filteredList.size,
            repository.getListBillFilteredFromDatabase(
                "",
                "",
                30F,
                LocalDate.parse("2020-10-10"),
                LocalDate.parse("2020-10-11")
            ).size
        )
    }


    @Test
    fun returnAllBillsFromDatabaseOrRetrofitWithoutDatabase(){
        every { AppDatabase.getDataBase() } returns null
        every { CheckInternetConection.returnInternetAvaliable() } returns false
        assertEquals(listOf<Bill>(),repository.returnAllBillsFromDatabaseOrRetrofit())
    }
    @Test
    fun returnAllBillsFromDatabaseOrRetrofitWithDatabase(){
        assertEquals(list.sortedByDescending { it.fecha },repository.returnAllBillsFromDatabaseOrRetrofit())
    }



    @After
    fun fin() {
        unmockkAll()
    }


}