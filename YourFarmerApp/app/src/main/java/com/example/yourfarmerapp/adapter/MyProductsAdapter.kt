
package com.example.yourfarmerapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.yourfarmerapp.R
import com.example.yourfarmerapp.entities.Product

class MyProductsAdapter( private val listProducts: List<Product>
) :
    RecyclerView.Adapter<MyProductsAdapter.MyProductsAdapterViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MyProductsAdapterViewHolder {
        val v: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.cardview_item_list_my_products, parent, false)
        return MyProductsAdapterViewHolder(v)
    }


    override fun onBindViewHolder(
        myProductsAdapterViewHolder: MyProductsAdapterViewHolder,
        position: Int
    ) {
        val item: Product = listProducts.get(position)
        myProductsAdapterViewHolder.textProductName.text=item.productName
        myProductsAdapterViewHolder.textProductQuantity.text=item.productQuantity.toString()
    }

    override fun getItemCount(): Int {
        return listProducts.size
    }

    class MyProductsAdapterViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView) {
        val textProductName: TextView = itemView.findViewById(R.id.text_product)
        val textProductQuantity: TextView = itemView.findViewById(R.id.text_quantity)
    }
}