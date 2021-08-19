package com.felipechauxlab.yourfarmerapp.view

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.PorterDuff
import android.os.Bundle
import android.os.StrictMode
import android.view.View
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.example.yourfarmerapp.R
import com.felipechauxlab.yourfarmerapp.restApi.dto.PublishProductDTO
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import java.io.InputStream
import java.net.HttpURLConnection
import java.net.URL


class ProductMapActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private var placeLocation: LatLng? = null
    private var productsMapArr: ArrayList<PublishProductDTO?>?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product_map)
        val mapFragment = supportFragmentManager
                .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
        getExtraProducts()
    }

    private fun getExtraProducts() {
        try {
            val params = intent.extras
            productsMapArr = params?.getParcelableArrayList(getString(R.string.extra_product_map_arr))
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        val policy = StrictMode.ThreadPolicy.Builder().permitAll().build()
        StrictMode.setThreadPolicy(policy)
        placeLocation = LatLng(4.2390239, -74.230111219)

        // MarkerOptions().position(it).title("Marker ").icon(BitmapDescriptorFactory.fromBitmap(getBitmapFromLink("https://res.cloudinary.com/dpyqmhhmi/image/upload/v1627937551/nji6scjofgbff5peup9k.jpg")))
        //MarkerOptions().position(it).title("Marker ").icon(BitmapDescriptorFactory.fromBitmap(getMarkerBitmapFromView("https://res.cloudinary.com/dpyqmhhmi/image/upload/v1627937551/nji6scjofgbff5peup9k.jpg")))

        mMap.moveCamera(CameraUpdateFactory.newLatLng(placeLocation))

        productsMapArr?.let {
            for (product in it) {
                mMap.addMarker(MarkerOptions().position(LatLng(product?.latitude!!.toDouble(), product.longitude!!.toDouble())).title(product.productName)
                        .icon(BitmapDescriptorFactory.fromBitmap(product.productPhoto?.let { p -> getMarkerBitmapFromView(p) })))
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

}