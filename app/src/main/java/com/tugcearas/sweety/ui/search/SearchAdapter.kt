package com.tugcearas.sweety.ui.search

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.tugcearas.sweety.data.model.Product
import com.tugcearas.sweety.databinding.ItemSearchBinding
import com.tugcearas.sweety.util.extensions.loadImage

class SearchAdapter (
    private val productListener: ProductListener
) : ListAdapter<Product, SearchAdapter.SearchViewHolder>(DiffCallBack()) {

    inner class SearchViewHolder(
        private val binding: ItemSearchBinding,
        private val productListener: ProductListener
    ): RecyclerView.ViewHolder(binding.root){

        fun bindProducts(product: Product) = with(binding){

            tvSearchTitle.text = product.title

            tvSearchPrice.text = "${product.price} $"

            ivSearch.loadImage(product.imageOne)

            root.setOnClickListener {
                productListener.clickProductItem(product.id ?: 1)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchViewHolder =
        SearchViewHolder(
            ItemSearchBinding.inflate(LayoutInflater.from(parent.context), parent, false),
            productListener
        )

    override fun onBindViewHolder(holder: SearchViewHolder, position: Int) =
        holder.bindProducts(getItem(position))

    class DiffCallBack: DiffUtil.ItemCallback<Product>(){
        override fun areItemsTheSame(oldItem: Product, newItem: Product): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Product, newItem: Product): Boolean {
            return oldItem == newItem
        }
    }

    interface ProductListener {
        fun clickProductItem(id: Int)
    }
}