package com.acm431.complaintmanagement.view.complaintviews

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.acm431.complaintmanagement.R

class ProfileFragment: Fragment(R.layout.fragment_profile) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        println("profile created")
    }
}