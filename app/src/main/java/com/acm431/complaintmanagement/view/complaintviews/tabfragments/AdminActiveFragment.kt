package com.acm431.complaintmanagement.view.complaintviews.tabfragments

import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.acm431.complaintmanagement.R
import com.acm431.complaintmanagement.adapter.NotificationsAdapter
import com.acm431.complaintmanagement.viewmodel.AdminViewModel
import com.acm431.complaintmanagement.viewmodel.AuthViewModel
import com.acm431.complaintmanagement.viewmodel.ProfileViewModel
import kotlinx.android.synthetic.main.fragment_admin_active.*
import kotlinx.android.synthetic.main.fragment_admin_solved.*
import kotlinx.android.synthetic.main.fragment_profile.*
import kotlinx.android.synthetic.main.notifications_row.*

class AdminActiveFragment : Fragment(R.layout.fragment_admin_active) {

    private var recyclerA = NotificationsAdapter()
    private lateinit var viewModel: AdminViewModel

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this)[AdminViewModel::class.java]

    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        rv_active_complaints_notifications.layoutManager = LinearLayoutManager(requireContext())
        rv_active_complaints_notifications.setHasFixedSize(true)
        rv_active_complaints_notifications.adapter = recyclerA

        observeLiveData()
    }

    private fun observeLiveData() {

        viewModel.complaintList.observe(this.viewLifecycleOwner, Observer { myList ->
            myList?.let {
                tv_no_problems_msg_notifications_active.visibility = View.GONE
                rv_active_complaints_notifications.visibility = View.VISIBLE
                recyclerA.complaints = myList
            }

            if (myList == null) {
                tv_no_problems_msg_notifications_active.visibility = View.VISIBLE
                rv_active_complaints_notifications.visibility = View.GONE
            }
        })
    }
}