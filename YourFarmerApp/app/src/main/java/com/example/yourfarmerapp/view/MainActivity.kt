package com.example.yourfarmerapp.view

import android.os.Bundle
import android.util.Log
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.NavHostFragment
import com.example.yourfarmerapp.R
import com.example.yourfarmerapp.databinding.ActivityMainBinding
import com.example.yourfarmerapp.view.adapter.ViewPagerAdapter
import com.example.yourfarmerapp.view.fragment.ProductsFragment
import com.example.yourfarmerapp.view.fragment.PublishFragment
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import androidx.lifecycle.Observer
import androidx.navigation.NavController

class MainActivity : AppCompatActivity() {

    private val viewModel: MainViewModel by viewModels { MainViewModelFactory() }
    private lateinit var binding: ActivityMainBinding
    private lateinit var titles:Array<String>
    private val fragmentList = ArrayList<Fragment>()
    var fragment: Fragment? = null
    private lateinit var  navController : NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        initNavHost()
        initObservers()
        setupViewPager()
        tabLayoutListener()
    }

    private fun initNavHost() {
        val navHostFragment =
                supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
         navController = navHostFragment.navController

        intent.getBooleanExtra(getString(R.string.intent_is_home),false).let {
            if(it){
                navController.navigate(R.id.homeFragment)
            }
        }
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
            binding.appBar.viewPager.adapter = adapter
            TabLayoutMediator(
                    binding.appBar.tabLayout,  binding.appBar.viewPager
            ) { tab: TabLayout.Tab, position: Int -> tab.text = titles[position] }.attach()
            for (i in titles.indices) {
                binding.appBar.tabLayout.getTabAt(i)?.customView = getTabView(titles[i])
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun getTabView(title: String?): View? {
        val v: View =
                LayoutInflater.from(this).inflate(R.layout.tab_vedio_item_view, null)
        val textView: TextView = v.findViewById<View>(R.id.text_tab) as TextView
        textView.text = title
        textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18f);
        return v
    }

    private fun changeTabSelect(tab: TabLayout.Tab) {
        val view = tab.customView
        (view?.findViewById<View>(R.id.text_tab))?.let {
            val textTitle = it as TextView
            textTitle.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20f);
            textTitle.paint.isFakeBoldText = true
        }
    }

    private fun changeTabNormal(tab: TabLayout.Tab) {
        val view = tab.customView
        (view?.findViewById<View>(R.id.text_tab))?.let {
            val textTitle = it as TextView
            textTitle.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18f);
            textTitle.paint.isFakeBoldText = false
        }
    }

    private fun tabLayoutListener() {//Navigation bar picture processing when TabLayout is switched
        binding.appBar.tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {//Select picture operation
                changeTabSelect(tab)
            }
            override fun onTabUnselected(tab: TabLayout.Tab) {//Unselected picture operation
                changeTabNormal(tab)
            }
            override fun onTabReselected(tab: TabLayout.Tab) {
                Log.i("TAG", "onTabReselected start")
            }
        })
    }

    private fun initObservers() {
        viewModel.showTabLayout.observe(this, Observer {
            if (it) {
                showTabLayout()
            } else {
                hideTabLayout()
            }
        })
    }

    private fun showTabLayout() {
        binding.appBar.tabLayout.visibility=View.VISIBLE
        binding.appBar.viewPager.visibility=View.VISIBLE
    }

    private fun hideTabLayout() {
        binding.appBar.tabLayout.visibility=View.GONE
        binding.appBar.viewPager.visibility=View.GONE
    }
}