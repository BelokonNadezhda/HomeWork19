package com.example.myapplication19

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.myapplication19.databinding.ActivityDetailsBinding
import com.example.myapplication19.databinding.ActivityMainBinding

class DetailsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)

        setFilmsDetails()
    }

    private fun setFilmsDetails() {
        //Получаем наш фильм из переданного бандла
        val film = intent.extras?.get("film") as Film
        lateinit var binding: ActivityDetailsBinding
        //Инициализируем объект
        binding = ActivityDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.detailsToolbar.title  =film.title
        binding.detailsPoster.setImageResource(film.poster)
        binding.detailsDescription.text = film.description
        //Устанавливаем заголовок
        //details_toolbar.title = film.title
        //Устанавливаем картинку
        //details_poster.setImageResource(film.poster)
        //Устанавливаем описание
        //details_description.text = film.description
    }
}