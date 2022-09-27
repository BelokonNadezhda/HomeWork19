package com.example.myapplication19.domain

import androidx.lifecycle.LiveData
import com.example.myapplication19.Film
import com.example.myapplication19.data.ApiConstants
import com.example.myapplication19.data.Entity.TmdbResults
import com.example.myapplication19.data.MainRepository
import com.example.myapplication19.data.PreferenceProvider
import com.example.myapplication19.data.TmdbApi
import com.example.myapplication19.utils.Converter
import com.example.myapplication19.viewmodel.HomeFragmentViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch


class Interactor(private val repo: MainRepository, private val retrofitService: TmdbApi, private val preferences: PreferenceProvider) {
    //В конструктор мы будм передавать коллбэк из вьюмоделе, чтобы реагировать на то, когда фильмы будут получены
    //и страницу, котороую нужно загрузить (это для пагинации)

    val scope: CoroutineScope = CoroutineScope(Dispatchers.IO)
    var progressBarState = Channel<Boolean>(Channel.CONFLATED)

   /* fun getFilmsFromApi(page: Int, callback: HomeFragmentViewModel.ApiCallback) {

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
                callback.onSuccess()
            }

            override fun onFailure(call: retrofit2.Call<TmdbResults>, t: Throwable) {
                //В случае провала вызываем другой метод коллбека
                callback.onFailure()
            }
        })


    }
    */

    fun getFilmsFromApi(page: Int) {
        //Показываем ProgressBar
        scope.launch {
            progressBarState.send(true)
        }
        //Метод getDefaultCategoryFromPreferences() будет получать при каждом запросе нужный нам список фильмов
        retrofitService.getFilms(getDefaultCategoryFromPreferences(), 1).enqueue(object : retrofit2.Callback<TmdbResults>  {
            override fun onResponse(call: retrofit2.Call<TmdbResults>, response: retrofit2.Response<TmdbResults>) {
                val list = Converter.convertApiListToDTOList(response.body()?.tmdbFilmItems)
                //Кладем фильмы в бд
                //В случае успешного ответа кладем фильмы в БД и выключаем ProgressBar
                scope.launch {
                    repo.putToDb(list)
                    progressBarState.send(false)
                }
            }

            override fun onFailure(call: retrofit2.Call<TmdbResults>, t: Throwable) {
                //В случае провала выключаем ProgressBar
                scope.launch {
                    progressBarState.send(false)
                }
            }
        })
    }

    //Метод для сохранения настроек
    fun saveDefaultCategoryToPreferences(category: String) {

        preferences.saveDefaultCategory(category)
    }
    //Метод для получения настроек
    fun getDefaultCategoryFromPreferences() = preferences.getDefaultCategory()


    //fun getFilmsFromDB(): LiveData<List<Film>> = repo.getAllFromDB()
    fun getFilmsFromDB(): Flow<List<Film>> = repo.getAllFromDB()
}