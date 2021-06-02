package com.example.practicafacturas.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.practicafacturas.domain.model.Bill
import java.time.LocalDate

@Dao
abstract class BillDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    abstract suspend fun insert(bill: Bill): Long

    @Query("SELECT * FROM Bill ORDER BY fecha DESC")
    abstract suspend fun getUnfilteredBills(): List<Bill>

    @Query("SELECT * FROM Bill WHERE (descEstado=:pagado OR descEstado=:pendiente) AND importeOrdenacion<=:importeOrdenacion AND fecha BETWEEN :desde AND :hasta")
    abstract suspend fun getfilteredBills(
        pagado: String,
        pendiente: String,
        importeOrdenacion: Float,
        desde: LocalDate?,
        hasta: LocalDate?
    ): List<Bill>


}