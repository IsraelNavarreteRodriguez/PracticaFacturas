package com.example.practicafacturas.domain.utils

import org.junit.Assert
import org.junit.Test
import java.time.LocalDate

class DateParserTest{

    @Test
    fun parseToCompareBetweenDates() {
        val result = LocalDate.parse("2021-02-07")
        Assert.assertEquals(result,DateParser.parseToCompareBetweenDates("07/02/2021"))
    }

    @Test
    fun parseForPrintingInScreen() {
        val result = "07 feb 2021"
        Assert.assertEquals(result,DateParser.parseForPrintingInScreen("2021-02-07"))
    }

    @Test
    fun parseForTextButton() {
        val result = "07/02/2021"
        Assert.assertEquals(result,DateParser.parseForTextButton("2021-02-07"))
    }
}