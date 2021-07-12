package com.example.yourfarmerapp.view

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.example.yourfarmerapp.R
import com.example.yourfarmerapp.databinding.ActivityIntroBinding
import com.example.yourfarmerapp.view.adapter.ViewPagerAdapter
import com.example.yourfarmerapp.view.fragment.IntroOneFragment
import com.example.yourfarmerapp.view.fragment.IntroThreeFragment
import com.example.yourfarmerapp.view.fragment.IntroTwoFragment

class IntroActivity : AppCompatActivity() {

    private lateinit var binding: ActivityIntroBinding
    private val fragmentList = ArrayList<Fragment>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityIntroBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        setupViewPager()
        initListeners()
    }

    private fun setupViewPager() {
        try {
            val adapter = ViewPagerAdapter(this)
            binding.vpIntro.adapter = adapter
            fragmentList.addAll(
                listOf(
                    IntroOneFragment(), IntroTwoFragment(), IntroThreeFragment()
                )
            )
            adapter.setFragmentList(fragmentList)
            binding.indicatorLayout.setIndicatorCount(adapter.itemCount)
            binding.indicatorLayout.selectCurrentPosition(0)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun initListeners() {
        try {
            binding.vpIntro.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
                override fun onPageSelected(position: Int) {
                    binding.indicatorLayout.selectCurrentPosition(position)
                    if (position ==0) {
                        binding.buttonBack.visibility = View.GONE
                    }
                    else{
                        binding.buttonBack.visibility = View.VISIBLE
                    }
                }
            })
            binding.textSkip.setOnClickListener {
                val intent = Intent(this, MainActivity::class.java)
                intent.putExtra(getString(R.string.intent_is_home),true)
                startActivity(intent)
                finish()
            }
            binding.buttonBack.setOnClickListener {
                val position = binding.vpIntro.currentItem
                if (position <= fragmentList.lastIndex) {
                    binding.vpIntro.currentItem = position -1
                }
            }
            binding.buttonNext.setOnClickListener {
                val position = binding.vpIntro.currentItem
                if (position < fragmentList.lastIndex) {
                    binding.vpIntro.currentItem = position + 1
                } else {
                    val intent = Intent(this, MainActivity::class.java)
                    intent.putExtra(getString(R.string.intent_is_home),true)
                    startActivity(intent)
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

}
