package com.plcoding.bookpedia

import androidx.compose.runtime.remember
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import com.plcoding.bookpedia.di.initKoin
import io.ktor.client.engine.okhttp.OkHttp
import org.koin.core.context.startKoin

//for desktop to start koin is quite simple , we just call it before drawing our window

fun main()  {
    initKoin()
    application {
    Window(
        onCloseRequest = ::exitApplication,
        title = "CMP-Bookpedia",
    ) {
        App(
            engine = remember {
                OkHttp.create()
            }
        )
    }
}}