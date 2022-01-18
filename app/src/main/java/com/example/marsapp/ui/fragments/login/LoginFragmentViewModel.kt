package com.example.marsapp.ui.fragments.login

import android.app.Application
import android.provider.ContactsContract
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.marsapp.data.local.database.DatabaseHelper
import com.example.marsapp.data.repository.AppRepository

class LoginFragmentViewModel(private val dbHelper: DatabaseHelper, application: Application) :
    ViewModel() {

    private val appRepository = AppRepository(application)
    private var isUserEmailVerified: MutableLiveData<Boolean>

    init {
        isUserEmailVerified = appRepository.getIsUserEmailVerified()
    }

    fun loginUser(email: String, password: String) {
        appRepository.loginUser(email, password)
    }

    fun getIsUserEmailVerified(): MutableLiveData<Boolean> {
        return isUserEmailVerified
    }

}