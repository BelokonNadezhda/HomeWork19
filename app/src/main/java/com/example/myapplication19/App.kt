package com.example.myapplication19

import android.app.Application
import com.example.myapplication19.di.AppComponent
import com.example.myapplication19.di.DaggerAppComponent

class App : Application() {
    lateinit var dagger: AppComponent

    override fun onCreate() {
        /* super.onCreate()
        startKoin {
            //Прикрепляем контекст
            androidContext(this@App)
            //(Опционально) подключаем зависимость
            androidLogger()
            //Инициализируем модули
            modules(listOf(DI.mainModule))
            */
        super.onCreate()
        instance = this
        //Создаем компонент
        dagger = DaggerAppComponent.create()
    }

    companion object {
        lateinit var instance: App
             private set
    }
}
