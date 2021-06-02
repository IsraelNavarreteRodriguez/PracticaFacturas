package com.example.practicafacturas.domain.utils

import java.time.LocalDate
import java.time.format.DateTimeFormatter

class DateParser {

    companion object{
        /**
         * Recoge las fechas del json (07/02/2021) y lo convierte a (2021-02-07)
         */
        fun parseToCompareBetweenDates(dateJson: String): LocalDate {
            val initialFormat = DateTimeFormatter.ofPattern("dd/MM/yyyy")
            return LocalDate.parse(dateJson, initialFormat)
        }

        /**
         * Recoge las fechas del json (07/02/2021) y lo convierte a (07 Feb. 2021)
         */
        fun parseForPrintingInScreen(dateJson: String): String {
            val initialFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd")
            val finalFormat = DateTimeFormatter.ofPattern("dd MMM yyyy")
            val date = LocalDate.parse(dateJson, initialFormat)
            return date.format(finalFormat)
        }

        /**
         * Recoge las fechas y las transforma en texto valido para los botones
         */
        fun parseForTextButton(dateJson: String): String {
            val initialFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd")
            val finalFormat = DateTimeFormatter.ofPattern("dd/MM/yyyy")
            val date = LocalDate.parse(dateJson, initialFormat)
            return date.format(finalFormat)
        }
    }

}