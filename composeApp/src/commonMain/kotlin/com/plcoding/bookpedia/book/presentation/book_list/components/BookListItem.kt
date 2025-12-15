package com.plcoding.bookpedia.book.presentation.book_list.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.snapping.SnapPosition
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import cmp_bookpedia.composeapp.generated.resources.Res
import cmp_bookpedia.composeapp.generated.resources.book_error_2
import coil3.compose.rememberAsyncImagePainter
import com.plcoding.bookpedia.book.domain.Book
import com.plcoding.bookpedia.core.presentation.LightBlue
import com.plcoding.bookpedia.core.presentation.SandYellow
import org.jetbrains.compose.resources.painterResource
import kotlin.math.round

@Composable
fun BookListItem(
    book : Book,
    onClick: () -> Unit,
    modifier : Modifier = Modifier
    ){
    //this are for each individual book item
    Surface(
        shape = RoundedCornerShape(32.dp),
        modifier = modifier
            .clickable(
                onClick = onClick),
                color = LightBlue.copy(alpha = 0.2f)

    ){
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
                //a minimum intrisic size enables the components in row to work with a fixed max size to fill in all the items
                .height(IntrinsicSize.Min),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ){
            Box(
                modifier = Modifier
                    .height(100.dp),
                contentAlignment = Alignment.Center
            ){
                //when obtaining results using coil we use the method below
                //if we don't get a result we will show a painter
                var imageLoadResult by remember {
                    mutableStateOf<Result<Painter>?>(null)
                }
                //to get the painter , also coming from coil
                val painter = rememberAsyncImagePainter(
                    model = book.imageUrl,
                    onSuccess = {
                        //sometimes API loads an image but it is still not a successful result so we use
                        if(it.painter.intrinsicSize.width > 1 && it.painter.intrinsicSize.height > 1){
                            Result.success(it.painter)
                        }else{
                            Result.failure(Exception("Invalid image size"))
                        }
                    },
                    onError = {
                        //print error on logs then show error message
                        it.result.throwable.printStackTrace()
                        imageLoadResult = Result.failure(it.result.throwable)
                    }
                )
                when(val result = imageLoadResult){
                    //if we haven't gotten a result yet it means we are still painting
                    null -> CircularProgressIndicator()
                    else -> {
                        Image(
                        painter = if(result.isSuccess) painter
                        else {
                            painterResource(
                                Res.drawable.book_error_2
                            )
                        },
                            contentDescription = book.title,
                            contentScale = if(result.isSuccess) {
                                //if doesn't fit it will be cropped
                                ContentScale.Crop
                            }
                            //dealing with book error
                            else {
                                ContentScale.Fit
                            },
                            modifier = Modifier
                                //give the whole thing a modifier, make each image have atleast the same aspect Ration and Fill Height first before width since each height book image is longer than it is wider
                                .aspectRatio(
                                    ratio = 0.65f,
                                    matchHeightConstraintsFirst = true
                                )
                        )
                    }
                }
            }
            Column(
                modifier = Modifier
                    .fillMaxHeight()
                    .weight(1f),
                verticalArrangement = Arrangement.Center
            ){
                Text(
                    text = book.title,
                    style = MaterialTheme.typography.titleMedium,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
                book.authors.firstOrNull()?.let {
                    authorName ->
                    Text(
                        text = authorName,
                        style = MaterialTheme.typography.bodyLarge,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }
                book.averageRating?.let{
                    rating ->
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ){
                        Text(
                            //since ratings have large values
                            text = "${round(rating*10)/ 10.0}",
                            style = MaterialTheme.typography.bodyMedium
                        )
                        Icon(
                            imageVector = Icons.Default.Star,
                            contentDescription = null,
                            tint = SandYellow
                        )
                    }
                }
            }
            //icon arrow
            Icon(
                imageVector =  Icons.AutoMirrored.Filled.KeyboardArrowRight,
                contentDescription = null,
                modifier = Modifier
                    .size(36.dp)
            )
        }
    }
}