package com.felipechauxlab.yourfarmerapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.yourfarmerapp.R
import com.felipechauxlab.yourfarmerapp.restApi.dto.PublishProductDTO
import com.squareup.picasso.Picasso

class MyProductsAdapter(private val listProducts: List<PublishProductDTO?>?, private val listener: (Int, PublishProductDTO?) -> Unit) : RecyclerView.Adapter<MyProductsAdapter.MyProductsAdapterViewHolder>() {

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
        val item: PublishProductDTO? = listProducts?.get(position)
        getImageUrl(myProductsAdapterViewHolder.imageProduct,item?.productPhoto.toString())
        myProductsAdapterViewHolder.textProductName.text = item?.productName
        myProductsAdapterViewHolder.textProductQuantity.text = item?.productQuantity.toString()

        myProductsAdapterViewHolder.buttonEdit.setOnClickListener {
            listener(it.id, item)
        }
        myProductsAdapterViewHolder.buttonDelete.setOnClickListener {
            listener(it.id, item)
        }
    }

    override fun getItemCount(): Int {
        return listProducts!!.size
    }

    class MyProductsAdapterViewHolder(itemView: View) :
            RecyclerView.ViewHolder(itemView) {
        val textProductName: TextView = itemView.findViewById(R.id.text_product)
        val textProductQuantity: TextView = itemView.findViewById(R.id.text_quantity)
        val buttonEdit: ImageButton = itemView.findViewById(R.id.btn_edit)
        val buttonDelete: ImageButton = itemView.findViewById(R.id.btn_delete)
        val imageProduct: ImageView = itemView.findViewById(R.id.img_product)
    }

    private fun getImageUrl(image: ImageView, url: String) {
        try {
            Picasso.get().load(url).transform(CircleTransform()).into(image)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}