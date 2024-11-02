package com.example.v.model

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.v.data.MovieUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class MovieViewModel: ViewModel() {
    private val _movieUiState = MutableStateFlow(MovieUiState())
    val movieUiState: StateFlow<MovieUiState> = _movieUiState.asStateFlow()

    internal val gameTilesCount = 156
    internal val hintNoteCount = 10
    private val scoreIncrease = 10
    var updatedScore = 0

    private var usedWords: MutableSet<String> = mutableSetOf()
    private var wordTileStorage: MutableSet<Int> = mutableSetOf()

    var userInput by mutableStateOf("")

    val movieEasyTiles = mapOf( 18 to 'm', 30 to 'o', 32 to 'm',  42 to 'a',
        44 to 'u', 52 to 't', 53 to 'a', 54 to 'n', 55 to 'g', 56 to 'l', 57 to 'e', 58 to 'd',
        63 to 'r', 64 to 'a', 65 to 'y', 66 to 'a', 68 to 'a', 71 to 'c', 76 to 'r',  80 to 'n',
        81 to 'e', 82 to 'm', 83 to 'o',  85 to 'f', 86 to 'r', 87 to '0', 88 to 'z', 89 to 'e', 90 to 'n',
        95 to 'o', 100 to 'a', 107 to 'o',109 to 'l', 110 to 'i', 111 to 'o', 112 to 'n',113 to 'k', 114 to 'i',115 to 'n',
        116 to 'g', 121 to 'u', 133 to 'c', 145 to 'a')

    val numberTiles = mapOf(6 to '1', 20 to '7',40 to '2', 51 to '3',59 to '9',62 to '4',79 to '8',84 to '5',108 to '6', 97 to "10")

    fun checkUserInput(userInput:String) {

    }

    fun updateUserInput(newUserInput: String) {
        userInput = newUserInput.lowercase()
    }

}