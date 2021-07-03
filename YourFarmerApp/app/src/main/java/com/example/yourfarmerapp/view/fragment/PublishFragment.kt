package com.example.yourfarmerapp.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.LinearInterpolator
import androidx.fragment.app.Fragment
import com.example.yourfarmerapp.databinding.FragmentPublishBinding

class PublishFragment : Fragment() {

    private var _binding: FragmentPublishBinding? = null
    private val binding get() = _binding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentPublishBinding.inflate(inflater, container, false)
        val view = binding?.root

        binding?.text?.textSize = 24f;
        // or animate
        binding?.text?.pivotX = 0f;
        binding?.text?.animate()?.scaleX(1.5f)
                ?.setInterpolator(LinearInterpolator())
                ?.setDuration(500)
                ?.start();

        return view
    }
}