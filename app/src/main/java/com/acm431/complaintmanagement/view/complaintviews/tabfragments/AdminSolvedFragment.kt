package com.acm431.complaintmanagement.view.complaintviews.tabfragments

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.acm431.complaintmanagement.R
import com.acm431.complaintmanagement.adapter.NotificationsAdapter
import com.acm431.complaintmanagement.model.Complaint
import kotlinx.android.synthetic.main.fragment_admin_active.*
import kotlinx.android.synthetic.main.fragment_admin_solved.*
import java.time.LocalDateTime

class AdminSolvedFragment : Fragment(R.layout.fragment_admin_solved) {

    private lateinit var complaintList: ArrayList<Complaint>

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //TODO: GET COMPLAINTS FROM FIREBASE

        //Example Complaints (Dummy Data)
        val exampleComplaint = Complaint("a","Kırık Yol","a","Kadıköy/Atatürk Mahallesi",
            "Ekipler Yönlendirildi","Acil","John Doe")
        val exampleComplaint2 = Complaint("a","Kırık Yol","a","Kadıköy/Atatürk Mahallesi",
            "Ekipler Yönlendirildi","Acil","John Doe")
        val exampleComplaint3 = Complaint("a","Kırık Yol","a","Kadıköy/Atatürk Mahallesi",
            "Ekipler Yönlendirildi","Acil","John Doe")
        val exampleComplaint4 = Complaint("a","Kırık Yol","a","Kadıköy/Atatürk Mahallesi",
            "Ekipler Yönlendirildi","Acil","John Doe")
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
        return inflater.inflate(R.layout.fragment_admin_solved, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if(complaintList.size>0){
            tv_no_problems_msg_notifications_solved.visibility = View.GONE
            rv_solved_complaints_notifications.visibility = View.VISIBLE
            rv_solved_complaints_notifications.layoutManager = LinearLayoutManager(requireContext())
            rv_solved_complaints_notifications.setHasFixedSize(true)
            val solvedNotificationsComplaintsAdapter = NotificationsAdapter(requireContext(),complaintList)
            rv_solved_complaints_notifications.adapter = solvedNotificationsComplaintsAdapter
        }
        else{
            tv_no_problems_msg_notifications_active.visibility = View.VISIBLE
            rv_solved_complaints_notifications.visibility = View.GONE
        }


    }
}