package com.acm431.complaintmanagement.view.complaintviews


import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.acm431.complaintmanagement.R
import com.acm431.complaintmanagement.database.GlobalValues
import com.acm431.complaintmanagement.model.Complaint
import com.acm431.complaintmanagement.viewmodel.AddComplaintViewModel

class AddComplaintFragment : Fragment(R.layout.fragment_add_complaint) {
    private lateinit var viewModel: AddComplaintViewModel


    fun makeShortTost(message : String) {
        Toast.makeText(
            this.context,
            message,
            Toast.LENGTH_SHORT
        ).show()
    }

    private fun checkPermission(permission: String, requestCode: Int) {
        if (ContextCompat.checkSelfPermission(this.requireActivity(), permission) == PackageManager.PERMISSION_DENIED) {
            ActivityCompat.requestPermissions(this.requireActivity(), arrayOf(permission), requestCode)
        }
    }


    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        checkPermission(Manifest.permission.CAMERA,100)
        checkPermission(Manifest.permission.ACCESS_FINE_LOCATION,101)

        viewModel = ViewModelProvider(this)[AddComplaintViewModel::class.java]


        val exampleComplaint = Complaint(
            userName = GlobalValues.userName.value.toString(),
            complaintID = "a",
            content = "rehwerh",
            imagePath = "a",
            location = "İçerenköy/Fındıklı Mahallesi",
            status = "Ekipler Yönlendirildi",
            urgency = "Acelesi yok"
        )

        viewModel.save(exampleComplaint)

    }

    override fun onRequestPermissionsResult(requestCode: Int,
                                            permissions: Array<String>,
                                            grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == 100) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                makeShortTost("Camera Permission Granted")
            } else {
                makeShortTost("Camera Permission Denied")
            }
        } else if (requestCode == 101) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                makeShortTost("Location Permission Granted")
            } else {
                makeShortTost("Location Permission Denied")
            }
        }
    }
}