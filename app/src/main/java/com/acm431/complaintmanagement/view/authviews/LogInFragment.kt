package com.acm431.complaintmanagement.view.authviews

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.acm431.complaintmanagement.R
import com.acm431.complaintmanagement.viewmodel.AuthViewModel
import kotlinx.android.synthetic.main.fragment_login.*

class LogInFragment : Fragment(R.layout.fragment_login) {
    private lateinit var viewModel: AuthViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = ViewModelProvider(this).get(AuthViewModel::class.java)
    }

    override fun onResume() {
        super.onResume()

        register_button_login.setOnClickListener {
            val action = LogInFragmentDirections.actionLogInFragmentToSignUpFragment()
            Navigation.findNavController(it).navigate(action)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        login_button.setOnClickListener {

            val email = et_email.text.toString()
            val password = et_password.text.toString()

            if (email.isNotEmpty() && password.isNotEmpty())
                viewModel.login(email, password)
            else
                Toast.makeText(
                    this.context,
                    "Lütfen email ve password alanlarını boş bırakmayın",
                    Toast.LENGTH_SHORT
                ).show()

            viewModel.loginResult.observe(viewLifecycleOwner, Observer { result ->
                if (result)
                    findNavController().navigate(R.id.profileFragment)
            })
        }
    }
}