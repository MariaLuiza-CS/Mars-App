package com.example.marsapp.data.local

import com.example.marsapp.data.local.entity.DatabaseHelper
import com.example.marsapp.data.local.entity.User

class DatabaseHelperImpl(private val appDatabase: AppDatabase) :DatabaseHelper {

    override suspend fun getAllUsers(): List<User> = appDatabase.userDao().getAllUsers()

    override suspend fun insertUser(user: User) = appDatabase.userDao().insertUser(user)

}