package com.example.receipegenerator.data.model

data class AiRequest(
    val version: String,
    val input: Map<String, String>
)