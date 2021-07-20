package com.example.yourfarmerapp.view

import android.content.Context
import android.util.AttributeSet
import com.example.yourfarmerapp.R
import com.google.android.material.tabs.TabLayout
import java.lang.reflect.Field


class FarmerTabLayout : TabLayout {
    constructor(context: Context?) : super(context!!) {
        initTabMinWidth()
    }

    constructor(context: Context?, attrs: AttributeSet?) : super(context!!, attrs) {
        initTabMinWidth()
    }

    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context!!, attrs, defStyleAttr) {
        initTabMinWidth()
    }

    private fun initTabMinWidth() {
//        int screenWidth = getResources().getDisplayMetrics().widthPixels;
//        int tabMinWidth = screenWidth / TabViewNumber;
        // In scrollable mode, the minimum width of tab
        val tabMinWidth = resources.getDimension(R.dimen.dimen_5).toInt()
        val field: Field
        try {
            field = TabLayout::class.java.getDeclaredField(SCROLLABLE_TAB_MIN_WIDTH)
            field.isAccessible = true
            field[this] = tabMinWidth
        } catch (e: NoSuchFieldException) {
            e.printStackTrace()
        } catch (e: IllegalAccessException) {
            e.printStackTrace()
        }
    }

    companion object {
        // How many tabs are displayed on one screen
        private const val TabViewNumber = 8

        // The lower version of support may be different
        private const val SCROLLABLE_TAB_MIN_WIDTH = "scrollableTabMinWidth"
    }
}
