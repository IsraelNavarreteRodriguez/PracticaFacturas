package com.example.practicafacturas.data.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.practicafacturas.data.dao.BillDao
import com.example.practicafacturas.domain.model.Bill
import com.example.practicafacturas.domain.utils.LocalDateTimeConverter
import java.util.concurrent.Executors

//1. Definir configuracion de la bd
@Database(entities = [Bill::class], version = 1)
@TypeConverters(LocalDateTimeConverter::class)
abstract class AppDatabase : RoomDatabase() {
    //2. Crear los métodos de obtención de los DAO
    abstract fun billDao() : BillDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null
        private val NUMBER_OF_THREADS = 4
        var TEST_MODE = false
        val databaseWriteExecutor =
            Executors.newFixedThreadPool(NUMBER_OF_THREADS)

        fun create(context: Context) {
            if (INSTANCE == null) {
                if (!TEST_MODE){
                    synchronized(AppDatabase::class.java) {
                        if (INSTANCE == null) {
                            INSTANCE =
                                Room.databaseBuilder(
                                    context.applicationContext,
                                    AppDatabase::class.java,
                                    "database"
                                )
                                    .build()
                        }
                    }
                }else{
                    synchronized(AppDatabase::class.java) {
                        if (INSTANCE == null) {
                            INSTANCE =
                                Room.inMemoryDatabaseBuilder(
                                    context.applicationContext,
                                    AppDatabase::class.java
                                )
                                    .build()
                        }
                    }
                }

            }
        }


        fun getDataBase(): AppDatabase? {
            return INSTANCE
        }
    }
}