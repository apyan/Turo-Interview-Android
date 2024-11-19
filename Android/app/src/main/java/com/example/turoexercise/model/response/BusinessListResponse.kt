package com.example.turoexercise.model.response

import com.google.gson.annotations.SerializedName

data class BusinessListResponse(
    @SerializedName("businesses")
    val businesses: List<Business>
)

data class Business(
    @SerializedName("id")
    val id: String? = null,
    @SerializedName("name")
    val name: String? = null,
    @SerializedName("image_url")
    val imageUrl: String? = null,
    @SerializedName("display_phone")
    val displayPhone: String? = null,
    @SerializedName("location")
    val location: Location? = null,
)

data class Location(
    @SerializedName("display_address")
    val displayAddress: List<String>? = null
)