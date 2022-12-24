package com.acm431.complaintmanagement.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.acm431.complaintmanagement.R
import com.acm431.complaintmanagement.model.Complaint
import kotlinx.android.synthetic.main.profile_complaints_row.view.*

class ProfileComplaintsAdapter(): RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    class ProfileComplaintsViewHolder(view: View) : RecyclerView.ViewHolder(view)

    private val diffUtil= object: DiffUtil.ItemCallback<Complaint>(){
        override fun areItemsTheSame(oldItem: Complaint, newItem: Complaint): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: Complaint, newItem: Complaint): Boolean {
            return oldItem== newItem
        }
    }

    private val listDifferRecycler = AsyncListDiffer(this,diffUtil)

    var analyzes: List<Complaint>
        get() = listDifferRecycler.currentList
        set(value) = listDifferRecycler.submitList(value)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return ProfileComplaintsViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.profile_complaints_row, parent, false)
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val model = analyzes[position]
        holder.itemView.tv_address_date_profile_complaint_row.background.alpha = 80
        holder.itemView.tv_complaint_name_profile_row.text = model.content
        holder.itemView.tv_urgency_profile_row.text = model.urgency
        holder.itemView.tv_row_complaint_status.text = model.status //TODO : Change the background color according to the status
        //holder.itemView.tv_address_date_profile_complaint_row.text = model.location+" - "+model.date
        holder.itemView.iv_complaint_image_small_profile_row.setImageResource(R.drawable.ic_baseline_photo_camera_24) //TODO: USE AN IMAGE LOADER LIBRARY (GLIDE)
    }

    override fun getItemCount(): Int {
        return analyzes.size
    }
}