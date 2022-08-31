package com.example.myapplication19.utils

import com.example.myapplication19.Film
import com.example.myapplication19.data.Entity.Item
import com.example.myapplication19.data.Entity.TmdbFilm1

object Converter {
    fun convertApiListToDTOList(list: List<Item>?): List<Film> {
        val result = mutableListOf<Film>()

        list?.forEach {
            result.add(Film(
                title = it.nameRu,
                idfilm = it.kinopoiskId,
                poster = it.posterUrl,
                description = it.nameEn,
                //rating = it.items[0].kinopoiskId,
                rating = (0..10).random().toDouble(),
                isInFavorites = false
            ))
        }
        return result
    }
}