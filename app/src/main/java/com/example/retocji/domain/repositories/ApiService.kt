package com.example.retocji.domain.repositories

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

interface ApiService {
    @POST("auth/generateToken")
    suspend fun login(@Body authRequest: AuthRequest): Response<Token>

    @POST("citas/crear")
    suspend fun crearCita(@Header("Authorization") token: String,
                          @Body citas : Citas
    ) : Response<Citas>


}
