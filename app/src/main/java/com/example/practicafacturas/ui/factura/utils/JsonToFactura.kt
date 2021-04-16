package com.example.practicafacturas.ui.factura.utils

import android.util.Log
import com.example.practicafacturas.data.model.Factura
import com.google.gson.JsonArray
import java.math.BigDecimal
import java.math.RoundingMode
import java.time.LocalDate
import java.time.format.DateTimeFormatter

/**
 * Convierte el jsonArray recibido del repository a una lista de facturas para mostrar en la app
 */
class JsonToFactura() {

    companion object {

        private lateinit var mutableList: MutableList<Factura>

        /**
         * Recoge un json y lo vuelve una lista de facturas
         */
        fun parseToList(json: JsonArray?): List<Factura> {
            mutableList = mutableListOf()
            json?.forEach {
                mutableList.add(
                    Factura(
                        it.asJsonObject.get("descEstado").toString()
                            .substring(1, it.asJsonObject.get("descEstado").toString().length - 1),
                        BigDecimal(it.asJsonObject.get("importeOrdenacion").asDouble).setScale(
                            2,
                            RoundingMode.HALF_EVEN
                        ).toDouble(),
                        dateParser(
                            it.asJsonObject.get("fecha").toString()
                                .substring(1, it.asJsonObject.get("fecha").toString().length - 1)
                        )

                    )
                )
            }
            Log.d("json", "Lista: $mutableList")
            mutableList.sortedBy {
                it.fecha
            }
            return mutableList
        }


      fun getMaxImporte(list : List<Factura>): Double {
          list.sortedBy {
              it.importeOrdenacion
          }
          return list.last().importeOrdenacion
      }

        /**
         * Recoge las fechas del json (07/02/2021) y lo convierte a
         */
        fun dateParser(dateJson: String): LocalDate {
            val initialFormat = DateTimeFormatter.ofPattern("dd/MM/yyyy")
            return LocalDate.parse(dateJson, initialFormat)
        }

        /**
         * Recoge las fechas del json (07/02/2021) y lo convierte a (07 Feb. 2021)
         */
        fun dateParserPrint(dateJson: String): String {
            val initialFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd")
            val finalFormat = DateTimeFormatter.ofPattern("dd MMM yyyy")
            val date = LocalDate.parse(dateJson, initialFormat)
            return date.format(finalFormat)
        }
    }


}
