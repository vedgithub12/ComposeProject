package com.example.composeproject

import retrofit2.Call
import retrofit2.http.GET

interface BlogService {
    @GET("blogs") // The endpoint is just "blogs" since you provided the base URL separately
    fun getBlogs(): Call<List<Article>>
}