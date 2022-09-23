package com.example.myapplication19.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.myapplication19.App
import com.example.myapplication19.Film
import com.example.myapplication19.domain.Interactor
import org.koin.core.KoinComponent
import org.koin.core.inject
import java.util.concurrent.Executors
import javax.inject.Inject

class HomeFragmentViewModel : ViewModel(), KoinComponent {
    //val filmsListLiveData:  MutableLiveData<List<Film>> = MutableLiveData()
    val filmsListLiveData: LiveData<List<Film>>
    val showProgressBar: MutableLiveData<Boolean> = MutableLiveData()
    //Инициализируем интерактор
    @Inject
    lateinit var interactor: Interactor

    init {
        App.instance.dagger.inject(this)
        filmsListLiveData = interactor.getFilmsFromDB()
        getFilms()
    }

    fun getFilms() {
        showProgressBar.postValue(true)
        interactor.getFilmsFromApi(1, object : ApiCallback {
            override fun onSuccess() {
                //filmsListLiveData.postValue(films)
                showProgressBar.postValue(false)
            }

            override fun onFailure() {
                showProgressBar.postValue(false)
                //filmsListLiveData.postValue(interactor.getFilmsFromDB())
                //Executors.newSingleThreadExecutor().execute {
                   // filmsListLiveData.postValue(interactor.getFilmsFromDB())
                }
            })
    }

    interface ApiCallback {
        //fun onSuccess(films: List<Film>)
        fun onSuccess()
        fun onFailure()
    }
}