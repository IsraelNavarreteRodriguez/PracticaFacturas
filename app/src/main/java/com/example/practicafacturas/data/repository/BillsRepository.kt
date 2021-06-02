package com.example.practicafacturas.data.repository

import com.example.practicafacturas.data.room.AppDatabase
import com.example.practicafacturas.data.service.DownloadJsonBillService
import com.example.practicafacturas.data.utils.CheckInternetConection
import com.example.practicafacturas.domain.model.Bill
import com.example.practicafacturas.domain.model.InvoiceFilter
import com.example.practicafacturas.domain.usecase.repositoryconnection.InterfaceFilterBillRepository
import com.example.practicafacturas.domain.usecase.repositoryconnection.InterfaceGetAllBillsRepository
import com.example.practicafacturas.domain.utils.MapperBill
import kotlinx.coroutines.*
import java.time.LocalDate

object BillsRepository :
    InterfaceGetAllBillsRepository, InterfaceFilterBillRepository {

    private lateinit var list: List<Bill>
    private val service = DownloadJsonBillService()
    private val billDao = AppDatabase.getDataBase()!!.billDao()


    private fun insert(list: List<Bill>) {
        val coroutine = CoroutineScope(Dispatchers.IO)
        coroutine.launch {
            list.forEach {
                billDao.insert(it)
            }
        }
    }

    /**
     * Todos los metodos de esta region se encargan de recoger la informacion de la base de datos filtrada
     */
    //region With Filter

    /**
     * Metodo sobreescrito de la interfaz InterfaceFilterBillRepository recoge todas las bills dentro del filtro especificado
     */
    override fun getListBillFilteredFromDatabase(
        pagado: String,
        pendiente: String,
        importeOrdenacion: Float,
        desde: LocalDate?,
        hasta: LocalDate?
    ): List<Bill> {
        return returnBillsFilteredFromDatabase(pagado, pendiente, importeOrdenacion, desde, hasta)
    }


    /**
     * Se conecta a la bd y recoge todas las bills dentro de los parametros del filtro
     */
    private suspend fun getBillsFilteredFromDatabase(
        pagado: String,
        pendiente: String,
        importeOrdenacion: Float,
        desde: LocalDate?,
        hasta: LocalDate?
    ): List<Bill> {
        val coroutine = CoroutineScope(Dispatchers.IO)
        val result = coroutine.async {
            billDao.getfilteredBills(
                pagado,
                pendiente,
                importeOrdenacion,
                desde,
                hasta
            )
        }
        return result.await()
    }

    /**
     * Devuelve las bills recogidas de la base de datos en el hilo princpial
     */
    private fun returnBillsFilteredFromDatabase(
        pagado: String,
        pendiente: String,
        importeOrdenacion: Float,
        desde: LocalDate?,
        hasta: LocalDate?
    ): List<Bill> =
        runBlocking {
            getBillsFilteredFromDatabase(
                pagado,
                pendiente,
                importeOrdenacion,
                desde,
                hasta
            )
        }

    //endregion


    /**
     * Los metodos de esta region se encargan de descargar
     * el json con retrofit e insertarla si hay conexion a internet,
     * de no haber acceden a la base de datos
     */
    //region Without Filter

    /**
     * Se conecta a la bd y recoge todas las bills
     */
    private suspend fun getAllBillsFromDatabase(
    ): List<Bill> {
        val coroutine = CoroutineScope(Dispatchers.IO)
        val result = coroutine.async {
            billDao.getUnfilteredBills()
        }
        return result.await()
    }

    /**
     * retorna la lista de bills en el hilo principal
     */
    private fun returnAllBillsFromDatabase(): List<Bill> =
        runBlocking {
            getAllBillsFromDatabase()
        }

    /**
     * Metodo sobreescrito de la interfaz InterfaceGetAllBillsRepository comprueba si hay conexion internet para descargar el json
     * de no haber recoge todas las bills disponibles de la base de datos
     */
    override fun returnAllBillsFromDatabaseOrRetrofit(): List<Bill> {
        when {
            CheckInternetConection.returnInternetAvaliable() -> {
                this.list = MapperBill.jsonBillToListBill(service.getJsonArray())
                insert(list)
            }
            AppDatabase.getDataBase() == null -> {
                this.list = listOf()
            }
            else -> this.list = returnAllBillsFromDatabase()
        }
        return this.list
    }


    /**
     * Metodo sobreescrito de la interfaz InterfaceGetAllBillsRepository setea el defaultfilter
     */
    override fun returnDefaultFilter(defaultFilter: InvoiceFilter): InvoiceFilter {
        return if (this.list.isNotEmpty()) {
            defaultFilter.importe = MapperBill.getMaxImporte(this.list)
            this.list.sortedByDescending { it.fecha }
            defaultFilter.desde = this.list.last().fecha
            defaultFilter.hasta = this.list.first().fecha
            defaultFilter
        } else {
            defaultFilter.importe = 100F
            defaultFilter.desde = LocalDate.parse("1900-10-10")
            defaultFilter.hasta = LocalDate.parse("2021-10-10")
            defaultFilter
        }
    }

    //endregion
}