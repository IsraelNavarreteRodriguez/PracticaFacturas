package com.example.practicafacturas.data.service

import com.example.practicafacturas.data.retrofit.RetrofitInterface
import com.google.gson.JsonObject
import com.google.gson.JsonParser
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import io.mockk.mockkObject
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.create

class DownloadJsonBillServiceTest {

    val service = DownloadJsonBillService()

    val jsonarray  = JsonParser.parseString("[\n" +
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



    @Test
    fun getJsonArray() {
        Assert.assertEquals(jsonarray,service.getJsonArray())
    }
}