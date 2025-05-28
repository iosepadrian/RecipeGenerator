package com.example.receipegenerator.network

import com.example.receipegenerator.data.model.AiRequest
import com.example.receipegenerator.data.model.AiResponse
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.Query

//TODO won't use it because I won t use an endpoint to get a response
interface ApiService {

    @POST("v1beta/models/gemini-1.5-turbo:generateText")
    suspend fun generateContent(
        @Query("key") apiKey: String,
        @Body request: AiRequest
    ): AiResponse
}