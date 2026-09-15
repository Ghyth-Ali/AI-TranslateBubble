package com.aitranslatebubble.data.repository

import com.aitranslatebubble.data.local.TranslationDao
import com.aitranslatebubble.data.local.TranslationEntity
import com.google.mlkit.nl.translate.TranslateLanguage
import com.google.mlkit.nl.translate.Translation
import com.google.mlkit.nl.translate.TranslatorOptions
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.tasks.await

class TranslationRepository(
    private val translationDao: TranslationDao
) {
    val allTranslations: Flow<List<TranslationEntity>> = translationDao.getAllTranslations()

    suspend fun translateTextOnDevice(
        text: String,
        sourceLangCode: String,
        targetLangCode: String
    ): Result<String> {
        return try {
            val sourceLang = TranslateLanguage.fromLanguageTag(sourceLangCode) ?: TranslateLanguage.ENGLISH
            val targetLang = TranslateLanguage.fromLanguageTag(targetLangCode) ?: TranslateLanguage.GERMAN

            val options = TranslatorOptions.Builder()
                .setSourceLanguage(sourceLang)
                .setTargetLanguage(targetLang)
                .build()

            val translator = Translation.getClient(options)
            translator.downloadModelIfNeeded().await()
            val result = translator.translate(text).await()
            translator.close()

            Result.success(result)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun saveTranslation(originalText: String, translatedText: String, sourceLang: String, targetLang: String) {
        val entity = TranslationEntity(
            originalText = originalText,
            translatedText = translatedText,
            sourceLanguage = sourceLang,
            targetLanguage = targetLang
        )
        translationDao.insertTranslation(entity)
    }

    suspend fun deleteTranslation(translation: TranslationEntity) {
        translationDao.deleteTranslation(translation)
    }
}
