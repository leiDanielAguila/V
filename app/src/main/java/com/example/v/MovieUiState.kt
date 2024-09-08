package com.example.v

const val boxCountMovie1 = 64
val movieTilesNumber =
    setOf(
        1, 8, 9, 10, 11, 12, 13, 17, 20, 25, 28, 30, 33, 36, 38, 39, 44, 46, 52, 54, 59, 60, 61, 62, 63
    )
val movieEasyWords: Set<String> =
    setOf(
        "up",
        "frozen",
        "moana",
        "encanto",
        "mulan",
        "brave"
    )


const val scoreIncrease = 10

data class MovieUiState(
    var userInput: String = "",
    var userScore:Int = 0,
    var isAnswerWrong:Boolean = false,
)


