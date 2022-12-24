package com.acm431.complaintmanagement.view.authviews

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.acm431.complaintmanagement.BaseFragment
import com.acm431.complaintmanagement.R
import com.acm431.complaintmanagement.model.User
import com.acm431.complaintmanagement.viewmodel.AuthViewModel
import kotlinx.android.synthetic.main.fragment_signup.*

class SignUpFragment :BaseFragment() {
    private lateinit var viewModel : AuthViewModel

    fun makeShortTost(message : String) {
        Toast.makeText(
            this.context,
            message,
            Toast.LENGTH_SHORT
        ).show()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = ViewModelProvider(this).get(AuthViewModel::class.java)

    }

    override fun onResume() {
        super.onResume()

        tv_already_button.setOnClickListener {
            val action = SignUpFragmentDirections.actionSignUpFragmentToLogInFragment()
            Navigation.findNavController(it).navigate(action)
        }

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        register_button.setOnClickListener {
            val username = et_username.text.toString()
            val email = et_email.text.toString()
            val password = et_password.text.toString()
            val passAgain = et_password_again.text.toString()
            val citizenID = et_citizen_id.text.toString()
            val user = User(
                username = username,
                email = email,
                password = password,
                identityNumber = citizenID
            )

            if(!(cb_terms_and_condition.isChecked && username.isEmpty() && email.isEmpty() && password.isEmpty() && citizenID.isEmpty()))
                viewModel.register(user)
            else if (passAgain != password)
                makeShortTost("Girdiğiniz şifreler uyuşmuyor")
            else
                makeShortTost("Lütfen boş bırakmadığınız alan olduğundan emin olun ve koşulları kabul edin")// her zaman bu satıra uğruyor ????

        }

        viewModel.registerLoading.observe(viewLifecycleOwner, Observer { loading->
            if(loading){
                showProgressBar(getString(R.string.please_wait))
            }
            else{
                hideProgressBar()
            }

        })

        viewModel.registerError.observe(viewLifecycleOwner, Observer { error->
            if(error){
                showErrorSnackBar(getString(R.string.an_error_occured),true)
            }
        })

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_signup, container, false)
    }

}