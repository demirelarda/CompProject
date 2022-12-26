package com.acm431.complaintmanagement.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.acm431.complaintmanagement.database.GlobalValues
import com.acm431.complaintmanagement.model.Complaint
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class ProfileViewModel : ViewModel() {

    private val database = FirebaseFirestore.getInstance()
    private val auth = FirebaseAuth.getInstance()
    private val collectionRef = database
        .collection("users")
        .document(auth.currentUser!!.email.toString())
        .collection("complaints")

    val list = MutableLiveData<ArrayList<Complaint>>()
    var currentUser = ""

    init {
        retrieveData()
        getUser()
    }
    private fun retrieveData() {

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

    fun getUser()  {
        database
            .collection("users")
            .document(auth.currentUser!!.email.toString()).get().addOnSuccessListener {
                if (it != null){
                    val value = it.getString("username").toString()
                    GlobalValues.userName.value = value
                    println(GlobalValues.userName)
                }
            }
    }

    fun logout() {
        if (auth.currentUser != null)
            auth.signOut()
        else
            throw Exception("You haven't logged in !")
    }

}