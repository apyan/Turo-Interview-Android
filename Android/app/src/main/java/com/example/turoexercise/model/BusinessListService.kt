package com.example.turoexercise.model

import com.example.turoexercise.core.Constant
import com.example.turoexercise.model.response.BusinessListResponse
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query


class BusinessListService {

    private var businessListApi: BusinessListApi

    init {
        val authClient = OkHttpClient.Builder().addInterceptor { chain ->
            val request = chain.request().newBuilder()
                .addHeader("Authorization", "Bearer ${Constant.API_KEY}").build()
            chain.proceed(request)
        }.build()

        val retrofit = Retrofit.Builder()
            .baseUrl(Constant.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(authClient)
            .build()

        businessListApi = retrofit.create(BusinessListApi::class.java)
    }

    suspend fun loadBusinessListInfo(searchTerm: String, location: String): BusinessListResponse {
        android.util.Log.d("ERROR-6","Reached: " + searchTerm + " | " + location)
        return businessListApi.loadBusinessListInfo(
            term = searchTerm,
            location = location
        )
    }

    interface BusinessListApi {
        @GET("businesses/search")
        suspend fun loadBusinessListInfo(
            @Query("term") term: String,
            @Query("location") location: String,
        ): BusinessListResponse
    }
}