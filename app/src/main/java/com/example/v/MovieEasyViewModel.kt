package com.example.v

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class MovieEasyViewModel: ViewModel() {

    private val _movieUiState = MutableStateFlow(MovieUiState())
    val movieUiState: StateFlow<MovieUiState> = _movieUiState.asStateFlow()

    internal val boxCountMovie1 = 81 //  change to 81 game box dimensions
    internal val easyMovieHintCount = 4 // hint notes count
    private val scoreIncrease = 10 // score increase
    private var updatedScore = 0

    private var usedWords: MutableSet<String> = mutableSetOf()
    private var wordTileStorage: MutableSet<Int> = mutableSetOf()

    var userInput by mutableStateOf("")

    val movieEasyTiles =
        mapOf(
            11 to 'B',
            19 to 'F',
            20 to 'R',
            21 to 'O',
            22 to 'Z',
            23 to 'E',
            24 to 'N',
            29 to 'A',
            32 to 'N',
            38 to 'V',
            41 to 'C',
            47 to 'E',
            50 to 'A',
            59 to 'N',
            68 to 'T',
            76 to 'M',
            77 to 'O',
            78 to 'A',
            79 to 'N',
            80 to 'A'
        )

    val numberingSystem = mapOf(
        2 to '1',
        14 to '3',
        18 to '2',
        75 to '4'
    )

    private val frozen = setOf(19, 20, 21, 22, 23, 24)
    private val moana = setOf(76, 77, 78, 79, 80)
    private val brave = setOf(11, 20, 29, 38, 47)
    private val encanto = setOf(23, 32, 41, 50, 59, 68, 77)
    private val numbering = setOf(2, 14, 18, 75)


    private val movieEasyWords = mapOf(
        frozen to "frozen",
        moana to "moana",
        encanto to "encanto",
        brave to "brave",
        numbering to numberingSystem
    )

    val movieEasyHint = mapOf(
        0 to "Disney movie about a scottish archer",
        1 to "Disney movie about a magical ice queen",
        2 to "A colombian family with superpowers",
        3 to "Journey across the ocean to save their island"
    )

    fun updateUserInput(input: String) {
        userInput = input.trim().lowercase()
    }

    private fun resetUserInput() {
        userInput = ""
    }

    fun ifUserInputCorrect() {
        if (userInput in movieEasyWords.values && userInput !in usedWords) {
            updatedScore = _movieUiState.value.userScore.plus(scoreIncrease)
            usedWords.add(userInput)
            findWord()
            updateState()
            SoundManager.correctSound()
            resetUserInput()
        } else if (userInput in usedWords || userInput.isBlank()) {
            SoundManager.usedWord()
            resetUserInput()
        } else {
            SoundManager.wrongSound()
            resetUserInput()
            removeOneLife()
        }
    }

    private fun findWord() {
        for ((key, value) in movieEasyWords) {
            if (userInput == value) {
                wordTileStorage.addAll(key)
            }
        }
    }

    private fun removeOneLife() {
        _movieUiState.update { currentState ->
            currentState.copy(
                gameLives = currentState.gameLives - 1,
                isCorrect = false
            )
        }
    }

    private fun updateState() {
        _movieUiState.update { currentState ->
            currentState.copy(
                userScore = updatedScore,
                wordTileStorage = wordTileStorage
            )
        }
    }

    fun ifGameFinished() {
        if (_movieUiState.value.userScore == 40) {
            _movieUiState.update { currentState ->
                currentState.copy(
                    isGameOverAndWin = true
                )
            }
        } else if (_movieUiState.value.gameLives == 0) {
            _movieUiState.update { currentState ->
                currentState.copy(
                    isGameOverAndLose = true
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