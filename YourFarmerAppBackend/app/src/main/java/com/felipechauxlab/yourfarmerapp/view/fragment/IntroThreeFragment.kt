package com.felipechauxlab.yourfarmerapp.view.fragment

import android.media.MediaPlayer
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.yourfarmerapp.databinding.FragmentIntroThreeBinding
import com.felipechauxlab.yourfarmerapp.view.MainViewModel

class IntroThreeFragment : Fragment() {

    private val mainViewModel: MainViewModel by activityViewModels()
    private var _binding: FragmentIntroThreeBinding? = null
    private val binding get() = _binding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentIntroThreeBinding.inflate(inflater, container, false)
        val view = binding?.root
        addButtonsListener()
        mainViewModel.stopWelcomeSound()
        mainViewModel.playMapSound()
        return view
    }

    private fun addButtonsListener() {
        binding?.imgMap?.setOnClickListener {
            mainViewModel.stopMapSound()
        }
    }
}