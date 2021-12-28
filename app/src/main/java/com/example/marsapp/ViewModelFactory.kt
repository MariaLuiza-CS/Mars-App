package com.example.marsapp


import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.marsapp.data.local.entity.DatabaseHelper

class ViewModelFactory(private val dbHelper: DatabaseHelper) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {

        if (modelClass.isAssignableFrom(SignUpFragmentViewModel::class.java)){
            return SignUpFragmentViewModel(dbHelper) as T
        }

        throw IllegalArgumentException("Unknown class name")

    }
}