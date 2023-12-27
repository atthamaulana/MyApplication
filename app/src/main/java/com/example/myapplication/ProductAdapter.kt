package com.example.myapplication

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class ProductAdapter(private val productList: List<Product>) :
    RecyclerView.Adapter<ProductAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val nameTextView: TextView = itemView.findViewById(R.id.textViewName)
        val priceTextView: TextView = itemView.findViewById(R.id.textViewPrice)
        val imageViewProduct: ImageView = itemView.findViewById(R.id.imageViewProduct)
        // Add more TextViews or ImageViews for other fields if needed
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(
            R.layout.item_product,
            parent,
            false
        )
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val product = productList[position]
        holder.nameTextView.text = product.name
        holder.priceTextView.text = product.price

        // Load image using Glide
        Glide.with(holder.itemView.context)
            .load(product.imageUri)
            .into(holder.imageViewProduct)

        // Set other fields if needed
    }

    override fun getItemCount(): Int {
        return productList.size
    }
}