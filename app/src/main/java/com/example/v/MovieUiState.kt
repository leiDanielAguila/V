package com.example.v

const val boxCountMovie1 = 64

val movieEasyTiles =
    mapOf(
        1 to 'B',
        8 to 'F',
        9 to 'R',
        10 to 'O',
        11 to 'Z',
        12 to 'E',
        13 to 'N',
        17 to 'A',
        20 to 'N',
        25 to 'V',
        28 to 'C',
        33 to 'E',
        36 to 'A',
        44 to 'N',
        52 to 'T',
        59 to 'M',
        60 to 'O',
        61 to 'A',
        62 to 'N',
        63 to 'A'
    )

val frozen = setOf(8, 9, 10, 11, 12, 13)
val moana = setOf(59, 60, 61, 62, 63)
val brave = setOf(1, 9, 17, 25, 33)
val encanto = setOf(12, 20, 28, 36, 44, 52, 60)


val movieEasyWords = mapOf(
        frozen to "frozen",
        moana to "moana",
        encanto to "encanto",
        brave to "brave"
    )

const val scoreIncrease = 10

data class MovieUiState (
    var userInput: String = "",
    var userScore:Int = 0,
    var wordTileStorage: MutableSet<Int> = mutableSetOf(),
    var isGameOver: Boolean = false
)


