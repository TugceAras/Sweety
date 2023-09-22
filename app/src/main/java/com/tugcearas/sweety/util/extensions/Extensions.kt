package com.tugcearas.sweety.util.extensions

import android.graphics.Color
import android.view.View
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.google.android.material.snackbar.Snackbar
import com.tugcearas.sweety.R

fun View.gone(){
    visibility = View.GONE
}

fun View.visible(){
    visibility = View.VISIBLE
}

fun View.click(func:() -> Unit){
    this.setOnClickListener{
        func()
    }
}

fun View.snackbar(message:String){
    Snackbar.make(this,message,1200)
        .setTextColor(Color.WHITE)
        .setBackgroundTint(resources.getColor(R.color.brown))
        .show()
}

fun ImageView.loadImage(url: String?) {
    Glide.with(this.context).load(url).into(this)
}


