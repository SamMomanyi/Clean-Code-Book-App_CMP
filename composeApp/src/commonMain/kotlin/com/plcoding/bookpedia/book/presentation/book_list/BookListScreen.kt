package com.plcoding.bookpedia.book.presentation.book_list

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.ViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.plcoding.bookpedia.book.domain.Book
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun BookListScreenRoot(
    viewModel: BookListViewModel = koinViewModel(),
    onBookCLick: (Book) -> Unit,
    modifier : Modifier = Modifier
){
    val state by viewModel.state.collectAsStateWithLifecycle()

    BookListScreen(
        state = state,
        //we did not bubble all the action like this onAction = viewModel::onAction
        //we want to intercept some of those actions due to navigation purposes
        onAction = {
            action ->
            when(action){
                is BookListCommand.OnBooKClick -> onBookCLick(action.book)
                else -> Unit
            }
            viewModel.onAction(action)
        }

    )
}
@Composable
fun BookListScreen(
    state: BookListState,
    onAction :(BookListCommand) -> Unit, ){

}