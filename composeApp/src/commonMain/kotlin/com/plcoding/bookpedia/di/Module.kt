package com.plcoding.bookpedia.di

import com.plcoding.bookpedia.book.data.Network.KtorRemoteBookDataSource
import com.plcoding.bookpedia.book.data.Network.RemoteBookDataSource
import com.plcoding.bookpedia.book.data.repository.DefaultBookRepository
import com.plcoding.bookpedia.book.domain.BookRepository
import com.plcoding.bookpedia.book.presentation.book_list.BookListViewModel
import com.plcoding.bookpedia.core.data.HttpClientFactory
import org.koin.core.module.Module
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.bind
import org.koin.dsl.module

//for koin different instances of dependencies are declared differently

//we use the keyword expect for platform specific dependecies
//what's cool : expect works like a interface where we are expected to have dependecies for each module click iosMain, desktopMain,androidMain
expect val platformModule: Module

val sharedModule  = module {
    single { HttpClientFactory.create(get()) }
    //we use singleOf to pass multiple instances of the dependecy
    //ktorRemoteDataSource is the actual dependecy while RemoteBookDataSource is the implementation
    //tip type the class first before the dpuble colons
    singleOf(::KtorRemoteBookDataSource).bind<RemoteBookDataSource>()
    singleOf(::DefaultBookRepository).bind<BookRepository>()

    //providing viewmodels
    viewModelOf(::BookListViewModel)


}