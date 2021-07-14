package com.example.yourfarmerapp.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.yourfarmerapp.databinding.FragmentIntroThreeBinding

class IntroThreeFragment : Fragment() {

    private var _binding: FragmentIntroThreeBinding? = null
    private val binding get() = _binding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentIntroThreeBinding.inflate(inflater, container, false)
        val view = binding?.root
        return view
    }
}