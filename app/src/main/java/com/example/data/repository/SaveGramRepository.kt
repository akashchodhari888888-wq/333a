package com.example.data.repository

import com.example.data.local.DownloadHistory
import com.example.data.local.HistoryDao
import com.example.data.remote.FetchRequest
import com.example.data.remote.FetchResponse
import com.example.data.remote.SaveGramApi
import kotlinx.coroutines.flow.Flow
import retrofit2.Response

class SaveGramRepository(
    private val api: SaveGramApi,
    private val historyDao: HistoryDao
) {
    val allHistory: Flow<List<DownloadHistory>> = historyDao.getAllHistory()

    suspend fun fetchMedia(url: String): Response<FetchResponse> {
        return api.fetchMedia(FetchRequest(url))
    }

    suspend fun saveToHistory(history: DownloadHistory) {
        historyDao.insertHistory(history)
    }

    suspend fun deleteHistoryItem(id: Int) {
        historyDao.deleteHistory(id)
    }

    suspend fun clearHistory() {
        historyDao.clearAllHistory()
    }
}
