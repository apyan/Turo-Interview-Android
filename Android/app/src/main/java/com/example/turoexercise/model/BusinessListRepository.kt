package com.example.turoexercise.model

import com.example.turoexercise.model.response.BusinessListResponse

class BusinessListRepository(
    private val businessListService: BusinessListService = BusinessListService()
) {
    suspend fun loadBusinessListInfo(searchTerm: String, location: String): BusinessListResponse {
        return businessListService.loadBusinessListInfo(
            searchTerm = searchTerm,
            location = location
        )
    }
}