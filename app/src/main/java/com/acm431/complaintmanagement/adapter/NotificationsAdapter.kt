package com.acm431.complaintmanagement.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.acm431.complaintmanagement.R
import com.acm431.complaintmanagement.model.Complaint
import kotlinx.android.synthetic.main.notifications_row.view.*



class NotificationsAdapter(private val context: Context, private var list: ArrayList<Complaint>): RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    class NotificationsViewHolder(view: View) : RecyclerView.ViewHolder(view)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return NotificationsViewHolder(
            LayoutInflater.from(context).inflate(R.layout.notifications_row, parent, false)
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val model = list[position]
        holder.itemView.tv_description_notification_row.text = model.content
        holder.itemView.tv_address_notifications_row.text = model.location
        holder.itemView.tv_user_name_surname_notifications_row.text = model.userName
        holder.itemView.iv_user_pp_notifications_row.setImageResource(R.drawable.user_image_placeholder)
        holder.itemView.iv_notifications_complaint_image_row.setImageResource(R.drawable.ic_baseline_photo_camera_24)
    }

    override fun getItemCount(): Int {
        return list.size
    }
}