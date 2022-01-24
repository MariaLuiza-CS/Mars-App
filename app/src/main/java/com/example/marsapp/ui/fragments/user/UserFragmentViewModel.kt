package com.example.marsapp.ui.fragments.user

import android.app.Application
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.marsapp.data.local.database.DatabaseHelper
import com.example.marsapp.data.repository.AppRepository

class UserFragmentViewModel(private val dbHelper: DatabaseHelper, application: Application) :
    ViewModel() {

    private val appRepository = AppRepository(application)
    private var isUserDiconnect: MutableLiveData<Boolean>

    init {
        isUserDiconnect = appRepository.getIsUserDisconnect()
    }

    fun logoutUser() {
        appRepository.logoutUser()
    }

    fun getIsUserDiconnect(): MutableLiveData<Boolean> {
        return isUserDiconnect
    }
}