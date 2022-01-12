package com.example.marsapp.ui.fragments.login

import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.Navigation
import com.example.marsapp.R
import com.example.marsapp.databinding.FragmentLoginBinding
import com.example.marsapp.ui.activities.home.HomeActivity
import com.google.firebase.auth.FirebaseAuth


class LoginFragment : Fragment() {

    private lateinit var binding: FragmentLoginBinding
    private var mAuth: FirebaseAuth? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        val view = inflater.inflate(R.layout.fragment_login, container, false)
        binding = FragmentLoginBinding.bind(view)

        initComponents()
        initFirebase()
        emailFocusListener()
        passwordFocusListener()

        return binding.root
    }

    private fun initFirebase() {
        mAuth = FirebaseAuth.getInstance()
    }

    private fun passwordFocusListener() {
        binding.textInputPassword.editText?.setOnFocusChangeListener { _, focused ->
            if (!focused) {
                binding.textInputPassword.helperText = validPassword()
            }
        }
    }

    private fun validPassword(): CharSequence? {
        val userPasswordText = binding.textInputPassword.editText?.text.toString()

        if (userPasswordText.length < 8) {
            return getString(R.string.invalid_password_length)
        }

        if (!userPasswordText.matches(".*[A-Z].*".toRegex())) {
            return getString(R.string.invalid_password_upper_case)
        }

        if (!userPasswordText.matches(".*[a-z].*".toRegex())) {
            return getString(R.string.invalid_password_lower_case)
        }

        if (!userPasswordText.matches(".*[@#\$%^&+=*].*".toRegex())) {
            return getString(R.string.invalid_password_special_character)
        }

        return null
    }

    private fun emailFocusListener() {
        binding.textInputEmail.editText?.setOnFocusChangeListener { _, focused ->
            if (!focused) {
                binding.textInputEmail.helperText = validEmail()
            }
        }
    }

    private fun validEmail(): CharSequence? {
        val userEmailText = binding.textInputEmail.editText?.text.toString()
        if (!Patterns.EMAIL_ADDRESS.matcher(userEmailText).matches()) {
            return getString(R.string.invalid_email)
        }
        return null
    }

    private fun initComponents() {

        binding.textGoSignup?.setOnClickListener {
            Navigation.findNavController(binding.root)
                .navigate(R.id.loginFragment_to_signUpFragment)
        }

        binding.buttonLogin.setOnClickListener {
            if (checkInputs()) {
                loginWithEmail()
            }
        }

    }

    private fun checkInputs(): Boolean {

        return if (
            (binding.textInputEmail.helperText == null && binding.textInputPassword.helperText == " ") ||
            (binding.textInputPassword.helperText == null && binding.textInputEmail.helperText == null)
        ) {
            true
        } else {
            binding.textInputEmail.helperText = validEmail()
            binding.textInputPassword.helperText = validPassword()

            emailFocusListener()
            passwordFocusListener()
            false
        }
    }

    private fun String.createToast() {
        Toast.makeText(context, this, Toast.LENGTH_SHORT).show()
    }

    private fun loginWithEmail() {
        binding.progressBarLogin.visibility = View.VISIBLE
        val email = binding.textInputEmail.editText?.text.toString()
        val password = binding.textInputPassword.editText?.text.toString()

        mAuth?.signInWithEmailAndPassword(email, password)?.addOnCompleteListener {
            if (it.isSuccessful) {
                val currentUser = FirebaseAuth.getInstance().currentUser

                if (currentUser!!.isEmailVerified) {
                    binding.progressBarLogin.visibility = View.GONE
                    startActivity(Intent(context, HomeActivity::class.java))
                } else {
                    currentUser.sendEmailVerification()
                    binding.progressBarLogin.visibility = View.GONE
                    "Please check your email to verify your account!".createToast()
                }
            } else {
                binding.progressBarLogin.visibility = View.GONE
                it.exception?.message?.createToast()
            }
        }

    }

}