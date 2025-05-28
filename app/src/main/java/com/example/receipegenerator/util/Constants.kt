package com.example.receipegenerator.util

import com.google.ai.client.generativeai.type.generationConfig

object Constants {
    const val API_KEY = "AIzaSyCC3k19plpJFpc-QErm6qFUdXZC0cmGOyE"
    const val AI_MODEL = "gemini-1.5-flash"
    val CONFIG = generationConfig {
        temperature = 0.2f
        maxOutputTokens = 3000
    }

    const val INITIAL_PROMPT = """
Please generate exactly 3 recipes as a JSON array. Each recipe has a "title", "preparation", "duration", and "ingredients" field. Return only valid JSON, nothing else.
Please return random recipes everytime

Example:

[
  {
    "title": "Spaghetti Carbonara",
    "preparation": "Boil pasta. In a separate pan, cook pancetta, then mix with eggs and cheese. Combine everything together.",
    "duration": "25",
    "ingredients": ["spaghetti", "pancetta", "eggs", "parmesan cheese", "black pepper"]
  },
  {
    "title": "Greek Salad",
    "preparation": "Chop all vegetables, mix in a bowl, and top with feta and olives. Drizzle with olive oil and lemon juice.",
    "duration": "10",
    "ingredients": ["tomatoes", "cucumber", "red onion", "feta cheese", "olives", "olive oil", "lemon juice"]
  },
  {
    "title": "Chicken Stir Fry",
    "preparation": "Slice chicken and vegetables, stir fry in a hot pan with soy sauce and garlic until cooked.",
    "duration": "20",
    "ingredients": ["chicken breast", "bell peppers", "soy sauce", "garlic", "onion", "vegetable oil"]
  }
]

Now generate your response:
"""
}