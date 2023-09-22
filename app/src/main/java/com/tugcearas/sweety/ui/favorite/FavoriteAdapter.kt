package com.tugcearas.sweety.ui.favorite

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.tugcearas.sweety.data.model.favorite.FavoriteModel
import com.tugcearas.sweety.databinding.ItemFavoriteBinding
import com.tugcearas.sweety.ui.home.HomeAllProductAdapter
import com.tugcearas.sweety.util.extensions.click
import com.tugcearas.sweety.util.extensions.loadImage

class FavoriteAdapter(
    var onDeleteClick: (Int)->Unit={},
    private val productListener: ProductFavListener
) : ListAdapter<FavoriteModel, FavoriteAdapter.FavoriteViewHolder>(DiffCallBack()) {

    inner class FavoriteViewHolder(
        private val binding: ItemFavoriteBinding,
        private val productListener: ProductFavListener
    ): RecyclerView.ViewHolder(binding.root){

        fun bind(product: FavoriteModel) = with(binding){

            tvFavTitle.text = product.title

            ratingBar.rating = product.rate?.toFloat() ?: 0.0f

            ivFav.loadImage(product.imageOne)

            binding.btnFavDelete.click {
                product.id?.let { onDeleteClick(it) }
            }

            root.setOnClickListener {
                productListener.clickProductItem(product.id ?: 1)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteViewHolder =
        FavoriteViewHolder(
            ItemFavoriteBinding.inflate(LayoutInflater.from(parent.context), parent, false),
            productListener
        )

    override fun onBindViewHolder(holder: FavoriteViewHolder, position: Int) = holder.bind(getItem(position))

    class DiffCallBack: DiffUtil.ItemCallback<FavoriteModel>(){
        override fun areItemsTheSame(oldItem: FavoriteModel, newItem: FavoriteModel): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: FavoriteModel, newItem: FavoriteModel): Boolean {
            return oldItem == newItem
        }
    }

    interface ProductFavListener {
        fun clickProductItem(id: Int)
    }
}