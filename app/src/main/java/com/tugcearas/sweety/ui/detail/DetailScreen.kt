package com.tugcearas.sweety.ui.detail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.tugcearas.sweety.R
import com.tugcearas.sweety.data.model.Product
import com.tugcearas.sweety.data.model.cart.AddToCartRequestModel
import com.tugcearas.sweety.data.model.favorite.FavoriteModel
import com.tugcearas.sweety.databinding.FragmentDetailScreenBinding
import com.tugcearas.sweety.util.extensions.click
import com.tugcearas.sweety.util.extensions.loadImage
import com.tugcearas.sweety.util.extensions.snackbar
import com.tugcearas.sweety.util.resource.Resource
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailScreen : Fragment() {

    private lateinit var binding:FragmentDetailScreenBinding
    private val args: DetailScreenArgs by navArgs()
    private val detailViewModel: DetailVM by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDetailScreenBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        detailViewModel.getDetailProduct(args.productId)
        initObserve()
        clickBackButton()

        val getUserId = Firebase.auth.currentUser
        val userId = getUserId?.uid

        with(binding){

            btnAddCart.click {
                val addToCartRequestModel = AddToCartRequestModel(userId, args.productId)
                detailViewModel.addToCart(addToCartRequestModel)
            }
            btnFavIcon.click {
                detailViewModel.addFavorite()
                binding.btnFavIcon.setImageResource(R.drawable.fill_fav_icon)
                requireView().snackbar("Successfully added to favorites!")
            }
        }
    }

    private fun initObserve(){
        detailViewModel.getProduct.observe(viewLifecycleOwner) {
            if (it != null){
                with(binding) {
                    tvProductTitle.text = it.data?.title
                    detailImageview.loadImage(it.data?.imageOne)
                    tvProductDescription.text = it.data?.description
                    tvCategoryName.text = it.data?.category
                    tvCount.text = it.data?.count.toString()
                    tvPrice.text = it.data?.price.toString() + " $"
                    tvSalePrice.text = it.data?.salePrice.toString() + " $"
                    ratingBar.rating = it.data?.rate?.toFloat() ?: 0.0f
                }
            }
        }

        detailViewModel.addToCartResult.observe(viewLifecycleOwner){response ->
            when(response){
                is Resource.Success -> {
                    response.data?.message?.let {  message ->
                        requireView().snackbar(message)
                    }
                }

                is Resource.Loading -> {}

                is Resource.Error -> {
                    requireView().snackbar(response.message.toString())
                }
                else -> {}
            }
        }

    }

    private fun clickBackButton(){
        binding.detailToolbar.setNavigationOnClickListener {
            findNavController().navigateUp()
        }
    }
}
