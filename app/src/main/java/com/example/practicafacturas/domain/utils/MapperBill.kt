package com.example.practicafacturas.domain.utils

import com.example.practicafacturas.domain.model.Bill
import com.google.gson.JsonArray
import java.math.BigDecimal
import java.math.RoundingMode

/**
 * Convierte el jsonArray recibido del repository a una lista de facturas para mostrar en la app
 */
class MapperBill() {

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
                        DateParser.parseToCompareBetweenDates(
                            it.asJsonObject.get("fecha").toString()
                                .substring(1, it.asJsonObject.get("fecha").toString().length - 1)
                        )

                    )
                )
            }
            return mutableList.sortedByDescending {
                it.fecha
            }

        }

        /**
         * Recoge el importe maximo de la lista de facturas
         */
        fun getMaxImporte(list: List<Bill>): Float {
            return if (list.isNotEmpty()) {
                val newList = list.sortedBy {
                    it.importeOrdenacion
                }
                newList.last().importeOrdenacion
            } else
                100F
        }


        /**
         * Recoge el importe minimo de la lista de facturas
         */
        fun getMinImporte(listBill: List<Bill>): Float {
            return if (listBill.isNotEmpty()) {
                val newList = listBill.sortedBy {
                    it.importeOrdenacion
                }
                newList.first().importeOrdenacion
            } else
                0F
        }

    }
}
