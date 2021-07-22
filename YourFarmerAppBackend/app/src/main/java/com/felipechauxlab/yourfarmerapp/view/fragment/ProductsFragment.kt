package com.felipechauxlab.yourfarmerapp.view.fragment

import android.content.res.Configuration
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.yourfarmerapp.R
import com.example.yourfarmerapp.adapter.MyProductsAdapter
import com.example.yourfarmerapp.databinding.FragmentProductsBinding
import com.felipechauxlab.yourfarmerapp.entities.Product
import com.felipechauxlab.yourfarmerapp.view.dialog.DialogProvider
import com.example.yourfarmerapp.view.dialog.SharedDialogViewModel

class ProductsFragment : Fragment() {

    private val sharedViewModel: SharedDialogViewModel by activityViewModels()
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
        val adapter = MyProductsAdapter(products) {id,product->
            when (id) {
                R.id.btn_edit -> {
                    showEditProductDialog(product)
                }
                R.id.btn_delete -> {
                    println("delete $product")
                }
            }
        }

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

    private fun showEditProductDialog(product: Product) {
        DialogProvider.showEditProductDialog(
            product,
            this,
            sharedViewModel,
            R.id.action_homeFragment_to_farmerDialogFragment,positiveCallback = {
                println("edit product $it")
            }
        )
    }

}