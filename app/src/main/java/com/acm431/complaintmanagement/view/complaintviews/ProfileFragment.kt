package com.acm431.complaintmanagement.view.complaintviews

import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.acm431.complaintmanagement.R
import com.acm431.complaintmanagement.adapter.ProfileComplaintsAdapter
import com.acm431.complaintmanagement.database.GlobalValues
import com.acm431.complaintmanagement.model.User
import com.acm431.complaintmanagement.viewmodel.ProfileViewModel
import kotlinx.android.synthetic.main.fragment_profile.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ProfileFragment : Fragment(R.layout.fragment_profile) {

    private lateinit var viewModel: ProfileViewModel
    private var recyclerA = ProfileComplaintsAdapter()


    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this)[ProfileViewModel::class.java]

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        rv_profile_complaints.layoutManager = LinearLayoutManager(requireContext())
        rv_profile_complaints.adapter = recyclerA
        rv_profile_complaints.setHasFixedSize(true)
        GlobalValues.stringValue.observe(viewLifecycleOwner, Observer {
            tv_name_surname_profile.text = it
        })
        observeLiveData()

    }

    private fun observeLiveData() {

        viewModel.list.observe(this.viewLifecycleOwner, Observer { myList ->
            myList?.let {
                tv_no_problems_msg_profile.visibility = View.GONE
                fl_rv_complaint_holder_profile.visibility = View.VISIBLE
                recyclerA.complaints = myList
            }

            if (myList == null) {
                tv_no_problems_msg_profile.visibility = View.VISIBLE
                fl_rv_complaint_holder_profile.visibility = View.GONE
            }
        })
    }
}