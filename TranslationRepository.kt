package com.example.aitranslatebubble.data

import kotlinx.coroutines.flow.Flow

class TranslationRepository(private val dao: TranslationDao) {
        val allHistory: Flow<List<TranslationHistory>> = dao.getAllHistory()

            suspend fun addTranslation(originalText: String, translatedText: String) {
                        val item = TranslationHistory(
                                        originalText = originalText,
                                                    translatedText = translatedText
                        )
                                dao.insertTranslation(item)
            }

                suspend fun clearAll() {
                            dao.clearHistory()
                }
}

                }
                        )
            }
}