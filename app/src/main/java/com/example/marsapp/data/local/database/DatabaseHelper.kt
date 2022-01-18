package com.example.marsapp.data.local.database

import com.example.marsapp.data.local.entity.User

interface DatabaseHelper {

    suspend fun getAllUsers() : List<User>

    suspend fun insertUser(user: User)
}