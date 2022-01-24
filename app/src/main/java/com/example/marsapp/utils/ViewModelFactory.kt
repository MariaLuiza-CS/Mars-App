package com.example.marsapp.utils


import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.marsapp.data.local.database.DatabaseHelper
import com.example.marsapp.ui.fragments.login.LoginFragmentViewModel
import com.example.marsapp.ui.fragments.signup.SignUpFragmentViewModel
import com.example.marsapp.ui.fragments.user.UserFragmentViewModel

class ViewModelFactory(private val dbHelper: DatabaseHelper, private val application: Application) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {

        if (modelClass.isAssignableFrom(SignUpFragmentViewModel::class.java)) {
            return SignUpFragmentViewModel(dbHelper, application) as T
        }

        if (modelClass.isAssignableFrom(LoginFragmentViewModel::class.java)) {
            return LoginFragmentViewModel(dbHelper, application) as T
        }

        if (modelClass.isAssignableFrom(UserFragmentViewModel::class.java)) {
            return UserFragmentViewModel(dbHelper, application) as T
        }

        throw IllegalArgumentException("Unknown class name")

    }
}