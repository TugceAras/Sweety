package com.tugcearas.sweety.ui.cart

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.tugcearas.sweety.data.model.Product
import com.tugcearas.sweety.databinding.ItemCartBinding
import com.tugcearas.sweety.util.extensions.click
import com.tugcearas.sweety.util.extensions.loadImage

class CartAdapter(
    var onDeleteClick: (Int)->Unit = {},
    private val cartListener: CartProductListener
) : ListAdapter<Product, CartAdapter.CartViewHolder>(DiffCallBack()) {

    inner class CartViewHolder(
        private val binding: ItemCartBinding,
        private val cartListener: CartProductListener
    ): RecyclerView.ViewHolder(binding.root){

        fun bindProducts(product: Product) = with(binding){

            tvProductCartTitle.text = product.title

            tvProductPrice.text = "${product.price} $"

            ivCartProduct.loadImage(product.imageOne)

            binding.btnDelete.click {
                product.id?.let { onDeleteClick(it) }
            }

            root.setOnClickListener {
                cartListener.clickProductItem(product.id ?: 1)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartViewHolder =
        CartViewHolder(
            ItemCartBinding.inflate(LayoutInflater.from(parent.context), parent, false), cartListener
        )

    override fun onBindViewHolder(holder: CartViewHolder, position: Int) =
        holder.bindProducts(getItem(position))

    class DiffCallBack: DiffUtil.ItemCallback<Product>(){
        override fun areItemsTheSame(oldItem: Product, newItem: Product): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Product, newItem: Product): Boolean {
            return oldItem == newItem
        }
    }

    interface CartProductListener {
        fun clickProductItem(id: Int)
    }
}