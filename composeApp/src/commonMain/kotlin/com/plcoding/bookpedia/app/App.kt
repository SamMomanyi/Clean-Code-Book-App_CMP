package com.plcoding.bookpedia.app

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.ViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.plcoding.bookpedia.book.presentation.SelectedBookViewModel
import com.plcoding.bookpedia.book.presentation.book_detail.BookDetailCommand
import com.plcoding.bookpedia.book.presentation.book_detail.BookDetailScreenRoot
import com.plcoding.bookpedia.book.presentation.book_detail.BookDetailViewModel
import com.plcoding.bookpedia.book.presentation.book_list.BookListScreenRoot
import com.plcoding.bookpedia.book.presentation.book_list.BookListViewModel
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.viewmodel.koinViewModel

@Composable
@Preview
//we pass the specific engine type from wherever we call our specific platform App function
//we remove the engine after creating DI's
//koin will naturally be able to pass the dependencies needed to the viewModel
fun App() {
    //for the navigation
    //first wrap everything inside the materialTheme
    MaterialTheme {
        val navController = rememberNavController()
        NavHost(
            navController  = navController,
            startDestination = Route.BookGraph
        ){
            navigation<Route.BookGraph>(
                startDestination = Route.BookList
            ){
                //we the define the composable screens
                //we pass the id needed on one side to the other
                composable<Route.BookList> {
                    val viewModel = koinViewModel<BookListViewModel>()
                    val selectedBookViewModel = it.sharedKoinViewModel<SelectedBookViewModel>(navController)
                    //if we get back to the BookList we then want to reset selectedBok
                    //this works with a LuanchedEffect

                    LaunchedEffect(true){
                        selectedBookViewModel.onSelectedBook(null)
                    }
                    BookListScreenRoot(
                        viewModel = viewModel,
                        //handle navigation
                        //also update the selected book in the shared viewModel
                        onBookCLick = {
                            book->
                            selectedBookViewModel.onSelectedBook(book)
                            navController.navigate(Route.BookDetail(book.id))
                        }
                    )
                }
                composable<Route.BookDetail> {
                    val selectedBookViewModel = it.sharedKoinViewModel<SelectedBookViewModel>(navController)
                    val selectedBook by selectedBookViewModel.selectedBook.collectAsStateWithLifecycle()
                    val viewModel = koinViewModel<BookDetailViewModel>()

                    LaunchedEffect(selectedBook){
                        selectedBook?.let {
                            viewModel.onAction(BookDetailCommand.OnSelectedBookChange(it))
                        }
                    }
                    BookDetailScreenRoot(
                        viewModel = viewModel,
                        onBackClick = {
                            //get to the previous screen
                            navController.navigateUp()
                        }
                    )
                }
            }
        }

    }

}
//a utility function for the shared viewModel
//he also mentioned something  about back stack entries
//we can copy paste this into any of our projects
@Composable
private inline fun<reified T: ViewModel> NavBackStackEntry.sharedKoinViewModel(
    navController: NavController
): T{
    val navGraphRoute = destination.parent?.route ?: return koinViewModel<T>()
    val parentEntry = remember(this){
        navController.getBackStackEntry(navGraphRoute)
    }
    return koinViewModel (
        viewModelStoreOwner = parentEntry
    )
}