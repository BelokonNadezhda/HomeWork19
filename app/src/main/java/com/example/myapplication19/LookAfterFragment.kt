package com.example.myapplication19

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.myapplication19.databinding.FragmentHomeBinding
import com.example.myapplication19.databinding.FragmentLookafterBinding

class LookAfterFragment : Fragment() {

    private lateinit var binding: FragmentLookafterBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        AnimationHelper.performFragmentCircularRevealAnimation(binding.watchLaterFragmentRoot, requireActivity(), 3)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        //Инициализируем объект
        binding = FragmentLookafterBinding.inflate(inflater, container, false)
        return binding.root
    }


}