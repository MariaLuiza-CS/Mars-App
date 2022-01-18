package com.example.marsapp.data.local.database

import android.content.Context
import androidx.room.Room
import com.example.marsapp.utils.Resource

object DatabaseBuilder {

    private var INSTANCE: AppDatabase? = null

    fun getInstance(context: Context): AppDatabase {
        if (INSTANCE == null){
            synchronized(AppDatabase::class){
                INSTANCE = buildRoomDb(context)
            }
        }
        return INSTANCE!!
    }

    private fun buildRoomDb(context: Context) =
        Room.databaseBuilder(
            context.applicationContext,
            AppDatabase::class.java,
            Resource.MARS_DATABASE_NAME
        ).build()
}