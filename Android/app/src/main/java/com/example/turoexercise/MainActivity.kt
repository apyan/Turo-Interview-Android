package com.example.turoexercise

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.turoexercise.model.response.Business
import com.example.turoexercise.ui.screen.BusinessListScreen
import com.example.turoexercise.ui.theme.TuroExerciseTheme
import com.example.turoexercise.viewmodel.BusinessListViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TuroExerciseTheme {
                val viewModel: BusinessListViewModel = viewModel()
                val businessListing = remember { mutableStateListOf<Business>() }

                viewModel.toastLoading = {
                    Toast.makeText(this, getText(R.string.toast_loading), Toast.LENGTH_SHORT).show()
                }

                viewModel.toastMessageEmpty = {
                    Toast.makeText(this, getText(R.string.toast_search_empty), Toast.LENGTH_SHORT).show()
                }

                viewModel.toastConnectionIssue = {
                    Toast.makeText(this, getText(R.string.toast_no_connection), Toast.LENGTH_SHORT).show()
                }

                BusinessListScreen(
                    context = this,
                    businessListViewModel = viewModel,
                    businessList = businessListing,
                    clearList = {
                        businessListing.clear()
                    },
                    startSearch = {
                        if (viewModel.searchTerm.isNotEmpty() && viewModel.locationTerm.isNotEmpty()) {
                            viewModel.launchBusinessSearch(this, businessListing)
                        } else {
                            Toast.makeText(this, getText(R.string.toast_term_empty), Toast.LENGTH_SHORT).show()
                        }
                    }
                )
            }
        }
    }
}