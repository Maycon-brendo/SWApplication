package com.example.swapplication.application

import android.app.Application
import com.example.swapplication.repositorios.BandasRepository


//adicionar ao manifesto dentro da tag application:
//android:name=".application.BandasApplication"

class BandasApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        BandasRepository.initializer()
    }
}