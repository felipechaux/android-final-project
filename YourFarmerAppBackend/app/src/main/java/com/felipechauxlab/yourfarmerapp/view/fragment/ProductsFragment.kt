package com.felipechauxlab.yourfarmerapp.view.fragment

import android.annotation.SuppressLint
import android.content.res.Configuration
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.yourfarmerapp.R
import com.example.yourfarmerapp.databinding.FragmentProductsBinding
import com.example.yourfarmerapp.view.dialog.SharedDialogViewModel
import com.felipechauxlab.yourfarmerapp.adapter.MyProductsAdapter
import com.felipechauxlab.yourfarmerapp.presenter.IPublishFragmentPresenter
import com.felipechauxlab.yourfarmerapp.presenter.PublishFragmentPresenter
import com.felipechauxlab.yourfarmerapp.restApi.dto.PublishProductDTO
import com.felipechauxlab.yourfarmerapp.utils.NavigationUtils
import com.felipechauxlab.yourfarmerapp.view.dialog.DialogProvider

class ProductsFragment : Fragment(), IProductsFragmentView {

    private val viewModel: PublishFragmentPresenter by activityViewModels()
    private val sharedViewModel: SharedDialogViewModel by activityViewModels()
    private var _binding: FragmentProductsBinding? = null
    private val binding get() = _binding
    private var intOrientation = 0
    private lateinit var layoutManager: RecyclerView.LayoutManager
    private lateinit var navController: NavController

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentProductsBinding.inflate(inflater, container, false)
        val view = binding?.root
        navController = findNavController()
        addObservers()
        logicIndicator()
        activity?.let { viewModel.getPublishProducts(it, this) }
        return view
    }

    private fun addObservers() {
        viewModel.successProductUpdated.observe(viewLifecycleOwner, Observer {
            if(it){
                refreshContent()
            }
        })

    }

    private fun showEditProductDialog(publishProductDTO: PublishProductDTO?) {
        DialogProvider.showEditProductDialog(
            publishProductDTO,
            this,
            sharedViewModel,
            R.id.action_homeFragment_to_farmerDialogFragment, positiveCallback = {
                NavigationUtils.closeDialog(navController, R.id.farmerDialogFragment)
                context?.let { context -> viewModel.editProduct(context, it) }
            }
        )
    }

    override fun generateGridLayout() {
        intOrientation = resources.configuration.orientation;
        if (intOrientation == Configuration.ORIENTATION_PORTRAIT) {
            layoutManager = LinearLayoutManager(context)
            (layoutManager as LinearLayoutManager).orientation = LinearLayoutManager.VERTICAL
        } else {
            layoutManager = GridLayoutManager(context, 2)
        }
        binding?.rvMyProducts?.layoutManager = layoutManager
    }

    override fun createAdapter(products: List<PublishProductDTO?>?): MyProductsAdapter {
        return MyProductsAdapter(products) { id, product ->
            when (id) {
                R.id.btn_edit -> {
                    showEditProductDialog(product)
                }
                R.id.btn_delete -> {
                    println("delete $product")
                }
            }
        }
    }

    private fun logicIndicator() {
        binding?.productIndicator?.setOnRefreshListener { this.refreshContent() }
    }

    private fun refreshContent() {
        activity?.let { viewModel.getPublishProducts(it, this) }
        binding?.productIndicator?.isRefreshing = false
    }

    override fun initAdapterMyProducts(adapter: MyProductsAdapter) {
        binding?.rvMyProducts?.adapter = adapter
    }

}