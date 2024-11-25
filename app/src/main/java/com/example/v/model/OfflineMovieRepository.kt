package com.example.v.model

import com.example.v.data.MovieState
import kotlinx.coroutines.flow.Flow

abstract class OfflineMovieRepository(private val newMovieDao: NewMovieDao) : newMovieRepository {
    override suspend fun insertItem(movieState: MovieState) = newMovieDao.upsertMovie(movieState)
    override suspend fun deleteItem(movieState: MovieState) = newMovieDao.deleteMovie(movieState)
    override fun getAllItems(): Flow<List<MovieState>> = newMovieDao.getAllMovieItems()
    override fun getItem(id: Int): Flow<MovieState> = newMovieDao.getMovieItem(id)
}



