package com.example.myapplication19.domain

import android.telecom.Call
import com.bumptech.glide.load.model.ByteArrayLoader
import com.example.myapplication19.API
import com.example.myapplication19.data.Entity.TmdbResults
import com.example.myapplication19.data.MainRepository
import com.example.myapplication19.data.TmdbApi
import com.example.myapplication19.utils.Converter
import com.example.myapplication19.viewmodel.HomeFragmentViewModel




class Interactor(private val repo: MainRepository, private val retrofitService: TmdbApi) {
    //В конструктор мы будм передавать коллбэк из вьюмоделе, чтобы реагировать на то, когда фильмы будут получены
    //и страницу, котороую нужно загрузить (это для пагинации)
    fun getFilmsFromApi(page: Int, callback: HomeFragmentViewModel.ApiCallback) {


        retrofitService.getFilms(2022,"JANUARY").enqueue(object :
            retrofit2.Callback<TmdbResults> {
            override fun onResponse(call: retrofit2.Call<TmdbResults>, response: retrofit2.Response<TmdbResults>) {
                //При успехе мы вызываем метод передаем onSuccess и в этот коллбэк список фильмов
                //При успехе мы вызываем метод передаем onSuccess и в этот коллбэк список фильмов
                val list = Converter.convertApiListToDTOList(response.body()?.tmdbFilms)
            //println("!!!"+list.toString())

                callback.onSuccess(list)
            }

            override fun onFailure(call: retrofit2.Call<TmdbResults>, t: Throwable) {
                //В случае провала вызываем другой метод коллбека
                callback.onFailure()
            }
        })
    }
}