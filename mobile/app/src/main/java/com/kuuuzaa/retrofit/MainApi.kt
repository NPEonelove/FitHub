package com.kuuuzaa.retrofit

import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.Path

interface MainApi {

    @POST("/api/v1/auth/sign-up")
    suspend fun reg(@Body regRequest: RegRequest):User

    @POST("/api/v1/auth/sign-in")
    suspend fun auth(@Body authRequest: AuthRequest):User

    @Headers("Content-Type: application/json")

    @GET("/api/v1/trains/{userId}/get-all-trains")
    suspend fun getAllTrainsByIdAuth(@Header("Authorization") token: String, @Path("userId") userId:String): List<Train>

    @Headers("Content-Type: application/json")

    @POST("/api/v1/trains/{userId}/create-demo-trains")
    suspend fun createTestTrains(@Header("Authorization") token: String, @Path("userId") userId:String)

    @Headers("Content-Type: application/json")

    @GET("/api/v1/trains/{trainId}")
    suspend fun getAllTrains(@Header("Authorization") token: String): List<Train>

    @Headers("Content-Type: application/json")

    @GET("/api/v1/trains/{trainId}")
    suspend fun getTrainById(@Header("Authorization") token: String, @Path("trainId") trainId:String): List<Train>
}