package com.example.marsapp

import android.app.Application
import android.os.Bundle
import android.util.Log
import android.util.Patterns
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.Navigation
import com.example.marsapp.databinding.FragmentSingUpBinding
import com.google.firebase.auth.FirebaseAuth
import androidx.annotation.NonNull
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.get
import com.example.marsapp.data.local.DatabaseBuilder
import com.example.marsapp.data.local.DatabaseHelperImpl
import com.example.marsapp.data.local.entity.DatabaseHelper
import com.example.marsapp.data.local.entity.User
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.play.core.internal.t
import com.google.firebase.database.FirebaseDatabase


class SignUpFragment : Fragment() {

    private lateinit var viewModel: SignUpFragmentViewModel
    private lateinit var binding: FragmentSingUpBinding
    private var mAuth: FirebaseAuth? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        val view = inflater.inflate(R.layout.fragment_sing_up, container, false)
        binding = FragmentSingUpBinding.bind(view)

        initComponents()
        initFirebase()
        nameFocusListener()
        emailFocusListener()
        passwordFocusListener()
        confirmPasswordListener()
        initObservers()

        val dbHelper = DatabaseHelperImpl(DatabaseBuilder.getIntance(requireContext()))

        val viewModelProvider = ViewModelFactory(dbHelper)
        viewModel = ViewModelProvider(requireActivity(),
            viewModelProvider).get(SignUpFragmentViewModel::class.java)

        return binding.root
    }

    private fun initObservers() {

    }


    private fun nameFocusListener() {
        binding.textInputName.editText?.setOnFocusChangeListener { _, focused ->
            if (!focused) {
                binding.textInputName.helperText = validName()
            }
        }
    }

    private fun validName(): CharSequence {
        val userNameText = binding.textInputName.editText?.text.toString()

        if (userNameText.isEmpty()) {
            return getString(R.string.invalid_name)
        }

        return " "
    }

    private fun confirmPasswordListener() {
        binding.textInputConfirmPassword.editText?.setOnFocusChangeListener { _, focused ->
            if (!focused) {
                binding.textInputConfirmPassword.helperText = validConfirmPassword()
            }
        }
    }

    private fun validConfirmPassword(): CharSequence {
        if (binding.textInputConfirmPassword.editText?.text.toString() != binding.textInputPassword.editText?.text.toString()) {
            return getString(R.string.invalid_confirm_password)
        }
        return " "
    }

    private fun passwordFocusListener() {
        binding.textInputPassword.editText?.setOnFocusChangeListener { _, focused ->
            if (!focused) {
                binding.textInputPassword.helperText = validPassword()
            }
        }
    }

    private fun validPassword(): CharSequence {
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

        return " "
    }

    private fun emailFocusListener() {
        binding.textInputEmail.editText?.setOnFocusChangeListener { _, focused ->
            if (!focused) {
                binding.textInputEmail.helperText = validEmail()
            }
        }
    }

    private fun validEmail(): CharSequence {
        val userEmailText = binding.textInputEmail.editText?.text.toString()
        if (!Patterns.EMAIL_ADDRESS.matcher(userEmailText).matches()) {
            return getString(R.string.invalid_email)
        }
        return " "
    }

    private fun initFirebase() {
        mAuth = FirebaseAuth.getInstance()
    }

    private fun initComponents() {
        binding.buttonGoLogin.setOnClickListener {
            Navigation.findNavController(binding.root)
                .navigate(R.id.signUpFragment_to_loginFragment)
        }

        binding.textGoLogin.setOnClickListener {
            Navigation.findNavController(binding.root)
                .navigate(R.id.signUpFragment_to_loginFragment)
        }

        binding.buttonSignin.setOnClickListener {
            checkInputTexts()
        }

    }

    private fun checkInputTexts() {

        if (
            binding.textInputEmail.helperText?.length == 1 &&
            binding.textInputEmail.helperText?.length == 1 &&
            binding.textInputPassword.helperText?.length == 1 &&
            binding.textInputConfirmPassword.helperText?.length == 1
        ) {
            val name = binding.textInputName.editText?.text.toString()
            val email = binding.textInputEmail.editText?.text.toString()
            val password = binding.textInputPassword.editText?.text.toString()

            viewModel.createUser(name, email, password)

            mAuth?.createUserWithEmailAndPassword(email, password)
                ?.addOnCompleteListener(OnCompleteListener {
                    if (it.isSuccessful) {

                        FirebaseDatabase.getInstance()
                            .getReference("users")
                            .child(FirebaseAuth.getInstance().currentUser!!.uid)
                            .setValue(User(id, name, email, password))
                            .addOnCompleteListener(OnCompleteListener { data ->
                                if (data.isSuccessful) {
                                    Toast.makeText(context, "Success", Toast.LENGTH_SHORT).show()
                                } else {
                                    Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show()
                                }
                            })


                        Navigation.findNavController(binding.root)
                            .navigate(R.id.signUpFragment_to_loginFragment)
                    } else {
                        viewModel.getUsers().observe(viewLifecycleOwner, Observer {
                            if (it.data!= null){
                                Toast.makeText(context, it.data.toString(), Toast.LENGTH_SHORT).show()
                            }else{
                                Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show()
                            }
                        })
                        Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show()
                    }
                })

        } else {

            binding.textInputName.helperText = validName()
            binding.textInputEmail.helperText = validEmail()
            binding.textInputPassword.helperText = validPassword()
            binding.textInputConfirmPassword.helperText = validConfirmPassword()

            nameFocusListener()
            emailFocusListener()
            passwordFocusListener()
            confirmPasswordListener()
        }
    }

}