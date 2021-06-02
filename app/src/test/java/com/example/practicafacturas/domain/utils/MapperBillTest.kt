package com.example.practicafacturas.domain.utils

import com.example.practicafacturas.domain.model.Bill
import com.google.gson.JsonParser
import org.junit.Assert
import org.junit.Test
import java.time.LocalDate

class MapperBillTest {

    private val list = listOf(
        Bill(	"Pagada"	,22.690000534057617F	, LocalDate.parse("2019-01-08")),
        Bill(	"Pagada"	,35.15999984741211F	, LocalDate.parse("2018-11-16")),
        Bill(	"Pagada"	,18.270000457763672F	, LocalDate.parse("2018-10-05")),
        Bill(	"Pendiente de pago"	,61.16999816894531F	, LocalDate.parse("2018-09-05")),
        Bill(	"Pagada"	,37.18000030517578F	, LocalDate.parse("2018-08-07")),
        Bill(	"Pendiente de pago"	,12.84000015258789F	, LocalDate.parse("2018-12-07")),
        Bill(	"Pagada"	,25.139999389648438F	, LocalDate.parse("2019-02-05")),
        Bill(	"Pendiente de pago"	,1.559999942779541F	, LocalDate.parse("2019-02-07"))
    )

    @Test
    fun jsonBillToListBill() {        val jsonarray = JsonParser.parseString("[\n" +
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
        Assert.assertEquals(list.sortedByDescending { it.fecha },MapperBill.jsonBillToListBill(jsonarray))
    }

    @Test
    fun getMaxImporte(){
        val result = 61.16999816894531F
        Assert.assertEquals(result,MapperBill.getMaxImporte(list))
    }

    @Test
    fun getMinImporte(){
        val result = 1.559999942779541F
        Assert.assertEquals(result,MapperBill.getMinImporte(list))
    }
}