package com.example.marsapp.utils


import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.marsapp.data.local.entity.DatabaseHelper
import com.example.marsapp.ui.fragments.signup.SignUpFragmentViewModel

class ViewModelFactory(private val dbHelper: DatabaseHelper) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {

        if (modelClass.isAssignableFrom(SignUpFragmentViewModel::class.java)){
            return SignUpFragmentViewModel(dbHelper) as T
        }

        throw IllegalArgumentException("Unknown class name")

    }
}