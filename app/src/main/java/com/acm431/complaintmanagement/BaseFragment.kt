package com.acm431.complaintmanagement

import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.progress_bar.*

open class BaseFragment : Fragment() {

    private lateinit var progressDialog : Dialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_base, container, false)
    }

    fun showProgressBar(text: String) {
        progressDialog = Dialog(requireActivity())
        progressDialog.setContentView(R.layout.progress_bar)
        progressDialog.tv_progress_text.text = text
        progressDialog.setCancelable(false)
        progressDialog.setCanceledOnTouchOutside(false)
        progressDialog.show()
    }

    fun hideProgressBar() {
        progressDialog.dismiss()
    }


}