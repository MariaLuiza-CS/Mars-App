package com.example.marsapp

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.marsapp.data.local.entity.DatabaseHelper
import com.example.marsapp.data.local.entity.User
import com.example.marsapp.utils.Resource
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.launch

class SignUpFragmentViewModel(private val dbHelper: DatabaseHelper) : ViewModel() {

    private val usersList = MutableLiveData<Resource<List<User>>>()

    init {
        fetchAllUsers()
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

    fun createUser(name: String, email: String, password: String) {
        viewModelScope.launch {
            try {
                dbHelper.insertUser(User(0, name, email, password))
            } catch (e: Exception) {
                Log.e("Error Insert User", e.toString())
            }
        }
    }

    fun getUsers():LiveData<Resource<List<User>>>{
        return usersList
    }


}