package com.tugcearas.sweety.ui.search

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.tugcearas.sweety.R
import com.tugcearas.sweety.data.model.Product
import com.tugcearas.sweety.databinding.FragmentSearchScreenBinding
import com.tugcearas.sweety.util.resource.Resource
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchScreen : Fragment(), SearchAdapter.ProductListener {

    private lateinit var binding: FragmentSearchScreenBinding
    private val searchViewModel: SearchVM by viewModels()
    private val searchAdapter by lazy { SearchAdapter(this) }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSearchScreenBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initAdapter(emptyList())
        initObserve()

        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if (newText?.length ?: 0 >= 3) {
                    searchViewModel.searchProduct(newText.orEmpty())
                } else {
                    searchAdapter.submitList(emptyList())
                }
                return true
            }
        })
    }

    private fun initAdapter(list: List<Product>){
        binding.rvSearchview.adapter = searchAdapter
        searchAdapter.submitList(list)
    }

    private fun initObserve() {
        searchViewModel.searchProduct.observe(viewLifecycleOwner) { response ->
            when (response) {
                is Resource.Success -> {
                    searchAdapter.submitList(response.data)
                }
                is Resource.Error -> {

                }
                is Resource.Loading -> {

                }
            }
        }
    }

    override fun clickProductItem(id: Int) {
        val action = SearchScreenDirections.actionSearchScreenToDetailScreen(productId = id)
        findNavController().navigate(action)
    }
}