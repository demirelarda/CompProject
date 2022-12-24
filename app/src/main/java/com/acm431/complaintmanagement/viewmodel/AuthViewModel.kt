package com.acm431.complaintmanagement.viewmodel

import android.content.ContentValues.TAG
import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment.Companion.findNavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.auth.User
import kotlin.math.log

class AuthViewModel : ViewModel() {

    var loginResult = MutableLiveData<Boolean>()
    val loginLoading = MutableLiveData<Boolean>()
    val registerLoading = MutableLiveData<Boolean>()
    val auth = FirebaseAuth.getInstance()
    val db = FirebaseFirestore.getInstance()

    fun login(email: String, password: String) {
        println("fonksiyona girildi")
        try {
            loginLoading.value = true
            auth.signInWithEmailAndPassword(email, password).addOnCompleteListener { result ->
                loginResult.value = result.isSuccessful
                loginLoading.value = false
                println("logged in")
                println(loginResult.value)
                println(result.isSuccessful)
            }
        }
        catch (e: java.lang.Exception) {
            loginLoading.value = false
            throw java.lang.Exception(e.message.toString())
        }
    }

    fun register(user: com.acm431.complaintmanagement.model.User) {
        registerLoading.value = true
        val auth = FirebaseAuth.getInstance()
        auth.createUserWithEmailAndPassword(user.email, user.password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    db.collection("users")
                        .add(user)
                        .addOnSuccessListener { documentReference ->
                            registerLoading.value = false
                            println("registered")
                            Log.d(TAG, "DocumentSnapshot added with ID: ${documentReference.id}")
                        }
                        .addOnFailureListener { e ->
                            registerLoading.value = false
                            println("cant registered")
                            Log.w(TAG, "Error adding document", e)
                        }
                }
            }
            .addOnFailureListener {
                println("error")
                registerLoading.value = false
            }
    }
}