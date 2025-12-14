package com.plcoding.bookpedia.book.presentation.book_list

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Surface
import androidx.compose.material3.TabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import cmp_bookpedia.composeapp.generated.resources.Res
import cmp_bookpedia.composeapp.generated.resources.favorites
import cmp_bookpedia.composeapp.generated.resources.search_results
import com.plcoding.bookpedia.book.domain.Book
import com.plcoding.bookpedia.book.presentation.book_list.components.BookSearchBar
import com.plcoding.bookpedia.core.presentation.DarkBlue
import com.plcoding.bookpedia.core.presentation.DesertWhite
import com.plcoding.bookpedia.core.presentation.SandYellow
import org.jetbrains.compose.resources.stringResource
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
    onAction :(BookListCommand) -> Unit,
    ){
    //we want to hide keyboard on imeSearch
    val keyboardController = LocalSoftwareKeyboardController.current
    //indicates that the horizontal pager has 2 states
    val pagerState =  rememberPagerState { 2 }

    Column(
        modifier = Modifier
            .fillMaxSize()
            //color from our resources
            .background(
                DarkBlue
            )
            //leave some space for the status bark
            .statusBarsPadding(),
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        BookSearchBar(
            searchQuery = state.searchQuery,
            //send the typed text to viewModel, viewModel updates it then shows it back using state.searchQuesry
            onSearchQueryChange = {
                onAction(BookListCommand.OnSearchQuesryChange(it))
            },
            onImeSearch = {
                keyboardController?.hide()
            },
            modifier = Modifier
                //for CMP apps we should think about other platforms e.g desktop and say the width should never be more than 400
                .widthIn(max = 400.dp)
                .fillMaxWidth()
                .padding(16.dp)
        )
        //after the search bar we have some space then a Surface for the books which fills the whole size
        Surface(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth(),
            color  = DesertWhite,
            shape = RoundedCornerShape(
                topStart = 32.dp,
                topEnd  = 32.dp
            )
        ){
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ){
                TabRow(
                    selectedTabIndex = state.selectedTabIndex,
                    modifier = Modifier
                        .padding(vertical  = 12.dp)
                        .widthIn(max = 700.dp)
                        .fillMaxWidth(),
                    containerColor = DesertWhite,
                    contentColor = SandYellow,
                    //the tab indicator thingy
                    indicator = { tabPositions ->
                        TabRowDefaults.SecondaryIndicator(
                            color = SandYellow,
                            modifier = Modifier
                                .tabIndicatorOffset(tabPositions[state.selectedTabIndex])
                        )
                    }
                ){
                    Tab(
                        selected = state.selectedTabIndex == 0,
                        onClick = {
                            onAction(BookListCommand.OnTabSelection(0))
                        },
                        modifier = Modifier.weight(1f),
                        selectedContentColor = SandYellow,
                        unselectedContentColor = Color.Black.copy(alpha = 0.5f)
                    ) {
                        Text(
                            text = stringResource(Res.string.search_results),
                            modifier = Modifier
                            .padding(vertical = 12.dp)

                        )
                    }

                    Tab(
                        selected = state.selectedTabIndex == 1,
                        onClick = {
                            onAction(BookListCommand.OnTabSelection(1))
                        },
                        modifier = Modifier.weight(1f),
                        selectedContentColor = SandYellow,
                        unselectedContentColor = Color.Black.copy(alpha = 0.5f)
                    ) {
                        Text(
                            text = stringResource(Res.string.favorites),
                            modifier = Modifier
                                .padding(vertical = 12.dp)

                        )
                    }
                }


            }
        }
        Spacer (modifier = Modifier.height(4.dp))
        //to be able to have swipe functionality we need more to the tabrow since the tabrow is just a skeleton tab row
        //we will need a horizontalPager
        HorizontalPager(
            state = pagerState,
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
        ){ pageIndex ->
            //to center the horizontal indicator
            Box(
                modifier = Modifier
                    .fillMaxSize(),
                contentAlignment = Alignment.Center
            )
            when(pageIndex){
                0 -> {
                    if(state.isLoading){
                        CircularProgressIndicator()
                    } else {
                        //different search result
                        when(
                            state.errorMessage != null -> {

                        }

                        ){

                        }
                    }
                }

                1-> {

                }
            }
        }
    }
}