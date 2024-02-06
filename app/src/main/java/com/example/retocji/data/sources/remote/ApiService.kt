package com.example.retocji.data.sources.remote

import com.example.retocji.domain.models.AuthRequest
import com.example.retocji.domain.models.citas.CitasDTO
import com.example.retocji.domain.models.logIn.RegisterUserDTO
import com.example.retocji.domain.models.gestiones.TipoCitaDTO
import com.example.retocji.domain.models.Token
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Query

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

    @GET("/auth/user/gestores")
    suspend fun getGestores(@Header("Authorization") token: String): Response<List<String>>

    @GET("/tipo/cita")
    suspend fun getTipoCitas(): Response<List<TipoCitaDTO>>

    @GET("/citas/citagestorydia")
    suspend fun getCitasPorGestorYDia(
        @Query("name") name: String,
        @Query("dia") dia: String
    ): Response<List<CitasDTO>>


    @GET("/auth/user/name")
    suspend fun getUserName(@Header("Authorization") authHeader: String, @Query("token") token: String): Response<ResponseBody>

}