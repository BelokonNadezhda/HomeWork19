package com.example.myapplication19.data

import android.content.ContentValues
import android.database.Cursor
import androidx.lifecycle.LiveData
import com.example.myapplication19.Film
import com.example.myapplication19.data.dao.FilmDao
import java.util.concurrent.Executors

class MainRepository(private val filmDao: FilmDao) {

    fun putToDb(films: List<Film>) {
        //Запросы в БД должны быть в отдельном потоке
        Executors.newSingleThreadExecutor().execute {
            filmDao.insertAll(films)
        }
    }

   //fun getAllFromDB(): List<Film> {
   fun getAllFromDB(): LiveData<List<Film>> = filmDao.getCachedFilms()

}