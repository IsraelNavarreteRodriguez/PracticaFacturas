package com.example.practicafacturas.ui.factura.utils

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.practicafacturas.data.model.Factura
import com.google.gson.JsonArray
import java.math.BigDecimal
import java.math.RoundingMode
import java.text.SimpleDateFormat
import java.util.*

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
                        it.asJsonObject.get("descEstado").toString().substring(1,it.asJsonObject.get("descEstado").toString().length-1),
                        BigDecimal(it.asJsonObject.get("importeOrdenacion").asDouble).setScale(2,RoundingMode.HALF_EVEN).toDouble(),
                        //dateParser(it.asJsonObject.get("fecha").toString().substring(1,it.asJsonObject.get("fecha").toString().length-1))
                        //todo hacer que funcione el dateParser
                        Date(it.asJsonObject.get("fecha").toString().substring(1,it.asJsonObject.get("fecha").toString().length-1))
                    )
                )
            }
            Log.d("json", "Lista: $mutableList")
            return mutableList
        }

        /**
         * Recoge las fechas del json (07/02/2021) y lo convierte a (07 Feb. 2021)
         */
        private fun dateParser(dateJson: String): Date {
            val initialFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
            val date : Date = initialFormat.parse(dateJson)!!
            val finalFormat = SimpleDateFormat("dd MMM yyyy", Locale.getDefault())
            return finalFormat.parse(date.toString())!!
        }
    }


}