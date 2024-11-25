package com.example.v.model

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Upsert
import com.example.v.data.MovieState
import com.example.v.data.MovieUiState
import kotlinx.coroutines.flow.Flow

@Dao // do not use
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

@Dao
interface NewMovieDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertMovie(movieState: MovieState)

    @Delete
    suspend fun deleteMovie(movieState: MovieState)

    @Query("SELECT * from moviestate WHERE id = :id")
    fun getMovieItem(id: Int): Flow<MovieState>

    @Query("SELECT * from moviestate ORDER BY id ASC")
    fun getAllMovieItems(): Flow<List<MovieState>>
}

interface newMovieRepository {
    suspend fun insertItem(movieState: MovieState)
    suspend fun deleteItem(movieState: MovieState)
    fun getAllItems(): Flow<List<MovieState>>
    fun getItem(id: Int): Flow<MovieState>
}

