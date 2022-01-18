package com.example.marsapp.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.marsapp.data.local.dao.UserDao
import com.example.marsapp.data.local.entity.User
import com.example.marsapp.utils.Resource.Companion.MARS_DATABASE_VERSION

@Database(entities = [User::class], version = MARS_DATABASE_VERSION)
abstract class AppDatabase : RoomDatabase() {

    abstract fun userDao(): UserDao
}