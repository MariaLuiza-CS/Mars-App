package com.example.marsapp.ui.fragments.user

import android.app.Application
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment
import com.example.marsapp.R
import com.example.marsapp.data.local.database.AppDatabase
import com.example.marsapp.data.local.database.DatabaseBuilder
import com.example.marsapp.data.local.database.DatabaseHelperImpl
import com.example.marsapp.databinding.FragmentUserBinding
import com.example.marsapp.utils.ViewModelFactory


class UserFragment : Fragment() {

    private lateinit var binding: FragmentUserBinding
    private lateinit var viewModel: UserFragmentViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_user, container, false)
        binding = FragmentUserBinding.bind(view)

        initComponents()
        initViewModel()
        return binding.root
    }

    private fun initViewModel() {
        val dbHelper = DatabaseHelperImpl(DatabaseBuilder.getInstance(requireContext()))

        val viewModelProvider = ViewModelFactory(dbHelper, Application())

        viewModel = ViewModelProvider(requireActivity(),
            viewModelProvider)[UserFragmentViewModel::class.java]
    }

    private fun initComponents() {

        initObservers()

        binding.textViewAccount.setOnClickListener {
            viewModel.logoutUser()
        }

    }

    private fun initObservers() {

        viewModel.getIsUserDiconnect().observe(viewLifecycleOwner, {
            if (it) {
                NavHostFragment.findNavController(this).popBackStack()
            }
        })
    }

}