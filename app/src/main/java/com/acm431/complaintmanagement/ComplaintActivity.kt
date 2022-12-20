package com.acm431.complaintmanagement

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.acm431.complaintmanagement.view.complaintviews.AddComplaintFragment
import com.acm431.complaintmanagement.view.complaintviews.AdminComplaints
import com.acm431.complaintmanagement.view.complaintviews.HomeFragment
import com.acm431.complaintmanagement.view.complaintviews.ProfileFragment
import kotlinx.android.synthetic.main.activity_complaint.*

class ComplaintActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_complaint)

        bottomNavigationView.setOnItemSelectedListener {
            when(it.itemId){
                R.id.home -> replaceFragment(HomeFragment())
                R.id.add_complaint -> replaceFragment(AddComplaintFragment())
                R.id.notifications -> replaceFragment(AdminComplaints())
                R.id.profile -> replaceFragment(ProfileFragment())
                else ->{

                }
            }
            true
        }



    }


    private fun replaceFragment(fragment: Fragment){

        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.frameLayout,fragment)
        fragmentTransaction.commit()

    }




}