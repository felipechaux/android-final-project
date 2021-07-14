package com.example.yourfarmerapp.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.yourfarmerapp.databinding.FragmentIntroTwoBinding

class IntroTwoFragment : Fragment() {

    private var _binding: FragmentIntroTwoBinding? = null
    private val binding get() = _binding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentIntroTwoBinding.inflate(inflater, container, false)
        val view = binding?.root
        return view
    }
}