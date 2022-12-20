package com.acm431.complaintmanagement.model

import java.time.LocalDate
import java.time.LocalDateTime

data class Complaint(
    val complaintID: String,
    val content : String,
    val imagePath: String,
    val location : String,
    val date : LocalDateTime = LocalDateTime.now(),
)
