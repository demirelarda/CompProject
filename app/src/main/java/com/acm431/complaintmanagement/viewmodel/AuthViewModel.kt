package com.acm431.complaintmanagement.viewmodel

import android.content.ContentValues.TAG
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class AuthViewModel : ViewModel() {

    var loginResult = MutableLiveData<Boolean>()
    val auth = FirebaseAuth.getInstance()
    val db = FirebaseFirestore.getInstance()
    val loginLoading = MutableLiveData<Boolean>()
    val registerLoading = MutableLiveData<Boolean>()
    val registerError = MutableLiveData<Boolean>()
    val loginError = MutableLiveData<Boolean>()
    val registrationSucces = MutableLiveData<Boolean>()

    fun login(email: String, password: String) {

        loginLoading.value = true
        auth.signInWithEmailAndPassword(email, password).addOnSuccessListener { result ->
            loginLoading.value = false
            loginResult.value = true
        }
            .addOnFailureListener {
                loginError.value = true
                loginLoading.value = false
                loginResult.value = false
            }
    }

    fun register(user: com.acm431.complaintmanagement.model.User) {
        registrationSucces.value = false
        registerLoading.value = true
        val auth = FirebaseAuth.getInstance()
        auth.createUserWithEmailAndPassword(user.email, user.password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    registrationSucces.value = true
                    registerLoading.value = false

                    db.collection("users").document(user.email).set(user)
                        .addOnSuccessListener { documentReference ->
                            Log.d(TAG, "DocumentSnapshot added with ID: ${user.email}")
                        }
                        .addOnFailureListener { e ->
                            Log.w(TAG, "Error adding document", e)
                        }
                }
            }
            .addOnFailureListener {
                registerLoading.value = false
                registerError.value = true
            }
    }
}