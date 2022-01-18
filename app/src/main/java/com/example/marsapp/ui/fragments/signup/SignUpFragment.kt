package com.example.marsapp.ui.fragments.signup

import android.app.Application
import android.os.Bundle
import android.util.Patterns
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.Navigation
import com.example.marsapp.databinding.FragmentSingUpBinding
import androidx.lifecycle.ViewModelProvider
import com.example.marsapp.R
import com.example.marsapp.utils.ViewModelFactory
import com.example.marsapp.data.local.database.DatabaseBuilder
import com.example.marsapp.data.local.database.DatabaseHelperImpl


class SignUpFragment : Fragment() {

    private lateinit var viewModel: SignUpFragmentViewModel
    private lateinit var binding: FragmentSingUpBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        val view = inflater.inflate(R.layout.fragment_sing_up, container, false)
        binding = FragmentSingUpBinding.bind(view)

        initComponents()
        initViewModel()
        nameFocusListener()
        emailFocusListener()
        passwordFocusListener()
        confirmPasswordListener()

        return binding.root
    }

    private fun initViewModel() {
        val dbHelper = DatabaseHelperImpl(DatabaseBuilder.getInstance(requireContext()))

        val viewModelProvider = ViewModelFactory(dbHelper, Application())

        viewModel = ViewModelProvider(requireActivity(),
            viewModelProvider)[SignUpFragmentViewModel::class.java]
    }

    private fun initViewModelObservers() {
        viewModel.getUserMutableLiveData().observe(viewLifecycleOwner, {
            if (it != null) {
                getString(R.string.toast_user_registered_successfully).createToast()
                binding.progressBarSignin.visibility = View.GONE
                Navigation.findNavController(binding.root)
                    .navigate(R.id.signUpFragment_to_loginFragment)
            }
        })
    }

    private fun nameFocusListener() {
        binding.textInputName.editText?.setOnFocusChangeListener { _, focused ->
            if (!focused) {
                binding.textInputName.helperText = validName()
            }
        }
    }

    private fun validName(): CharSequence? {
        val userNameText = binding.textInputName.editText?.text.toString()

        if (userNameText.isEmpty()) {
            return getString(R.string.invalid_name)
        }

        return null
    }

    private fun confirmPasswordListener() {
        binding.textInputConfirmPassword.editText?.setOnFocusChangeListener { _, focused ->
            if (!focused) {
                binding.textInputPassword.helperText = validConfirmPassword()
            }
        }
    }

    private fun validConfirmPassword(): CharSequence? {
        if (binding.textInputConfirmPassword.editText?.text.toString() != binding.textInputPassword.editText?.text.toString()) {
            return getString(R.string.invalid_confirm_password)
        }
        return null
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
        binding.buttonGoLogin.setOnClickListener {
            Navigation.findNavController(binding.root)
                .navigate(R.id.signUpFragment_to_loginFragment)
        }

        binding.textGoLogin.setOnClickListener {
            Navigation.findNavController(binding.root)
                .navigate(R.id.signUpFragment_to_loginFragment)
        }

        binding.buttonSignin.setOnClickListener {
            if (checkInputTexts()) {
                signInWithEmailAndPassword()
            }
        }

    }

    private fun signInWithEmailAndPassword() {

        initViewModelObservers()

        binding.progressBarSignin.visibility = View.VISIBLE

        val name = binding.textInputName.editText?.text.toString()
        val email = binding.textInputEmail.editText?.text.toString()
        val password = binding.textInputPassword.editText?.text.toString()

        viewModel.registerUser(name, email, password)
    }

    private fun checkInputTexts(): Boolean {

        if (binding.textInputName.helperText == null && binding.textInputEmail.helperText == null &&
            binding.textInputPassword.helperText == null
        ) {

            return true
        } else {

            binding.textInputName.helperText = validName()
            binding.textInputEmail.helperText = validEmail()
            binding.textInputPassword.helperText = validPassword()
            binding.textInputConfirmPassword.helperText = validConfirmPassword()

            nameFocusListener()
            emailFocusListener()
            passwordFocusListener()
            confirmPasswordListener()

            return false
        }
    }

    private fun String.createToast() {
        Toast.makeText(context, this, Toast.LENGTH_SHORT).show()
    }

}