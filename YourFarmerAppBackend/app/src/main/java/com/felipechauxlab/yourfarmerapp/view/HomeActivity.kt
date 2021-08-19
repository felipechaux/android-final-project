package com.felipechauxlab.yourfarmerapp.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.yourfarmerapp.databinding.ActivityHomeBinding

class HomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

    }

}
