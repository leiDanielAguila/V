package com.example.v

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class MovieViewModel: ViewModel() {

    private val _movieUiState = MutableStateFlow(MovieUiState())
    val movieUiState: StateFlow<MovieUiState> = _movieUiState.asStateFlow()

    var userInput by mutableStateOf("")

    fun updateUserInput(input: String) {
        userInput = input
    }
    fun resetUserInput() {
        userInput = ""
    }

    fun ifUserInputCorrect() {
        if (userInput.lowercase() in movieEasyWords) {
            val updatedScore = _movieUiState.value.userScore.plus(scoreIncrease)
            SoundManager.correctSound()
            resetUserInput()
            _movieUiState.update { currentState ->
                currentState.copy(
                    isAnswerWrong = false,
                    userScore = updatedScore
                )
            }
        } else {
            SoundManager.wrongSound()
            _movieUiState.update { currentState ->
                currentState.copy(
                    isAnswerWrong = true
                )
            }
        }
    }

    fun resetGame() {
        _movieUiState.value = MovieUiState(
            userInput = "",
            userScore = 0,
            isAnswerWrong = false
        )
    }
}