package com.felipechauxlab.yourfarmerapp.view

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.IntentSender
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.util.Log
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.example.yourfarmerapp.R
import com.example.yourfarmerapp.databinding.ActivityMainBinding
import com.example.yourfarmerapp.view.adapter.ViewPagerAdapter
import com.felipechauxlab.yourfarmerapp.utils.Constants
import com.felipechauxlab.yourfarmerapp.utils.Constants.SHARED_PREFERENCES_ID
import com.felipechauxlab.yourfarmerapp.view.fragment.LoginFragment
import com.felipechauxlab.yourfarmerapp.view.fragment.ProductsFragment
import com.felipechauxlab.yourfarmerapp.view.fragment.PublishFragment
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.common.api.ResolvableApiException
import com.google.android.gms.location.*
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.tasks.Task
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.karumi.dexter.Dexter
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionDeniedResponse
import com.karumi.dexter.listener.PermissionGrantedResponse
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.single.PermissionListener

class MainActivity : AppCompatActivity(), PermissionListener {

    private val viewModel: MainViewModel by viewModels { MainViewModelFactory() }
    private lateinit var binding: ActivityMainBinding
    private lateinit var titles:Array<String>
    private val fragmentList = ArrayList<Fragment>()
    var fragment: Fragment? = null
    private lateinit var  navController : NavController
    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        initNavHost()
        initObservers()
        setupViewPager()
        tabLayoutListener()
        fusedLocationProviderClient = FusedLocationProviderClient(this)

        if (isPermissionGiven()){
            requestLocation()
        } else {
            givePermission()
        }
    }

    override fun onPermissionGranted(response: PermissionGrantedResponse?) {
        requestLocation()
    }

    override fun onPermissionDenied(response: PermissionDeniedResponse?) {
        Toast.makeText(this, "Permission required for showing location", Toast.LENGTH_LONG).show()
        finish()
    }

    override fun onPermissionRationaleShouldBeShown(
        permission: PermissionRequest?,
        token: PermissionToken?
    ) {
        token?.continuePermissionRequest()
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        when (requestCode) {
            LoginFragment.REQUEST_CHECK_SETTINGS -> {
                requestLocation()
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    private fun isPermissionGiven(): Boolean{
        return ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
    }

    private fun givePermission() {
        Dexter.withActivity(this)
                .withPermission(Manifest.permission.ACCESS_FINE_LOCATION)
                .withListener(this)
                .check()
    }

    private fun requestLocation(){
        val locationRequest = LocationRequest()
        locationRequest.setFastestInterval(2000)
                .setInterval(10 * 1000.toLong()).
                priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        val builder = LocationSettingsRequest.Builder().addLocationRequest(locationRequest)
            val result: Task<LocationSettingsResponse> = LocationServices.getSettingsClient(this).checkLocationSettings(
                builder.build()
            )
            result.addOnCompleteListener { task ->
                try {
                    val response = task.getResult(ApiException::class.java)
                    if (response?.locationSettingsStates?.isLocationPresent == true){
                        println("isLocationPresent true")
                        getCurrentLocation()
                    }
                }catch (e: ApiException){
                    when (e.statusCode) {
                        LocationSettingsStatusCodes.RESOLUTION_REQUIRED -> try {
                            val resolvable = e as ResolvableApiException
                            resolvable.startResolutionForResult(
                                this,
                                LoginFragment.REQUEST_CHECK_SETTINGS
                            )
                        } catch (e: IntentSender.SendIntentException) {
                            e.printStackTrace()
                        } catch (e: ClassCastException) {
                            e.printStackTrace()
                        }
                        LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE -> {
                        }
                    }
                }
            }
    }

    @SuppressLint("MissingPermission")
    private fun getCurrentLocation() {
        fusedLocationProviderClient.lastLocation
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful && task.result != null) {
                        val location= task.result
                        location?.let {
                            val sharedPreferences = this.getSharedPreferences(
                                SHARED_PREFERENCES_ID,
                                Context.MODE_PRIVATE
                            )
                            sharedPreferences.edit()?.putFloat(Constants.SHARED_PREFERENCE_LATITUDE,
                                it.latitude.toFloat()
                            )?.apply()
                            sharedPreferences.edit()?.putFloat(Constants.SHARED_PREFERENCE_LONGITUDE,
                                it.longitude.toFloat()
                            )?.apply()
                        }
                    }
        }
    }

    private fun initNavHost() {
        val navHostFragment =
                supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
         navController = navHostFragment.navController

        intent.getBooleanExtra(getString(R.string.intent_is_home), false).let {
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
                binding.appBar.tabLayout, binding.appBar.viewPager
            ) { tab: TabLayout.Tab, position: Int -> tab.text = titles[position] }.attach()
            for (i in titles.indices) {
                binding.appBar.tabLayout.getTabAt(i)?.customView = getTabView(titles[i])
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun getTabView(title: String?): View {
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
        viewModel.requestLocation.observe(this, Observer {
            if (it) {
                requestLocation()
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