package com.plcoding.bookpedia

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import com.plcoding.bookpedia.app.App
import com.plcoding.bookpedia.di.initKoin

//for desktop to start koin is quite simple , we just call it before drawing our window

fun main()  {
    initKoin()
    application {
    Window(
        onCloseRequest = ::exitApplication,
        title = "CMP-Bookpedia",
    ) {
        App()
    }
}}