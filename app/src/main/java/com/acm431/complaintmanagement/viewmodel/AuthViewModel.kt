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

    fun login(email: String, password: String) {

        try {
            auth.signInWithEmailAndPassword(email, password).addOnCompleteListener { result ->
                loginResult.value = result.isSuccessful
            }
        } catch (e: java.lang.Exception) {
            throw java.lang.Exception(e.message.toString())
        }
    }

    fun register(user: com.acm431.complaintmanagement.model.User) {
        val auth = FirebaseAuth.getInstance()
        auth.createUserWithEmailAndPassword(user.email, user.password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {

                  db.collection("users").document(user.email).set(user).addOnSuccessListener { documentReference ->
                            Log.d(TAG, "DocumentSnapshot added with ID: ${user.email}")
                        }
                        .addOnFailureListener { e ->
                            Log.w(TAG, "Error adding document", e)
                        }
                }
            }
    }
}