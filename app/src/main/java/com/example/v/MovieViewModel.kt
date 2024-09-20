package com.example.v

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
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
            val updatedScore = _movieUiState.value.userScore.plus(scoreIncrease)

            findWord(userInput)
            //keyStorage.add(findKeyPair(userInput))
            usedWords.add(userInput)
            SoundManager.correctSound()
            resetUserInput()
            _movieUiState.update { currentState ->
                currentState.copy(
                    isAnswerWrong = false,
                    userScore = updatedScore,
                    wordTileStorage = wordTileStorage
                    // keyStorage = keyStorage
                )
            }
        } else if (userInput.lowercase() in usedWords) {
            SoundManager.usedWord()
            resetUserInput()
        } else {
            SoundManager.wrongSound()
            resetUserInput()
            _movieUiState.update { currentState ->
                currentState.copy(
                    isAnswerWrong = true
                )
            }
        }
    }

    private fun findWord(userInput: String) {
        for ((key, value) in movieEasyWords) {
            if (value == userInput) {
                wordTileStorage.addAll(key)
            }
        }
    }

//    private fun findKeyPair(userInput: String): Int {
//        var keyFound = 0
//        for ((key, value) in movieEasyWords) {
//            if (value == userInput) {
//                keyFound = key
//            }
//        }
//        return keyFound
//    }

    fun resetGame() {
        _movieUiState.value = MovieUiState(
            userInput = "",
            userScore = 0,
            isAnswerWrong = false,
            wordTileStorage = mutableSetOf()
            // keyStorage = mutableSetOf(),
        )
        usedWords.clear()
        wordTileStorage.clear()
    }
}