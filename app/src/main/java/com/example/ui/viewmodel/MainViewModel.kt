package com.example.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.datastore.PreferencesManager
import com.example.data.local.DownloadHistory
import com.example.data.remote.FetchResponse
import com.example.data.repository.SaveGramRepository
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

sealed class UiState {
    object Idle : UiState()
    object Loading : UiState()
    data class Success(val data: FetchResponse) : UiState()
    data class Error(val message: String) : UiState()
}

class MainViewModel(
    private val repository: SaveGramRepository,
    private val preferencesManager: PreferencesManager
) : ViewModel() {

    private val _uiState = MutableStateFlow<UiState>(UiState.Idle)
    val uiState: StateFlow<UiState> = _uiState.asStateFlow()

    val history = repository.allHistory.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = emptyList()
    )

    val themeMode = preferencesManager.themeMode.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = "system"
    )

    val language = preferencesManager.language.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = "en"
    )

    fun fetchMedia(url: String) {
        if (url.isBlank()) {
            _uiState.value = UiState.Error("Please enter a valid Instagram link")
            return
        }

        viewModelScope.launch {
            _uiState.value = UiState.Loading
            try {
                val response = repository.fetchMedia(url)
                if (response.isSuccessful && response.body() != null) {
                    val data = response.body()!!
                    _uiState.value = UiState.Success(data)
                    
                    // Save to history
                    repository.saveToHistory(
                        DownloadHistory(
                            url = url,
                            type = data.type,
                            thumbnail = data.thumbnail,
                            caption = data.caption,
                            owner = data.owner
                        )
                    )
                } else {
                    _uiState.value = UiState.Error("Failed to fetch media. Make sure the link is public.")
                }
            } catch (e: Exception) {
                _uiState.value = UiState.Error("Connection error. Please try again later.")
            }
        }
    }

    fun clearState() {
        _uiState.value = UiState.Idle
    }

    fun setThemeMode(mode: String) {
        viewModelScope.launch { preferencesManager.setThemeMode(mode) }
    }

    fun setLanguage(lang: String) {
        viewModelScope.launch { preferencesManager.setLanguage(lang) }
    }

    fun clearHistory() {
        viewModelScope.launch { repository.clearHistory() }
    }
}
