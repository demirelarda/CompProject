package com.acm431.complaintmanagement.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.acm431.complaintmanagement.R
import com.acm431.complaintmanagement.model.Complaint
import com.acm431.complaintmanagement.view.complaintviews.tabfragments.AdminActiveFragment
import com.acm431.complaintmanagement.view.complaintviews.tabfragments.AdminSolvedFragment
import kotlinx.android.synthetic.main.notifications_row.view.*



class NotificationsAdapter(val fragment: Fragment,val isAdmin: Boolean): RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    class NotificationsViewHolder(view: View) : RecyclerView.ViewHolder(view)

    private val diffUtil= object: DiffUtil.ItemCallback<Complaint>(){
        override fun areItemsTheSame(oldItem: Complaint, newItem: Complaint): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: Complaint, newItem: Complaint): Boolean {
            return oldItem== newItem
        }
    }

    private val listDifferRecycler = AsyncListDiffer(this,diffUtil)

    var complaints: List<Complaint>
        get() = listDifferRecycler.currentList
        set(value) = listDifferRecycler.submitList(value)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return NotificationsViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.notifications_row, parent, false)
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val model = complaints[position]
        if(isAdmin){
            holder.itemView.btn_solve.visibility = View.VISIBLE
        }
        else{
            holder.itemView.btn_solve.visibility = View.GONE
        }
        holder.itemView.tv_description_notification_row.text = model.content
        holder.itemView.tv_address_notifications_row.text = model.location
        holder.itemView.tv_user_name_surname_notifications_row.text = model.userName
        holder.itemView.iv_user_pp_notifications_row.setImageResource(R.drawable.user_image_placeholder)
        holder.itemView.iv_notifications_complaint_image_row.setImageResource(R.drawable.ic_baseline_photo_camera_24)
        holder.itemView.btn_solve.setOnClickListener {
            when(fragment){
                is AdminActiveFragment ->{
                    fragment.editStatus(model.complaintID)
                }
            }
        }
    }

    override fun getItemCount(): Int {
        return complaints.size
    }



}