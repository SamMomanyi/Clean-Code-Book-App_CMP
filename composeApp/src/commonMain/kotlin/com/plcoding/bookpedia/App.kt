package com.plcoding.bookpedia

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import com.plcoding.bookpedia.book.data.Network.KtorRemoteBookDataSource
import com.plcoding.bookpedia.book.data.repository.DefaultBookRepository
import com.plcoding.bookpedia.book.presentation.book_list.BookListScreenRoot
import com.plcoding.bookpedia.book.presentation.book_list.BookListViewModel
import com.plcoding.bookpedia.core.data.HttpClientFactory
import io.ktor.client.engine.HttpClientEngine
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
@Preview
//we pass the specific engine type from wherever we call our specific platform App function
//we remove the engine after creating DI's
fun App() {

    BookListScreenRoot(
        viewModel = remember { BookListViewModel(
            bookRepository = DefaultBookRepository(
                remoteBookDataSource = KtorRemoteBookDataSource(
                    httpClient = HttpClientFactory.create(
                        engine
                    )
                )
            )
        ) },
        //handle navigation
        onBookCLick = {

        }
    )
}