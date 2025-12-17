package com.plcoding.bookpedia.di

import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.engine.okhttp.OkHttp
import okhttp3.OkHttpClient
import org.koin.core.module.Module
import org.koin.dsl.module

actual val platformModule: Module
    //we must also specify that it is of type HttpClientEngine
    get() = module {
        single<HttpClientEngine> {
           OkHttp.create()
        }
    }