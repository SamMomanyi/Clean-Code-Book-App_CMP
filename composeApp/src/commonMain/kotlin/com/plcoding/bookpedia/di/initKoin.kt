package com.plcoding.bookpedia.di

import org.koin.core.KoinApplication
import org.koin.core.context.startKoin
import org.koin.dsl.KoinAppDeclaration

//to call initKoin we need to have each call per application platform start
fun initKoin(
    config : KoinAppDeclaration? = null
){
    startKoin{
        config?.invoke(this)
        modules(sharedModule, platformModule)
    }
}