package com.example.practicafacturas.data.retrofit

import com.google.gson.JsonObject
import retrofit2.Call
import retrofit2.http.GET

/**
 * Interfaz encargada de tener los metodos de Retrofit para recoger el json
 */
interface FacturaInterface{
    /**
     * Metodo que recoge las facturas dehttps://viewnextandroid.mocklab.io/facturas
     */
    @GET("facturas")
    fun getAllFacturas() : Call<JsonObject>
}