package com.example.practicafacturas.domain.model

import androidx.annotation.NonNull
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDate

/**
 * Es la clase modelo de factura
 */
@Entity
data class Bill(
    @NonNull
    val descEstado : String,
    @NonNull
    val importeOrdenacion : Float,
    @PrimaryKey
    @NonNull
    val fecha : LocalDate){
}