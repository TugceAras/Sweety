package com.tugcearas.sweety.ui.cart

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
import com.tugcearas.sweety.data.model.Product
import com.tugcearas.sweety.data.model.cart.ClearCartRequestModel
import com.tugcearas.sweety.data.model.cart.DeleteFromCartRequestModel
import com.tugcearas.sweety.databinding.FragmentCartScreenBinding
import com.tugcearas.sweety.util.extensions.click
import com.tugcearas.sweety.util.extensions.gone
import com.tugcearas.sweety.util.extensions.snackbar
import com.tugcearas.sweety.util.extensions.visible
import com.tugcearas.sweety.util.resource.Resource
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CartScreen : Fragment(), CartAdapter.CartProductListener {

    private lateinit var binding: FragmentCartScreenBinding
    private val cartViewModel: CartVM by viewModels()
    private val cartAdapter by lazy { CartAdapter(::deleteProduct, this) }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCartScreenBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initObserve()

        val getUserId = Firebase.auth.currentUser
        val userId = getUserId?.uid

        if (userId != null) {
            cartViewModel.getCartProducts(userId)
        }

        binding.btnAllProductDelete.click {
            AlertDialog.Builder(requireContext())
                .setTitle("Clear cart")
                .setMessage("Do you want to clear cart?")
                .setCancelable(false)
                .setPositiveButton("Yes"){_,_ ->
                    if (userId != null) {
                        val getCartRequest = ClearCartRequestModel(userId)
                        cartViewModel.clearCart(getCartRequest)
                        val emptyList = emptyList<Product>()
                        cartAdapter.submitList(emptyList)
                        updateTotalPrice(emptyList)
                        with(binding){
                            emptyCart.visible()
                            cartEmptyTitle.visible()
                        }
                    }
                }
                .setNegativeButton("No",null)
                .show()
        }
    }

    private fun initAdapter(list: List<Product>){
        binding.rvCart.adapter = cartAdapter
        cartAdapter.submitList(list)
        updateTotalPrice(list)
    }

    private fun initObserve(){
        cartViewModel.cartProducts.observe(viewLifecycleOwner){response ->
            when(response){
                is Resource.Success -> {
                    response.data?.let {
                        initAdapter(it)
                        if(it.isNotEmpty()){
                            clickPayButton()
                        }
                        else{
                            with(binding){
                                emptyCart.visible()
                                cartEmptyTitle.visible()
                            }
                        }
                    }
                }

                is Resource.Error -> {
                    println(response.message.toString())
                }

                is Resource.Loading -> {}

                else -> {requireView().snackbar("Cart is empty!")}
            }
        }

        cartViewModel.deleteFromCart.observe(viewLifecycleOwner){cartResponse->
            when(cartResponse){
                is Resource.Success -> {
                    cartResponse.data?.message?.let {  message ->
                        requireView().snackbar(message)
                    }
                }

                is Resource.Error -> {
                    requireView().snackbar(cartResponse.message.toString())
                }

                is Resource.Loading -> {}

                else -> { cartResponse.message.toString()}
            }
        }

        cartViewModel.clearCart.observe(viewLifecycleOwner){clearCartResponse ->
            when(clearCartResponse){
                is Resource.Success -> {
                    clearCartResponse.data?.message?.let {cartMessage ->
                        requireView().snackbar(cartMessage)
                    }
                }

                is Resource.Error -> {
                    requireView().snackbar(clearCartResponse.message.toString())
                }

                is Resource.Loading -> {}

                else -> {clearCartResponse.message.toString()}
            }
        }
    }

    private fun deleteProduct(id:Int){

        AlertDialog.Builder(requireContext())
            .setTitle("Delete Dessert")
            .setMessage("Do you want to delete?")
            .setPositiveButton("Yes"){_,_ ->
                val getRequest = DeleteFromCartRequestModel(id)
                cartViewModel.deleteFromCart(getRequest)

                val currentListCopy = cartAdapter.currentList.toMutableList()
                val positionToRemove = currentListCopy.indexOfFirst { it.id == id }
                if (positionToRemove != -1) {
                    currentListCopy.removeAt(positionToRemove)
                    cartAdapter.submitList(currentListCopy)
                    updateTotalPrice(currentListCopy)
                    if (currentListCopy.isEmpty()) {
                        with(binding){
                            emptyCart.visible()
                            cartEmptyTitle.visible()
                        }
                    }
                }
            }.setNegativeButton("No",null)
            .show()
    }

    override fun clickProductItem(id: Int) {
        val action = CartScreenDirections.actionCartScreenToDetailScreen(productId = id)
        findNavController().navigate(action)
    }

    private fun clickPayButton(){
        binding.btnPay.click {
            val action = CartScreenDirections.actionCartScreenToPaymentScreen()
            findNavController().navigate(action)
        }
    }

    private fun calculateTotalPrice(cartProducts: List<Product>): Double {
        var totalPrice = 0.0
        for (product in cartProducts) {
            product.price?.let {
                totalPrice += it
            }
        }
        return totalPrice
    }

    private fun updateTotalPrice(cartProducts: List<Product>) {
        val totalPrice = calculateTotalPrice(cartProducts)
        binding.tvTotal.text = "$totalPrice $"
    }
}