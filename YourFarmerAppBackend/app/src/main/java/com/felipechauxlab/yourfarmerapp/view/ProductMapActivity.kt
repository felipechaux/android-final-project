package com.felipechauxlab.yourfarmerapp.view

import android.annotation.SuppressLint
import android.graphics.*
import android.os.Bundle
import android.os.StrictMode
import android.view.View
import android.widget.ImageView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.example.yourfarmerapp.R
import com.felipechauxlab.yourfarmerapp.adapter.CircleTransform
import com.felipechauxlab.yourfarmerapp.presenter.PublishFragmentPresenter
import com.felipechauxlab.yourfarmerapp.restApi.dto.PublishProductDTO
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.squareup.picasso.Picasso
import java.io.InputStream
import java.net.HttpURLConnection
import java.net.URL


class ProductMapActivity : AppCompatActivity(), OnMapReadyCallback {

    private val viewModel: PublishFragmentPresenter by viewModels { PublishViewModelFactory() }
    private lateinit var mMap: GoogleMap
    private var placeLocation: LatLng? = null
    private var locations: ArrayList<LatLng>? = null
    private var productsMap: List<PublishProductDTO?>?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product_map)
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        addObservers()
        val mapFragment = supportFragmentManager
                .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    private fun addObservers() {
        viewModel.products.observe(this, Observer {
            it?.let {
                productsMap = it
                loadLocations()
                println("products map enable $productsMap")
            }
        })
    }

    private fun loadLocations() {
        locations = ArrayList()
        productsMap?.let {
            for (product in it){
                locations?.add(LatLng(product?.latitude!!.toDouble(), product.longitude!!.toDouble()))
            }
        }
        println("locations loaded $locations")
    }


    override fun onMapReady(googleMap: GoogleMap) {
        viewModel.getPublishProducts(this, null)
        mMap = googleMap
        val policy = StrictMode.ThreadPolicy.Builder().permitAll().build()
        StrictMode.setThreadPolicy(policy)

        placeLocation = LatLng(4.2390239, -74.230111219)
       /*mMap.addMarker(placeLocation?.let {
            //  MarkerOptions().position(it).title("Marker ").icon(BitmapDescriptorFactory.fromBitmap(getBitmapFromLink("https://res.cloudinary.com/dpyqmhhmi/image/upload/v1627937551/nji6scjofgbff5peup9k.jpg")))
            MarkerOptions().position(it).title("Marker ").icon(BitmapDescriptorFactory.fromBitmap(getMarkerBitmapFromView("https://res.cloudinary.com/dpyqmhhmi/image/upload/v1627937551/nji6scjofgbff5peup9k.jpg")))
        })*/
        mMap.moveCamera(CameraUpdateFactory.newLatLng(placeLocation))

        println("map ready y locations  $locations")

        locations?.let {
            for (i in 0 until it.size) {
                mMap.addMarker(MarkerOptions().position(it[i]).title("p $i"))
            }
        }
    }

    private fun getBitmapFromLink(link: String?): Bitmap? {
        var myBitmap: Bitmap? = null
        try {
            val url = URL(link)
            val connection: HttpURLConnection = url.openConnection() as HttpURLConnection
            connection.connect()
            val input: InputStream = connection.inputStream
            myBitmap = BitmapFactory.decodeStream(input)
        } catch (e: java.lang.Exception) {
            e.printStackTrace()
        }
        return myBitmap
    }

    @SuppressLint("ResourceType")
    private fun getMarkerBitmapFromView(imageURL: String): Bitmap? {
        val customMarkerView: View = layoutInflater.inflate(R.layout.marker_custom, null, false)
        val markerImageView: ImageView = customMarkerView.findViewById<View>(R.id.image_marker) as ImageView
        markerImageView.setImageBitmap(getBitmapFromLink(imageURL))
        customMarkerView.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
        customMarkerView.layout(0, 0, customMarkerView.measuredWidth, customMarkerView.measuredHeight);
        customMarkerView.buildDrawingCache()
        val returnedBitmap = Bitmap.createBitmap(
                customMarkerView.measuredWidth, customMarkerView.measuredHeight,
                Bitmap.Config.ARGB_8888
        )
        val canvas = Canvas(returnedBitmap)
        canvas.drawColor(Color.WHITE, PorterDuff.Mode.SRC_IN)
        val drawable = customMarkerView.background
        drawable?.draw(canvas)
        customMarkerView.draw(canvas)
        return returnedBitmap
    }

    private fun getImageUrl(image: ImageView, url: String) {
        try {
            Picasso.get().load(url).transform(CircleTransform()).into(image)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

}