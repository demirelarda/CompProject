package com.acm431.complaintmanagement.view.complaintviews


import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity.RESULT_OK
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Geocoder
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Looper
import android.provider.MediaStore
import android.view.View
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.acm431.complaintmanagement.LoadGlide
import com.acm431.complaintmanagement.R
import com.acm431.complaintmanagement.database.GlobalValues
import com.acm431.complaintmanagement.model.Complaint
import com.acm431.complaintmanagement.viewmodel.AddComplaintViewModel
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_add_complaint.*
import java.util.*

class AddComplaintFragment : Fragment(R.layout.fragment_add_complaint) {
    private lateinit var viewModel: AddComplaintViewModel
    private lateinit var activityResultLauncher : ActivityResultLauncher<Intent>
    private lateinit var permissionLauncher: ActivityResultLauncher<String>
    private var selectedPicture: Uri? = null
    private val locationManager  by lazy {
        requireActivity().getSystemService(Context.LOCATION_SERVICE) as LocationManager?
    }



    private var addressString : String? = null


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


    override fun onResume() {
        super.onResume()
        iv_complaint_image.setOnClickListener {
            imageViewClicked()
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        checkPermission(Manifest.permission.CAMERA,100)
        checkPermission(Manifest.permission.ACCESS_FINE_LOCATION,101)
        registerLauncher()
        viewModel = ViewModelProvider(this)[AddComplaintViewModel::class.java]

        if (ContextCompat.checkSelfPermission(this.requireActivity(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            locationManager!!.requestSingleUpdate(LocationManager.GPS_PROVIDER, object : LocationListener {
                override fun onLocationChanged(location: Location) {

                    val geocoder = Geocoder(context, Locale.getDefault())
                    val addresses = geocoder.getFromLocation(location!!.latitude, location.longitude, 1)
                    val address = addresses[0]
                    addressString = " ${address.locality}"

                    println(addressString)
                }

                override fun onStatusChanged(provider: String?, status: Int, extras: Bundle?) {}
                override fun onProviderEnabled(provider: String) {
                    println("gps enabled")
                }
                override fun onProviderDisabled(provider: String) {
                    println("gps disabled")

                }
            }, Looper.getMainLooper())
        }
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        btn_make_complaint.setOnClickListener {
            val complaintContent = et_complaint.text.toString()
            val complaintLocation :String? = addressString


            val complaint = Complaint(
                userName = GlobalValues.userName.value.toString(),
                content = complaintContent,
                location = complaintLocation ?: "İçerenköy/Fındıklı Mahallesi",
                status = "Ekipler Yönlendirildi",
                urgency = "Acelesi yok"
            )

            viewModel.saveImageToStorage(selectedPicture!!,complaint)
        }
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

    private fun imageViewClicked(){
        if(ContextCompat.checkSelfPermission(requireContext(),
                Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
            if(ActivityCompat.shouldShowRequestPermissionRationale(requireActivity(), Manifest.permission.READ_EXTERNAL_STORAGE)){
                Snackbar.make(requireView(),getString(R.string.permission_needed_for_gallery), Snackbar.LENGTH_INDEFINITE).setAction(getString(R.string.give_permission)){
                    //request permission
                    permissionLauncher.launch(Manifest.permission.READ_EXTERNAL_STORAGE)
                }.show()
            }else{
                //request permission
                permissionLauncher.launch(Manifest.permission.READ_EXTERNAL_STORAGE)
            }
        }else{
            val intentToGallery = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            //start activity for result
            activityResultLauncher.launch(intentToGallery)

        }
    }

    private fun registerLauncher(){
        activityResultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){ result->
            if(result.resultCode == RESULT_OK){
                val intentFromResult = result.data
                if(intentFromResult != null){
                    intentFromResult.data
                    selectedPicture = intentFromResult.data
                    selectedPicture.let {
                        LoadGlide(requireContext()).loadComplaintImage(selectedPicture!!,iv_complaint_image)
                    }
                }
            }
        }

        permissionLauncher = registerForActivityResult(ActivityResultContracts.RequestPermission()){ result ->
            if(result){
                //permission granted
                val intentToGallery = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
                activityResultLauncher.launch(intentToGallery)
            }else{
                //permission denied
                Toast.makeText(requireActivity(),getString(R.string.permission_needed_for_gallery), Toast.LENGTH_LONG).show()
            }
        }
    }
}

