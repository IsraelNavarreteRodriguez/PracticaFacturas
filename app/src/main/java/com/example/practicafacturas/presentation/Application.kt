package com.example.practicafacturas.presentation

import android.app.Application
import com.example.practicafacturas.data.room.AppDatabase

class Application : Application() {
    override fun onCreate() {
        super.onCreate()
        AppDatabase.create(this)
    }
}