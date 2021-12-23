package com.example.marsapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import com.example.marsapp.databinding.FragmentSingUpBinding

class SignUpFragment : Fragment() {

    private lateinit var binding: FragmentSingUpBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_sing_up, container, false)
        binding = FragmentSingUpBinding.bind(view)

        binding.btnSignup.setOnClickListener {
            Navigation.findNavController(binding.root).navigate(R.id.signUpFragment_to_loginFragment)
        }

        return binding.root
    }

}