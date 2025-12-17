package com.plcoding.bookpedia

import androidx.compose.runtime.remember
import platform.UIKit.UIViewController
import androidx.compose.ui.window.ComposeUIViewController
import com.plcoding.bookpedia.di.initKoin
import io.ktor.client.engine.darwin.Darwin

//here to start koin we just call the configure function
fun MainViewController() = ComposeUIViewController(
    configure = {
        initKoin()
    }
) {
    App(
) }