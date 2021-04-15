package com.example.practicafacturas.ui.factura.service

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.util.Log
import com.example.practicafacturas.data.retrofit.FacturaInterface
import com.google.gson.JsonArray
import kotlinx.coroutines.*
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Serivio encargado de descargar el json
 */
class DownloadService : Service() {

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
    private suspend fun getJsonArray() : JsonArray?{
        var jsonArray : JsonArray? = null
        val coroutine = CoroutineScope(Dispatchers.IO)
        val result : Deferred<JsonArray?> = coroutine.async{
            val call = retrofit.create(FacturaInterface::class.java).getAllFacturas().execute()
            if (call.isSuccessful) {
                jsonArray = call.body()!!.get("facturas").asJsonArray
                Log.d("json", "JsonArray:  $jsonArray")
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
    fun returnJsonArray() : JsonArray? = runBlocking {
        getJsonArray()
    }
}