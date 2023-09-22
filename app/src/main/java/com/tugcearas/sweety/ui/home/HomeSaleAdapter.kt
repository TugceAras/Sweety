package com.tugcearas.sweety.ui.home

import android.text.SpannableStringBuilder
import android.text.style.StrikethroughSpan
import android.view.ViewGroup
import android.view.LayoutInflater
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.tugcearas.sweety.data.model.Product
import com.tugcearas.sweety.databinding.ItemProductBinding
import com.tugcearas.sweety.util.extensions.loadImage

class HomeSaleAdapter(
    private val productListener: ProductListener
) : ListAdapter<Product, HomeSaleAdapter.ProductViewHolder>(DiffCallBack()) {

       inner class ProductViewHolder(
            private val binding: ItemProductBinding,
            private val productListener: ProductListener
       ):RecyclerView.ViewHolder(binding.root){

            fun bind(product:Product) = with(binding){

                tvSaleTitle.text = product.title
                
                val salePriceText = "${product.salePrice} $"
                val spannable = SpannableStringBuilder(salePriceText)
                spannable.setSpan(StrikethroughSpan(), 0, salePriceText.length, 0)
                tvSalePrice.text = spannable


                tvPrice.text = "${product.price} $"

                ivSaleProduct.loadImage(product.imageOne)

                root.setOnClickListener {
                    productListener.clickProductItem(product.id ?: 1)
                }
            }
       }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder =
        ProductViewHolder(
            ItemProductBinding.inflate(LayoutInflater.from(parent.context), parent, false), productListener
        )

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) = holder.bind(getItem(position))

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