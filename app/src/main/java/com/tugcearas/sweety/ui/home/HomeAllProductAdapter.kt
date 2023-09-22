package com.tugcearas.sweety.ui.home

import android.text.SpannableStringBuilder
import android.text.style.StrikethroughSpan
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.tugcearas.sweety.data.model.Product
import com.tugcearas.sweety.databinding.ItemAllProductBinding
import com.tugcearas.sweety.util.extensions.gone
import com.tugcearas.sweety.util.extensions.loadImage
import com.tugcearas.sweety.util.extensions.visible

class HomeAllProductAdapter(
    private val productListener: ProductAllListener
) : ListAdapter<Product, HomeAllProductAdapter.AllProductViewHolder>(DiffCallBack()) {

    inner class AllProductViewHolder(
        private val binding: ItemAllProductBinding,
        private val productListener: ProductAllListener
    ): RecyclerView.ViewHolder(binding.root){

        fun bindProducts(product: Product) = with(binding){

            tvTitle.text = product.title

            if (product.saleState == true){
                val salePriceText = "${product.salePrice} $"
                val spannable = SpannableStringBuilder(salePriceText)
                spannable.setSpan(StrikethroughSpan(), 0, salePriceText.length, 0)
                tvSalePrice.text = spannable
                tvSalePrice.visible()
            }
            else{
                tvSalePrice.text = "There is no discount"
                tvPrice.gone()
            }

            tvPrice.text = "${product.price} $"
            tvPrice.visible()

            ivProduct.loadImage(product.imageOne)

            root.setOnClickListener {
                productListener.clickProductItem(product.id ?: 1)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AllProductViewHolder =
        AllProductViewHolder(
            ItemAllProductBinding.inflate(LayoutInflater.from(parent.context), parent, false), productListener
        )

    override fun onBindViewHolder(holder: AllProductViewHolder, position: Int) =
        holder.bindProducts(getItem(position))

    class DiffCallBack: DiffUtil.ItemCallback<Product>(){
        override fun areItemsTheSame(oldItem: Product, newItem: Product): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Product, newItem: Product): Boolean {
            return oldItem == newItem
        }
    }

    interface ProductAllListener {
        fun clickProductItem(id: Int)
    }
}