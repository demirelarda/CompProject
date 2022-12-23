package com.acm431.complaintmanagement.view.complaintviews

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.acm431.complaintmanagement.R
import com.acm431.complaintmanagement.adapter.ProfileComplaintsAdapter
import com.acm431.complaintmanagement.model.Complaint
import com.acm431.complaintmanagement.viewmodel.AddComplaintViewModel
import com.acm431.complaintmanagement.viewmodel.ProfileViewModel
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.fragment_profile.*
import java.time.LocalDateTime

class ProfileFragment: Fragment(R.layout.fragment_profile) {
    private lateinit var viewModel: ProfileViewModel
    private var recyclerA = ProfileComplaintsAdapter()
    private lateinit var complaintList: ArrayList<Complaint>
    val auth = FirebaseAuth.getInstance()

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this)[ProfileViewModel::class.java]

     /*   //Example Complaints (Dummy Data)
        val exampleComplaint = Complaint("a","Kırık Yol","a","Kadıköy/Atatürk Mahallesi",
            LocalDateTime.now(),"Ekipler Yönlendirildi","Acil")
        val exampleComplaint2 = Complaint("a","Kırık Yol","a","Kadıköy/Atatürk Mahallesi",
            LocalDateTime.now(),"Ekipler Yönlendirildi","Acil")
        val exampleComplaint3 = Complaint("a","Kırık Yol","a","Kadıköy/Atatürk Mahallesi",
            LocalDateTime.now(),"Ekipler Yönlendirildi","Acil")
        val exampleComplaint4 = Complaint("a","Kırık Yol","a","Kadıköy/Atatürk Mahallesi",
            LocalDateTime.now(),"Ekipler Yönlendirildi","Acil")
        complaintList = ArrayList()
        complaintList.add(exampleComplaint)
        complaintList.add(exampleComplaint2)
        complaintList.add(exampleComplaint3)
        complaintList.add(exampleComplaint4)*/

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        observeLiveData()

        return inflater.inflate(R.layout.fragment_profile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        rv_profile_complaints.layoutManager = LinearLayoutManager(requireContext())
        rv_profile_complaints.adapter = recyclerA
        rv_profile_complaints.setHasFixedSize(true)
        observeLiveData()
        println(auth.currentUser!!.email)

    }

    fun observeLiveData() {

        viewModel.list.observe(this.viewLifecycleOwner, Observer { myList ->
            myList?.let {
                tv_no_problems_msg_profile.visibility = View.GONE
                fl_rv_complaint_holder_profile.visibility = View.VISIBLE
                recyclerA.analyzes= myList
            }

            if (myList == null) {
                tv_no_problems_msg_profile.visibility = View.VISIBLE
                fl_rv_complaint_holder_profile.visibility = View.GONE
            }
        })
    }
}