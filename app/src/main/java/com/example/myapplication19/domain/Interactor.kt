package com.example.myapplication19.domain

import com.example.myapplication19.Film
import com.example.myapplication19.data.MainRepository

class Interactor(val repo: MainRepository) {
    fun getFilmsDB(): List<Film> = repo.filmsDataBase

}