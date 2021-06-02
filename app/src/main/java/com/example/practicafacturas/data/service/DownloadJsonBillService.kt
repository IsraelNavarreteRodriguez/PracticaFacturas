package com.example.practicafacturas.data.service

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.util.Log
import com.example.practicafacturas.data.retrofit.RetrofitInterface
import com.google.gson.JsonArray
import com.google.gson.JsonParser
import kotlinx.coroutines.*
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Serivio encargado de descargar el json
 */
class DownloadJsonBillService : Service() {

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }
    private var retrofit: Retrofit = Retrofit.Builder()
        .baseUrl("https://viewnextandroid.mocklab.io/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()



    /**
     * Recoge las facturas del json de https://viewnextandroid.mocklab.io/facturas/ en un hilo aparte
     */
    private suspend fun downloadJsonArray() : JsonArray?{
        var jsonArray : JsonArray? = null
        val coroutine = CoroutineScope(Dispatchers.IO)
        val result : Deferred<JsonArray?> = coroutine.async{
            val call = retrofit.create(RetrofitInterface::class.java).getAllBills().execute()
            if (call.isSuccessful) {
                jsonArray = call.body()!!.get("facturas").asJsonArray
               // Log.d("json", "JsonArray:  $jsonArray")
            }
            else
                Log.d("json","Error no se ha podido cargar el json")
            jsonArray
        }
        return result.await()
    }

    /**
     * Asegura que la recogida de datos sea válida y no hay errores por usar hilos diferentes
     */
    fun getJsonArray() : JsonArray? = runBlocking {
        downloadJsonArray()
    }

    /*fun getJsonArrayTest() : JsonArray?{
        return JsonParser.parseString("[\n" +
                "    {\n" +
                "      \"descEstado\": \"Pendiente de pago\",\n" +
                "      \"importeOrdenacion\": 1.5600000000000001,\n" +
                "      \"fecha\": \"07/02/2019\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"descEstado\": \"Pagada\",\n" +
                "      \"importeOrdenacion\": 25.140000000000001,\n" +
                "      \"fecha\": \"05/02/2019\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"descEstado\": \"Pagada\",\n" +
                "      \"importeOrdenacion\": 22.690000000000001,\n" +
                "      \"fecha\": \"08/01/2019\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"descEstado\": \"Pendiente de pago\",\n" +
                "      \"importeOrdenacion\": 12.84,\n" +
                "      \"fecha\": \"07/12/2018\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"descEstado\": \"Pagada\",\n" +
                "      \"importeOrdenacion\": 35.159999999999997,\n" +
                "      \"fecha\": \"16/11/2018\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"descEstado\": \"Pagada\",\n" +
                "      \"importeOrdenacion\": 18.27,\n" +
                "      \"fecha\": \"05/10/2018\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"descEstado\": \"Pendiente de pago\",\n" +
                "      \"importeOrdenacion\": 61.170000000000002,\n" +
                "      \"fecha\": \"05/09/2018\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"descEstado\": \"Pagada\",\n" +
                "      \"importeOrdenacion\": 37.18,\n" +
                "      \"fecha\": \"07/08/2018\"\n" +
                "    }\n" +
                "  ]").asJsonArray
    }*/
}