package com.acm431.complaintmanagement.view.complaintviews

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.acm431.complaintmanagement.R
import com.acm431.complaintmanagement.adapter.ProfileComplaintsAdapter
import com.acm431.complaintmanagement.model.Complaint
import kotlinx.android.synthetic.main.fragment_profile.*
import java.time.LocalDateTime

class ProfileFragment: Fragment(R.layout.fragment_profile) {

    private lateinit var complaintList: ArrayList<Complaint>

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //TODO: GET COMPLAINTS FROM FIREBASE

        //Example Complaints (Dummy Data)
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
        complaintList.add(exampleComplaint4)

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if(complaintList.size>0){
            tv_no_problems_msg_profile.visibility = View.GONE
            fl_rv_complaint_holder_profile.visibility = View.VISIBLE
            rv_profile_complaints.layoutManager = LinearLayoutManager(requireContext())
            rv_profile_complaints.setHasFixedSize(true)
            val profileComplaintsAdapter = ProfileComplaintsAdapter(requireContext(),complaintList)
            rv_profile_complaints.adapter = profileComplaintsAdapter
        }
        else{
            tv_no_problems_msg_profile.visibility = View.VISIBLE
            fl_rv_complaint_holder_profile.visibility = View.GONE
        }


    }


}