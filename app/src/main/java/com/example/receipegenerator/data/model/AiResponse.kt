package com.example.receipegenerator.data.model

data class AiResponse(
    val id: String,
    val status: String,
    val output: List<String>?
)