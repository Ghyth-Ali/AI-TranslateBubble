package com.aitranslatebubble.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aitranslatebubble.data.local.TranslationEntity
import com.aitranslatebubble.data.repository.TranslationRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class MainViewModel(
    private val repository: TranslationRepository
) : ViewModel() {

    val history: StateFlow<List<TranslationEntity>> = repository.allTranslations
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    fun translateAndSave(text: String, sourceLang: String = "en", targetLang: String = "de") {
        viewModelScope.launch {
            val result = repository.translateTextOnDevice(text, sourceLang, targetLang)
            result.getOrNull()?.let { translated ->
                repository.saveTranslation(text, translated, sourceLang, targetLang)
            }
        }
    }

    fun deleteItem(item: TranslationEntity) {
        viewModelScope.launch {
            repository.deleteTranslation(item)
        }
    }
}
