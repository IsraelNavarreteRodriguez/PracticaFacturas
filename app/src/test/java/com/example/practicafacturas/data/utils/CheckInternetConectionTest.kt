package com.example.practicafacturas.data.utils

import org.junit.Test

class CheckInternetConectionTest{

    @Test
    fun isInternetAvaliable(){
        assert(CheckInternetConection.returnInternetAvaliable())
    }


}