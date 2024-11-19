package com.example.turoexercise.ui.screen

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.CachePolicy
import coil.request.ImageRequest
import com.example.turoexercise.R
import com.example.turoexercise.model.response.Business
import com.example.turoexercise.viewmodel.BusinessListViewModel

@Composable
fun BusinessListScreen(
    context: Context? = null,
    businessListViewModel: BusinessListViewModel? = null,
    businessList: List<Business> = emptyList(),
    clearList: () -> Unit = {},
    startSearch: () -> Unit = {},
) {
    Scaffold(
        topBar = {
            Column {
                Row (
                    horizontalArrangement = Arrangement.Center,
                    modifier = Modifier
                        .background(color = Color.White)
                        .fillMaxWidth()
                ) {
                    var searchText by remember { mutableStateOf(TextFieldValue("")) }
                    TextField(
                        modifier = Modifier
                            .padding(5.dp)
                            .width(250.dp),
                        value = searchText,
                        onValueChange = {
                            searchText = it
                            businessListViewModel?.searchTerm = it.text
                        },
                        placeholder = {
                            Text(text = stringResource(id = R.string.placeholder_search))
                        },
                    )

                    Button(
                        modifier = Modifier.width(100.dp),
                        onClick = { clearList.invoke() }
                    ) {
                        Text(
                            text = stringResource(id = R.string.clear),
                            textAlign = TextAlign.Center,
                            style = MaterialTheme.typography.bodySmall,
                            color = Color.White
                        )
                    }
                }

                Row (
                    horizontalArrangement = Arrangement.Center,
                    modifier = Modifier
                        .background(color = Color.White)
                        .fillMaxWidth()
                ) {
                    var locationText by remember { mutableStateOf(TextFieldValue("")) }
                    TextField(
                        modifier = Modifier
                            .padding(5.dp)
                            .width(250.dp),
                        value = locationText,
                        onValueChange = {
                            locationText = it
                            businessListViewModel?.locationTerm = it.text
                        },
                        placeholder = {
                            Text(text = stringResource(id = R.string.placeholder_location))
                        },
                    )

                    Button(
                        modifier = Modifier.width(100.dp),
                        onClick = { startSearch.invoke() }
                    ) {
                        Text(
                            text = stringResource(id = R.string.search),
                            textAlign = TextAlign.Center,
                            style = MaterialTheme.typography.bodySmall,
                            color = Color.White
                        )
                    }
                }
            }
        }
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(2.dp),
        ) {
            items(businessList) { business ->
                BusinessInfoItem(
                    context = context,
                    business = business
                )
            }
        }
    }
}

@Composable
fun BusinessInfoItem(
    context: Context?,
    business: Business
) {
    Card(
        modifier = Modifier
            .padding(5.dp)
            .fillMaxWidth()
            .wrapContentHeight()
            .background(color = Color.LightGray),
        shape = MaterialTheme.shapes.medium,
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
        ) {
            val imageRequest = context?.let {
                ImageRequest.Builder(it)
                    .data(business.imageUrl)
                    .memoryCacheKey(business.imageUrl)
                    .diskCacheKey(business.imageUrl)
                    .placeholder(R.drawable.ic_launcher_background)
                    .error(R.drawable.ic_launcher_background)
                    .fallback(R.drawable.ic_launcher_background)
                    .diskCachePolicy(CachePolicy.ENABLED)
                    .memoryCachePolicy(CachePolicy.ENABLED)
                    .build()
            }

            AsyncImage(
                model = imageRequest,
                contentDescription = "Business Image",
                contentScale = ContentScale.Fit,
                modifier = Modifier
                    .size(75.dp)
                    .padding(10.dp),
            )

            Column(Modifier.padding(5.dp)) {
                business.name?.let {
                    Text(
                        modifier = Modifier
                            .padding(horizontal = 2.dp),
                        text = it,
                        textAlign = TextAlign.Center,
                        style = MaterialTheme.typography.bodyLarge,
                        color = Color.Black
                    )
                }
                business.displayPhone?.let {
                    Text(
                        modifier = Modifier
                            .padding(horizontal = 2.dp),
                        text = it,
                        textAlign = TextAlign.Center,
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color.Black
                    )
                }
                business.location?.displayAddress?.forEach {
                    Text(
                        modifier = Modifier
                            .padding(horizontal = 2.dp),
                        text = it,
                        textAlign = TextAlign.Center,
                        style = MaterialTheme.typography.bodySmall,
                        color = Color.Black
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun BusinessListScreenPreview() {
    BusinessListScreen()
}