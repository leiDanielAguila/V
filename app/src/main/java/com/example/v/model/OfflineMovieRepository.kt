package com.example.v.model

import com.example.v.data.MovieUiState
import kotlinx.coroutines.flow.Flow

abstract class OfflineMovieRepository(private val movieDao: MovieDao) : MovieRepository {
    override suspend fun insertItem(movieUiState: MovieUiState) = movieDao.upsertMovie(movieUiState)
    override suspend fun deleteItem(movieUiState: MovieUiState) = movieDao.deleteMovie(movieUiState)
    override fun getAllItems(): Flow<List<MovieUiState>> = movieDao.getAllItems()
    override fun getItem(id: Int): Flow<MovieUiState> = movieDao.getItem(id)
}