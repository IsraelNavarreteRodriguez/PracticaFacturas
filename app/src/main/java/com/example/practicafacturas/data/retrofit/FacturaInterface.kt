package com.example.practicafacturas.data.retrofit

import com.example.practicafacturas.data.model.Factura
import com.google.gson.JsonObject
import retrofit2.Call
import retrofit2.http.GET

interface FacturaInterface{
    @GET("https://viewnextandroid.mocklab.io/facturas")
    fun getAllFacturas() : Call<JsonObject>
}