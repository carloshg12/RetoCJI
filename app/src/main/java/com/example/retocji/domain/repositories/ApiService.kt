package com.example.retocji.domain.repositories

import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

interface ApiService {
    @POST("auth/generateToken")
    suspend fun login(@Body authRequest: AuthRequest): Response<Token>

    @POST("citas/crear")
    suspend fun crearCita(
        @Header("Authorization") token: String,
        @Body citas: CitasDTO
    ): Response<ResponseBody>

    @GET("auth/user/userProfile")
    suspend fun userProfile(@Header("Authorization") token: String): Response<ResponseBody>


    @POST("/auth/addNewUser")
    suspend fun addNewUser(@Body registerUserDTO: RegisterUserDTO): Response<ResponseBody>

}
