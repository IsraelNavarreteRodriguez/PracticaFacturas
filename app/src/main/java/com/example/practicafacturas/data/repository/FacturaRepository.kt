package com.example.practicafacturas.data.repository

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.practicafacturas.data.model.Factura
import com.example.practicafacturas.data.retrofit.FacturaInterface
import com.google.gson.Gson
import com.google.gson.JsonArray
import com.google.gson.JsonObject
import com.google.gson.internal.GsonBuildConfig
import kotlinx.coroutines.*
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * La funcion de esta clase es recoger todas las facturas del json eliminando datos innecesarios
 * se realiza en un hilo aparte para no bloquear la UI y nos aseguramos que se recibe el json
 */
class FacturaRepository {

    private var retrofit: Retrofit = Retrofit.Builder()
        .baseUrl("https://viewnextandroid.mocklab.io/facturas/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    companion object {
        val repository: FacturaRepository = FacturaRepository()
    }

    /**
     * Recoge las facturas del json de https://viewnextandroid.mocklab.io/facturas/ en un hilo aparte
     */
    suspend fun getJsonArray() : JsonArray?{
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