package com.example.myapplication19.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.myapplication19.App
import com.example.myapplication19.Film
import com.example.myapplication19.domain.Interactor
import org.koin.core.KoinComponent
import org.koin.core.inject
import javax.inject.Inject

class HomeFragmentViewModel : ViewModel(), KoinComponent {
    val filmsListLiveData:  MutableLiveData<List<Film>> = MutableLiveData()
    //Инициализируем интерактор
    //private var interactor: Interactor = App.instance.interactor
    //Инициализируем интерактор
    //Инициализируем интерактор
    @Inject
    lateinit var interactor: Interactor

    // private val interactor: Interactor by inject()

    init {
        App.instance.dagger.inject(this)
        interactor.getFilmsFromApi(1, object : ApiCallback {
            override fun onSuccess(films: List<Film>) {
                filmsListLiveData.postValue(films)
            }

            override fun onFailure() {
            }
        })
    }

    interface ApiCallback {
        fun onSuccess(films: List<Film>)
        fun onFailure()
    }
}