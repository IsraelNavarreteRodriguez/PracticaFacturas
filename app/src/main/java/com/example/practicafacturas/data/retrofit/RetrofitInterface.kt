package com.example.practicafacturas.data.retrofit

import com.google.gson.JsonObject
import retrofit2.Call
import retrofit2.http.GET

/**
 * Interfaz encargada de tener los metodos de Retrofit para recoger el json
 */
interface RetrofitInterface{
    /**
     * Metodo que recoge las facturas de https://viewnextandroid.mocklab.io/facturas
     */
    @GET("facturas")
    fun getAllBills() : Call<JsonObject>
}