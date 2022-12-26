package com.acm431.complaintmanagement.viewmodel

import androidx.lifecycle.ViewModel
import com.acm431.complaintmanagement.model.Complaint
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class AddComplaintViewModel : ViewModel() {

    private val database = FirebaseFirestore.getInstance()
    private val auth = FirebaseAuth.getInstance()
    private val collectionRef = database.collection("users")
        .document(auth.currentUser!!.email.toString())
        .collection("complaints")

    fun save(data: Complaint) {

        val newRef = collectionRef.document()     // 👈 generates a new reference with a unique ID

        data.complaintID = newRef.id // 👈 set the ID into your object

        newRef.set(data)    // 👈 writes the data to the new reference
            .addOnFailureListener { exception ->
                throw Exception(exception.localizedMessage)
            }
        database.collection("allComplaints").document(data.complaintID).set(data)
    }
}