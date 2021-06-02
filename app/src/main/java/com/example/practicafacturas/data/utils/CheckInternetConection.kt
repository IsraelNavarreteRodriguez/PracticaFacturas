package com.example.practicafacturas.data.utils

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.runBlocking
import java.net.InetAddress

class CheckInternetConection {

    companion object{
        private suspend fun isInternetAvailable(): Boolean {
            val coroutine = CoroutineScope(Dispatchers.IO)
            val result = coroutine.async {
                try {
                    val ipAddr: InetAddress = InetAddress.getByName("google.com")
                    //You can replace it with your name
                    !ipAddr.equals("")
                } catch (e: Exception) {
                    false
                }
            }
            return result.await()
        }

        fun returnInternetAvaliable(): Boolean =
            runBlocking {
                isInternetAvailable()
            }
    }

}