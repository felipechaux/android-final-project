package com.example.yourfarmerapp.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.yourfarmerapp.R
import com.example.yourfarmerapp.databinding.ActivityHomeBinding
import com.example.yourfarmerapp.view.adapter.ViewPagerAdapter
import com.example.yourfarmerapp.view.fragment.ProductsFragment
import com.example.yourfarmerapp.view.fragment.PublishFragment
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import kotlin.collections.ArrayList

class HomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHomeBinding
    private val fragmentList = ArrayList<Fragment>()
    private lateinit var titles:Array<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        setupViewPager()
    }

    private fun setupViewPager() {
        try {
            titles=arrayOf(getString(R.string.text_publish), getString(R.string.text_my_products))
            val adapter = ViewPagerAdapter(this)
            fragmentList.addAll(
                listOf(
                    PublishFragment(), ProductsFragment()
                )
            )
            adapter.setFragmentList(fragmentList)
            binding.viewPager.adapter = adapter
            TabLayoutMediator(
                binding.tabLayout, binding.viewPager
            ) { tab: TabLayout.Tab, position: Int -> tab.text = titles[position] }.attach()

        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}
