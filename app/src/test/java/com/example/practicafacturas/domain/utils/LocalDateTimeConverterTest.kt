package com.example.practicafacturas.domain.utils

import org.junit.Assert
import org.junit.Test
import java.time.LocalDate

class LocalDateTimeConverterTest {

    private val localDateTimeConverter = LocalDateTimeConverter()

    @Test
    fun toDate() {
        val result = LocalDate.parse("2020-10-10")
        Assert.assertEquals(result,localDateTimeConverter.toDate("2020-10-10"))
    }

    @Test
    fun toDateString() {
        val result = "2020-10-10"
        Assert.assertEquals(result,localDateTimeConverter.toDateString(LocalDate.parse("2020-10-10")))
    }
    @Test
    fun toDateNull() {
        val result = null
        Assert.assertEquals(result,localDateTimeConverter.toDate(null))
    }

    @Test
    fun toDateStringNull() {
        val result = null
        Assert.assertEquals(result,localDateTimeConverter.toDateString(null))
    }
}