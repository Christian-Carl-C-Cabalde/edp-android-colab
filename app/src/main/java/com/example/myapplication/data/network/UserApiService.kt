package com.example.myapplication.data.network

import com.example.myapplication.data.network.dto.NewUserDto
import com.example.myapplication.data.network.dto.UserDto
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface UserApiService {

    // GET "users" — finds accounts whose data contains this email.
    // The server answers with a JSON array, so the result is a List.
    @GET("users")
    suspend fun findByEmail(@Query("email") email: String): List<UserDto>

    // POST "users" — creates the account. The server answers 201 with the
    // saved user, now carrying the id it made.
    @POST("users")
    suspend fun createUser(@Body user: NewUserDto): UserDto
}