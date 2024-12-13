package com.example.flush.domain.repository

import com.example.flush.domain.model.Emotion
import org.w3c.dom.Text

interface EmotionAnalysisRepository {
    suspend fun analyzeEmotion(text: String): Emotion

    suspend fun gemini(text: String): String
}