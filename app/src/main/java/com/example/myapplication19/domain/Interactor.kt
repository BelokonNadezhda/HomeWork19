package com.example.myapplication19.domain

import com.example.myapplication19.Film
import com.example.myapplication19.data.ApiConstants
import com.example.myapplication19.data.Entity.TmdbResults
import com.example.myapplication19.data.MainRepository
import com.example.myapplication19.data.PreferenceProvider
import com.example.myapplication19.data.TmdbApi
import com.example.myapplication19.utils.Converter
import com.example.myapplication19.viewmodel.HomeFragmentViewModel




class Interactor(private val repo: MainRepository, private val retrofitService: TmdbApi, private val preferences: PreferenceProvider) {
    //В конструктор мы будм передавать коллбэк из вьюмоделе, чтобы реагировать на то, когда фильмы будут получены
    //и страницу, котороую нужно загрузить (это для пагинации)
    fun getFilmsFromApi(page: Int, callback: HomeFragmentViewModel.ApiCallback) {

        println("!!!getDefaultCategoryFromPreferences()="+getDefaultCategoryFromPreferences())
        retrofitService.getFilms(getDefaultCategoryFromPreferences(),1).enqueue(object :
       // retrofitService.getFilms(ApiConstants.TOP_250,1).enqueue(object :
            retrofit2.Callback<TmdbResults> {
            override fun onResponse(call: retrofit2.Call<TmdbResults>, response: retrofit2.Response<TmdbResults>) {
                //При успехе мы вызываем метод, передаем onSuccess и в этот коллбэк список фильмов
                val list = Converter.convertApiListToDTOList(response.body()?.tmdbFilmItems)
                //Кладем фильмы в бд
                list.forEach {
                    //repo.putToDb(film = it)
                    repo.putToDb(list)
                }
                callback.onSuccess(list)
            }

            override fun onFailure(call: retrofit2.Call<TmdbResults>, t: Throwable) {
                //В случае провала вызываем другой метод коллбека
                callback.onFailure()
            }
        })


    }
    //Метод для сохранения настроек
    fun saveDefaultCategoryToPreferences(category: String) {

        preferences.saveDefaultCategory(category)
    }
    //Метод для получения настроек
    fun getDefaultCategoryFromPreferences() = preferences.getDefaultCategory()

    fun getFilmsFromDB(): List<Film> = repo.getAllFromDB()

}