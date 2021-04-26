package com.example.practicafacturas.ui.factura.utils

import android.util.Log
import com.example.practicafacturas.data.model.Bill
import com.google.gson.JsonArray
import java.math.BigDecimal
import java.math.RoundingMode
import java.time.LocalDate
import java.time.format.DateTimeFormatter

/**
 * Convierte el jsonArray recibido del repository a una lista de facturas para mostrar en la app
 */
class JsonToBill() {

    companion object {

        private lateinit var mutableList: MutableList<Bill>

        /**
         * Recoge un json y lo vuelve una lista de facturas
         */
        fun jsonBillToListBill(json: JsonArray?): List<Bill> {
            mutableList = mutableListOf()
            json?.forEach {
                mutableList.add(
                    Bill(
                        it.asJsonObject.get("descEstado").toString()
                            .substring(1, it.asJsonObject.get("descEstado").toString().length - 1),
                        BigDecimal(it.asJsonObject.get("importeOrdenacion").asDouble).setScale(
                            2,
                            RoundingMode.HALF_EVEN
                        ).toFloat(),
                        dateParserToCompare(
                            it.asJsonObject.get("fecha").toString()
                                .substring(1, it.asJsonObject.get("fecha").toString().length - 1)
                        )

                    )
                )
            }
            Log.d("json", "Lista: $mutableList")
            return mutableList.sortedByDescending {
                it.fecha
            }

        }

        /**
         * Recoge el importe maximo de la lista de facturas
         */
        fun getMaxImporte(list: List<Bill>): Float {
            val newList = list.sortedBy {
                it.importeOrdenacion
            }
            return newList.last().importeOrdenacion
        }

        /**
         * Recoge las fechas del json (07/02/2021) y lo convierte a (2021-02-07)
         */
        fun dateParserToCompare(dateJson: String): LocalDate {
            val initialFormat = DateTimeFormatter.ofPattern("dd/MM/yyyy")
            return LocalDate.parse(dateJson, initialFormat)
        }

        /**
         * Recoge las fechas del json (07/02/2021) y lo convierte a (07 Feb. 2021)
         */
        fun dateParserForPrinting(dateJson: String): String {
            val initialFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd")
            val finalFormat = DateTimeFormatter.ofPattern("dd MMM yyyy")
            val date = LocalDate.parse(dateJson, initialFormat)
            return date.format(finalFormat)
        }

        /**
         * Recoge las fechas y las transforma en texto valido para los botones
         */
        fun dateParseForTextButton(dateJson: String): String {
            val initialFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd")
            val finalFormat = DateTimeFormatter.ofPattern("dd/MM/yyyy")
            val date = LocalDate.parse(dateJson, initialFormat)
            return date.format(finalFormat)
        }

        /**
         * Recoge el importe minimo de la lista de facturas
         */
        fun getMinImporte(listBill: List<Bill>): Float {
            val newList = listBill.sortedBy {
                it.importeOrdenacion
            }
            return newList.first().importeOrdenacion
        }
    }


}
