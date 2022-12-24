package com.acm431.complaintmanagement.viewmodel

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.acm431.complaintmanagement.model.Complaint
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import kotlin.Exception

class ProfileViewModel : ViewModel() {

    val database = FirebaseFirestore.getInstance()
    val auth = FirebaseAuth.getInstance()
    val collectionRef = database
        .collection("users")
        .document(auth.currentUser!!.email.toString())
        .collection("complaints")

    val list = MutableLiveData<ArrayList<Complaint>>()

    init {
        retrieveData()
    }
    fun retrieveData() {

        collectionRef.addSnapshotListener { value, error ->
            try {
                if (value != null && !value.isEmpty) {

                    val allAnalysis = ArrayList<Complaint>()
                    val documents = value.documents
                    println("query snapshot null değil")

                    documents.forEach {
                        val analyze = it.toObject(Complaint::class.java)
                        if (analyze != null) {
                            println("documents snapshot null değil")
                            allAnalysis.add(analyze)
                        }
                    }
                    list.value = allAnalysis
                } else if (error != null) {
                    println(error.localizedMessage)
                }
            } catch (e: Exception) {
                println(e.localizedMessage)
            }
        }
    }
}