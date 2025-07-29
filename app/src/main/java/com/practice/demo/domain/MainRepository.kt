package com.practice.demo.domain

import retrofit2.Response
import javax.inject.Inject

class MainRepository @Inject constructor(private val apiService: ApiService) {

    suspend fun getUsers(
        count: Int,
        seed: String? = null,
        page: Int? = null,
        gender: String? = null
    ): Response<UserResponse> {
        return apiService.getUsers(count, seed, page, gender)
    }

}