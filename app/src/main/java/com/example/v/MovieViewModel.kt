package com.example.v

import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.airbnb.lottie.compose.LottieAnimatable
import com.airbnb.lottie.compose.rememberLottieAnimatable
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class MovieViewModel: ViewModel() {

    private val _movieUiState = MutableStateFlow(MovieUiState())
    val movieUiState: StateFlow<MovieUiState> = _movieUiState.asStateFlow()

    var userInput by mutableStateOf("")

    fun updateUserInput(input: String) {
        userInput = input.trim()
    }
    private fun resetUserInput() {
        userInput = ""
    }

    private var usedWords: MutableSet<String> = mutableSetOf()
    private var wordTileStorage: MutableSet<Int> = mutableSetOf()
    // private var keyStorage: MutableSet<Int> = mutableSetOf()



    fun ifUserInputCorrect() {
        if (
            userInput.lowercase() in movieEasyWords.values &&
            userInput.lowercase() !in usedWords
            )
        {
            usedWords.add(userInput)
            val updatedScore = _movieUiState.value.userScore.plus(scoreIncrease)
            findWord()
            SoundManager.correctSound()
            _movieUiState.update { currentState ->
                currentState.copy(
                    userScore = updatedScore,
                    wordTileStorage = wordTileStorage
                )
            }
            resetUserInput()
        } else if (userInput.lowercase() in usedWords) {
            SoundManager.usedWord()
            resetUserInput()

        } else if(userInput.isBlank()) {
            resetUserInput()
        }
        else {
            SoundManager.wrongSound()
            resetUserInput()
        }
    }

    private fun findWord() {
        for ((key, value) in movieEasyWords) {
            if (userInput == value) {
                wordTileStorage.addAll(key)
            }
        }
    }

    fun ifGameFinished() {
        if (_movieUiState.value.userScore == 40) {
            _movieUiState.update { currentState ->
                currentState.copy(
                    isGameOver = true
                )
            }
        }
    }

    fun resetGame() {
        _movieUiState.value = MovieUiState(
            userInput = "",
            userScore = 0,
            wordTileStorage = mutableSetOf()
            // keyStorage = mutableSetOf(),
        )
        usedWords.clear()
        wordTileStorage.clear()
    }
}