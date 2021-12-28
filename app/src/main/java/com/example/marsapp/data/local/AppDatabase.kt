package com.example.marsapp.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.marsapp.data.local.dao.UserDao
import com.example.marsapp.data.local.entity.User

@Database(entities = [User::class], version = 1)
abstract class AppDatabase : RoomDatabase() {

    abstract fun userDao(): UserDao
}