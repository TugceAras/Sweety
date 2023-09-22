package com.tugcearas.sweety.ui.favorite

import android.app.AlertDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.tugcearas.sweety.databinding.FragmentFavoriteScreenBinding
import com.tugcearas.sweety.ui.home.HomeScreenDirections
import com.tugcearas.sweety.util.extensions.gone
import com.tugcearas.sweety.util.extensions.snackbar
import com.tugcearas.sweety.util.extensions.visible
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FavoriteScreen : Fragment() , FavoriteAdapter.ProductFavListener{

    private lateinit var binding: FragmentFavoriteScreenBinding
    private val favFavoriteViewModel: FavoriteVM by viewModels()
    private val favAdapter by lazy { FavoriteAdapter(productListener = this) }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFavoriteScreenBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initAdapter()
        initObserver()
    }

    private fun initAdapter(){
        binding.rvFav.adapter = favAdapter
        favAdapter.onDeleteClick = {
            AlertDialog.Builder(requireContext())
                .setTitle("Delete From Favorites")
                .setMessage("Do you want to delete?")
                .setPositiveButton("Yes") { _, _ ->
                    favFavoriteViewModel.deleteFromFavorites(it)
                    requireView().snackbar("Successfully deleted!")
                }
                .setNegativeButton("No",null)
                .show()
        }
    }

    private fun initObserver(){
        favFavoriteViewModel.getFavoriteProduct.observe(viewLifecycleOwner){ favoriteProducts ->
            if (favoriteProducts.isNotEmpty()) {
                with(binding){
                    emptyFav.gone()
                    emptyFavTitle.gone()
                }
                favAdapter.submitList(favoriteProducts)
            } else {
                with(binding){
                    emptyFav.visible()
                    emptyFavTitle.visible()
                }
                favAdapter.submitList(emptyList())
            }
        }
    }

    override fun clickProductItem(id: Int) {
        val action = FavoriteScreenDirections.actionFavoriteScreenToDetailScreen(productId = id)
        findNavController().navigate(action)
    }
}