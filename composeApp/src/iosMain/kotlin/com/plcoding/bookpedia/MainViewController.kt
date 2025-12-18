package com.plcoding.bookpedia

import androidx.compose.ui.window.ComposeUIViewController
import com.plcoding.bookpedia.app.App
import com.plcoding.bookpedia.di.initKoin

//here to start koin we just call the configure function
fun MainViewController() = ComposeUIViewController(
    configure = {
        initKoin()
    }
) {
    App(
) }