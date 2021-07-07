package com.example.yourfarmerapp.view.fragment

import android.content.res.Configuration
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.yourfarmerapp.adapter.MyProductsAdapter
import com.example.yourfarmerapp.databinding.FragmentProductsBinding
import com.example.yourfarmerapp.entities.Product

class ProductsFragment : Fragment() {

    private var _binding: FragmentProductsBinding? = null
    private val binding get() = _binding
    private var intOrientation = 0
    private lateinit var layoutManager: RecyclerView.LayoutManager

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentProductsBinding.inflate(inflater, container, false)
        val view = binding?.root

        initMyProductsAdapter()
        return view
    }

    private fun initMyProductsAdapter() {
        val products: ArrayList<Product> = ArrayList()
        val productOne = Product().apply {
            this.productName = "Panela"
            this.productQuantity = 4
        }
        val productTwo = Product().apply {
            this.productName = "Platano verde"
            this.productQuantity = 10
        }
        products.add(productOne)
        products.add(productTwo)
        val adapter = MyProductsAdapter(products)
        intOrientation = resources.configuration.orientation;
        if (intOrientation == Configuration.ORIENTATION_PORTRAIT) {
            layoutManager = LinearLayoutManager(context)
            (layoutManager as LinearLayoutManager).orientation = LinearLayoutManager.VERTICAL
        } else {
            layoutManager = GridLayoutManager(context, 2)
        }
        binding?.rvMyProducts?.layoutManager = layoutManager
        binding?.rvMyProducts?.adapter = adapter
    }
}