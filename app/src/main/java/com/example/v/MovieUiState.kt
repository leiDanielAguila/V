package com.example.v

const val boxCountMovie1 = 64
val movieTilesNumber =
    setOf(
        1, 8, 9, 10, 11, 12, 13, 17, 20, 25, 28, 30, 33, 36, 38, 39, 44, 46, 52, 54, 59, 60, 61, 62, 63
    )

val frozenTilesNumber =
    mapOf(
        8 to "F",
        9 to "R",
        10 to "O",
        11 to "Z",
        12 to "E",
        13 to "N"
    )

val upTilesNumber =
    mapOf(
        38 to "U",
        39 to "P"
    )

val moanaTilesNumber =
    mapOf(
        59 to "M",
        60 to "O",
        61 to "A",
        62 to "N",
        63 to "A"
    )

val encantoTilesNumber =
    mapOf(
        12 to "E",
        20 to "N",
        28 to "C",
        36 to "E",
        44 to "N",
        52 to "T",
        60 to "O"
    )

val mulanTilesNumber =
    mapOf(
        30 to "M",
        38 to "U",
        46 to "L",
        54 to "A",
        62 to "N"
    )

val braveTilesNumber =
    mapOf(
        1 to "B",
        9 to "R",
        17 to "A",
        25 to "V",
        33 to "E"
    )


val movieEasyWords = mapOf(
        1 to "up",
        2 to "frozen",
        3 to "moana",
        4 to "encanto",
        5 to "mulan",
        6 to "brave"
    )


const val scoreIncrease = 10

data class MovieUiState(
    var userInput: String = "",
    var userScore:Int = 0,
    var isAnswerWrong:Boolean = false,
    var keyStorage: MutableSet<Int> = mutableSetOf(),
    // var resetTiles: Boolean = false
)


