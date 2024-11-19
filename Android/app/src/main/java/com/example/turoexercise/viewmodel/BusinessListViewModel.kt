package com.example.turoexercise.viewmodel

import android.content.Context
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.turoexercise.core.Constant
import com.example.turoexercise.model.BusinessListRepository
import com.example.turoexercise.model.response.Business
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class BusinessListViewModel(
    private val businessListRepository: BusinessListRepository = BusinessListRepository()
) : ViewModel() {

    var searchTerm: String = ""
    var locationTerm: String = ""

    var toastLoading: (() -> Unit)? = null
    var toastMessageEmpty: (() -> Unit)? = null
    var toastConnectionIssue: (() -> Unit)? = null

    private val _businessList: MutableState<List<Business>> = mutableStateOf(emptyList())
    val businessList = _businessList.value

    fun launchBusinessSearch(
        context: Context,
        businessListing: SnapshotStateList<Business>
    ) {
        if (Constant.isConnected(context) && !Constant.isAirplaneModeOn(context)) {
            toastLoading?.invoke()
            viewModelScope.launch(Dispatchers.IO) {
                getBusinessListInfo(
                    search = searchTerm,
                    location = locationTerm,
                    businessListing = businessListing
                )
            }
        } else {
            toastConnectionIssue?.invoke()
        }
    }

    private suspend fun getBusinessListInfo(
        search: String,
        location: String,
        businessListing: SnapshotStateList<Business>
    ) {
        try {
            val businessListResult = businessListRepository.loadBusinessListInfo(
                searchTerm = search,
                location = location
            ).businesses
            _businessList.value = businessListResult

            if (businessListResult.isEmpty()) {
                toastMessageEmpty?.invoke()
            }

            preventDuplicateBusinesses(
                businessListing = businessListing,
                addedBusinessList = businessListResult
            )
        } catch (error: Error) {
            _businessList.value = emptyList()
            toastMessageEmpty?.invoke()
        }
    }

    private fun preventDuplicateBusinesses(
        businessListing: SnapshotStateList<Business>,
        addedBusinessList: List<Business>
    ) {
        addedBusinessList.forEach {
            if (!businessListing.contains(it)) {
                businessListing.add(it)
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        toastLoading = null
        toastMessageEmpty = null
        toastConnectionIssue = null
    }
}