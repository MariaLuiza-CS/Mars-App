package com.example.marsapp.data.repository

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import com.example.marsapp.R
import com.example.marsapp.data.local.entity.User
import com.example.marsapp.utils.Resource.Companion.FIREBASE_DATABASE_PATH_USER
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.FirebaseDatabase

class AppRepository(private var application: Application) {

    private var userMutableLiveData: MutableLiveData<FirebaseUser> = MutableLiveData<FirebaseUser>()
    private var isUserEmailVerified: MutableLiveData<Boolean> = MutableLiveData<Boolean>()
    private var isUserDisconnect: MutableLiveData<Boolean> = MutableLiveData<Boolean>()
    private var firebaseAuth: FirebaseAuth = FirebaseAuth.getInstance()

    fun registerUser(name: String, email: String, password: String) {
        firebaseAuth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    FirebaseDatabase.getInstance()
                        .getReference(FIREBASE_DATABASE_PATH_USER)
                        .child(FirebaseAuth.getInstance().currentUser!!.uid)
                        .setValue(User(0, name, email, password))
                        .addOnCompleteListener {
                            userMutableLiveData.postValue(firebaseAuth.currentUser)
                        }.addOnFailureListener { onError ->
                            Toast.makeText(application.applicationContext,
                                onError.message,
                                Toast.LENGTH_SHORT).show()
                        }
                } else {
                    Toast.makeText(application.applicationContext,
                        it.exception?.message,
                        Toast.LENGTH_SHORT).show()
                }
            }
    }

    fun loginUser(email: String, password: String) {
        firebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener {
            if (it.isSuccessful) {
                val currentUser = FirebaseAuth.getInstance().currentUser

                if (currentUser!!.isEmailVerified) {
                    isUserEmailVerified.postValue(true)
                } else {
                    isUserEmailVerified.postValue(false)
                    currentUser.sendEmailVerification()
                    Toast.makeText(application.applicationContext,
                        R.string.toast_user_email_not_verified,
                        Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(application.applicationContext,
                    it.exception?.message,
                    Toast.LENGTH_SHORT).show()
            }
        }
    }

    fun logoutUser() {
        try {
            firebaseAuth.signOut()
            isUserDisconnect.postValue(true)
        } catch (e: Exception) {
            isUserDisconnect.postValue(false)
            Toast.makeText(application.applicationContext, e.message, Toast.LENGTH_SHORT).show()
        }
    }

    fun getUserMutableLiveData(): MutableLiveData<FirebaseUser> {
        return userMutableLiveData
    }

    fun getIsUserEmailVerified(): MutableLiveData<Boolean> {
        return isUserEmailVerified
    }

    fun getIsUserDisconnect(): MutableLiveData<Boolean> {
        return isUserDisconnect
    }

}