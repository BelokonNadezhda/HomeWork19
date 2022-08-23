package com.example.myapplication19

import androidx.appcompat.app.AppCompatActivity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.myapplication19.databinding.FragmentDetailsBinding
import com.example.myapplication19.databinding.FragmentHomeBinding

class DetailsFragment : Fragment() {

    private lateinit var binding: FragmentDetailsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setFilmsDetails()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        //Инициализируем объект
        binding = FragmentDetailsBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }


    private fun setFilmsDetails() {
        //Получаем наш фильм из переданного бандла
        val film = arguments?.get("film") as Film

        binding.detailsToolbar.title  =film.title
        binding.detailsPoster.setImageResource(film.poster)
        binding.detailsDescription.text = film.description
    }
}