package com.acm431.complaintmanagement.model

import com.google.firebase.Timestamp

data class Complaint(
    var complaintID: String = "",
    val content : String= "",
    val imagePath: String= "",
    val location : String= "",
    val date : String = Timestamp.now().toDate().toString(),
    val status: String= "",
    val urgency: String= "",
    val userName: String = "",
    val solved : Int = 0,

)
