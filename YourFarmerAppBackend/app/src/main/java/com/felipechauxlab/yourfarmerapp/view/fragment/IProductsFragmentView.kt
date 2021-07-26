package com.felipechauxlab.yourfarmerapp.view.fragment

import com.felipechauxlab.yourfarmerapp.adapter.MyProductsAdapter
import com.felipechauxlab.yourfarmerapp.restApi.dto.PublishProductDTO

interface IProductsFragmentView {

    fun generateGridLayout()

    fun createAdapter(products: List<PublishProductDTO?>?): MyProductsAdapter

    fun initAdapterMyProducts(adapter: MyProductsAdapter)

}