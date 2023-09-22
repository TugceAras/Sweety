package com.tugcearas.sweety.ui.success

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.tugcearas.sweety.R
import com.tugcearas.sweety.databinding.FragmentSuccessOrderScreenBinding
import com.tugcearas.sweety.util.extensions.click
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SuccessOrderScreen : Fragment() {

    private lateinit var binding: FragmentSuccessOrderScreenBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSuccessOrderScreenBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnBackToHome.click {
            val action = SuccessOrderScreenDirections.actionSuccessOrderScreenToHomeScreen()
            findNavController().navigate(action)
        }
    }
}