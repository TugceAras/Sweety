package com.tugcearas.sweety.ui.home

import android.app.AlertDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.tugcearas.sweety.R
import com.tugcearas.sweety.data.model.Product
import com.tugcearas.sweety.data.model.UserId
import com.tugcearas.sweety.databinding.FragmentHomeScreenBinding
import com.tugcearas.sweety.util.extensions.click
import com.tugcearas.sweety.util.extensions.gone
import com.tugcearas.sweety.util.extensions.snackbar
import com.tugcearas.sweety.util.extensions.visible
import com.tugcearas.sweety.util.resource.Resource
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeScreen : Fragment(), HomeSaleAdapter.ProductListener, HomeAllProductAdapter.ProductAllListener {

    private lateinit var binding: FragmentHomeScreenBinding
    private val homeSaleAdapter by lazy { HomeSaleAdapter(this) }
    private val homeAllProductAdapter by lazy { HomeAllProductAdapter(this) }
    private val homeViewModel : HomeVM by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeScreenBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initObserve()
        signOut()

        val user = Firebase.auth.currentUser
        val getUid = user?.uid

        getUid?.let {
            val userId  = UserId(it)
            homeViewModel.getAllProducts(userId)
        }
    }

    private fun initSaleProductAdapter(list: List<Product>){
        binding.rvSaleProducts.adapter = homeSaleAdapter
        homeSaleAdapter.submitList(list)
    }

    private fun initAllProductAdapter(allList : List<Product>){
        binding.rvAllDessert.adapter = homeAllProductAdapter
        homeAllProductAdapter.submitList(allList)
    }

    private fun initObserve(){
        homeViewModel.getSaleProducts.observe(viewLifecycleOwner){response ->
            when(response){
                is Resource.Success -> {
                    response.data?.let {
                        binding.saleProgressBar.gone()
                        initSaleProductAdapter(it)
                    }
                }

                is Resource.Loading -> { binding.saleProgressBar.visible() }

                is Resource.Error -> { binding.saleProgressBar.visible() }

                else -> {
                    requireView().snackbar(response.message.orEmpty())
                }
            }
        }

        homeViewModel.getAllProducts.observe(viewLifecycleOwner){allProductResponse ->
            when(allProductResponse){
                is Resource.Success ->{
                    allProductResponse.data?.let {
                        binding.allProgressBar.gone()
                        initAllProductAdapter(it)
                    }
                }

                is Resource.Loading -> { binding.allProgressBar.visible() }

                is Resource.Error -> { binding.allProgressBar.visible() }

                else -> {requireView().snackbar(allProductResponse.message.orEmpty())}
            }
        }
    }

    override fun clickProductItem(id: Int) {
        val action = HomeScreenDirections.actionHomeScreenToDetailScreen(productId = id)
        findNavController().navigate(action)
    }

    private fun signOut(){
        binding.btnSingout.click{
            AlertDialog.Builder(requireContext())
                .setTitle("Sign out")
                .setMessage("Do you want to sign out?")
                .setCancelable(false)
                .setPositiveButton("Yes"){_,_ ->
                    Firebase.auth.signOut()
                    findNavController().navigate(R.id.action_homeScreen_to_signinScreen)
                    requireView().snackbar("Sign out successfully!")
                }
                .setNegativeButton("No",null)
                .show()
        }
    }
}