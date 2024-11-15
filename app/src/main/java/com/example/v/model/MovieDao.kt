package com.example.v.model

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import com.example.v.data.MovieUiState
import kotlinx.coroutines.flow.Flow

@Dao
interface MovieDao {
    @Upsert
    suspend fun upsertMovie(movieUiState: MovieUiState)

    @Delete
    suspend fun deleteMovie(movieUiState: MovieUiState)

    @Query("SELECT * from movieuistate WHERE id = :id")
    fun getItem(id: Int): Flow<MovieUiState>

    @Query("SELECT * from movieuistate ORDER BY id ASC")
    fun getAllItems(): Flow<List<MovieUiState>>
}

interface MovieRepository {
    suspend fun insertItem(movieUiState: MovieUiState)
    suspend fun deleteItem(movieUiState: MovieUiState)
    fun getAllItems(): Flow<List<MovieUiState>>
    fun getItem(id: Int): Flow<MovieUiState>
}

