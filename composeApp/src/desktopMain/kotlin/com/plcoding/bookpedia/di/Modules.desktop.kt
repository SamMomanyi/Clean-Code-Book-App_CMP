package com.plcoding.bookpedia.di

import com.plcoding.bookpedia.book.data.database.DatabaseFactory
import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.engine.okhttp.OkHttp
import org.koin.dsl.module
import org.koin.core.module.Module

actual val platformModule: Module
    get() = module {
        single<HttpClientEngine> {
            OkHttp.create()
        }
        single<DatabaseFactory>{
            DatabaseFactory()
        }
    }