package com.example.marsapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import com.example.marsapp.databinding.FragmentLoginBinding


class LoginFragment : Fragment() {

    private lateinit var binding: FragmentLoginBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.fragment_login, container, false)
        binding = FragmentLoginBinding.bind(view)

        initComponents()

        return binding.root
    }

    private fun initComponents() {

        binding.textGoSignup?.setOnClickListener {
            Navigation.findNavController(binding.root)
                .navigate(R.id.loginFragment_to_signUpFragment)
        }

    }

}