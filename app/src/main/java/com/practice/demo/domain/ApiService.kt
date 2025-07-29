package com.practice.demo.domain

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("api/") // Base URL will be "https://randomuser.me/"
    suspend fun getUsers(
        @Query("results") count: Int,
        @Query("seed") seed: String? = null,
        @Query("page") page: Int? = null,
        @Query("gender") gender: String? = null

    ): Response<UserResponse> // Using Response wrapper

    @GET("api/")
    suspend fun getSpecificNumberOfUsers(
        @Query("results") count: Int = 20
    ): Response<UserResponse>
}