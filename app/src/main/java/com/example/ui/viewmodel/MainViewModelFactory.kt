package com.example.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.data.datastore.PreferencesManager
import com.example.data.repository.SaveGramRepository

class MainViewModelFactory(
    private val repository: SaveGramRepository,
    private val preferencesManager: PreferencesManager
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return MainViewModel(repository, preferencesManager) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
