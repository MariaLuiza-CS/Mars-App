package com.example.marsapp.utils

data class Resource<out T>(val status: Status, val data: T?, val message: String?) {

    companion object {

        fun <T> success(data: T?): Resource<T> {
            return Resource(Status.SUCCESS, data, null)
        }

        fun <T> error(message: String, data: T?): Resource<T> {
            return Resource(Status.ERROR, data, message)
        }

        fun <T> loading(data: T?): Resource<T> {
            return Resource(Status.LOADING, data, null)
        }

        //Database const
        const val MARS_DATABASE_NAME = "mars-app-database"
        const val MARS_DATABASE_VERSION = 1
        //Database const

        //Firebase Database
        const val FIREBASE_DATABASE_PATH_USER = "users"
        //Firebase Database
    }

}
