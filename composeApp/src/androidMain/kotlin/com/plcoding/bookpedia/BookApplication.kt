package com.plcoding.bookpedia

import android.app.Application
import com.plcoding.bookpedia.di.initKoin
import org.koin.android.ext.koin.androidContext

class BookApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        //if we had other dependecies we wanted to use just for android we could add them in initKoin but since we don't
        //we also need to instruct koin to be aware of android context
        //then finally register the application inside our android manifest
        initKoin {
            androidContext(
                this@BookApplication
            )
        }
    }
}