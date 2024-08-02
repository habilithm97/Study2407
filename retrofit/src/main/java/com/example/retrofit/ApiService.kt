package com.example.retrofit

import retrofit2.Call
import retrofit2.http.GET

// Retrofit API 인터페이스
interface ApiService {
    @GET("users") // HTTP GET 요청
    fun getUsers(): Call<List<User>> // Call<> : 비동기/동기 HTTP 요청
}