package com.example.marsapp.ui.fragments.signup

import android.app.Application
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.marsapp.data.local.database.DatabaseHelper
import com.example.marsapp.data.local.entity.User
import com.example.marsapp.data.repository.AppRepository
import com.example.marsapp.utils.Resource
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.launch

class SignUpFragmentViewModel(private val dbHelper: DatabaseHelper, application: Application) :
    ViewModel() {

    private val usersList = MutableLiveData<Resource<List<User>>>()
    private val appRepository = AppRepository(application)
    private var userMutableLiveData: MutableLiveData<FirebaseUser>

    init {
        fetchAllUsers()
        userMutableLiveData = appRepository.getUserMutableLiveData()
    }

    private fun fetchAllUsers() {
        usersList.postValue(Resource.loading(null))
        viewModelScope.launch {
            try {
                usersList.postValue(Resource.success(dbHelper.getAllUsers()))
            } catch (e: Exception) {
                usersList.postValue(Resource.error("error to get all users", null))
            }
        }
    }

    fun registerUser(name: String, email: String, password: String) {
        appRepository.registerUser(name, email, password)
        createUser(name, email, password)
    }

    private fun createUser(name: String, email: String, password: String) {
        viewModelScope.launch {
            try {
                dbHelper.insertUser(User(0, name, email, password))
            } catch (e: Exception) {
                Log.e("Error Insert User", e.toString())
            }
        }
    }

    fun getUserMutableLiveData(): MutableLiveData<FirebaseUser> {
        return userMutableLiveData
    }


}