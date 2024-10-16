package com.example.v


data class MovieUiState (
    var userInput: String = "",
    var userScore:Int = 0,
    var wordTileStorage: MutableSet<Int> = mutableSetOf(),
    var isGameOver: Boolean = false,
    var isCorrect: Boolean = false,
    var gameLives: Int = 3
)


