package com.example.marsapp.data.local.entity

interface DatabaseHelper {

    suspend fun getAllUsers() : List<User>

    suspend fun insertUser(user: User)
}