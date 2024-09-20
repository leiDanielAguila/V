package com.example.v

const val boxCountMovie1 = 64
val movieTilesNumber =
    setOf(
        1, 8, 9, 10, 11, 12, 13, 17, 20, 25, 28, 30, 33, 36, 38, 39, 44, 46, 52, 54, 59, 60, 61, 62, 63
    )

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
        30 to 'M',
        33 to 'E',
        36 to 'A',
        38 to 'U',
        39 to 'P',
        44 to 'N',
        46 to 'L',
        52 to 'T',
        54 to 'A',
        59 to 'M',
        60 to 'O',
        61 to 'A',
        62 to 'N',
        63 to 'A'
    )

val frozen = setOf(8, 9, 10, 11, 12, 13)
val moana = setOf(59, 60, 61, 62, 63)
val up = setOf(38, 39)
val brave = setOf(1, 9, 17, 25, 33)
val mulan = setOf(30, 38, 46, 54, 62)
val encanto = setOf(12, 20, 28, 36, 44, 52, 60)


val movieEasyWords = mapOf(
        up to "up",
        frozen to "frozen",
        moana to "moana",
        encanto to "encanto",
        mulan to "mulan",
        brave to "brave"
    )

const val scoreIncrease = 10

data class MovieUiState (
    var userInput: String = "",
    var userScore:Int = 0,
    var isAnswerWrong:Boolean = false,
    var wordTileStorage: MutableSet<Int> = mutableSetOf()
    // var keyStorage: MutableSet<Int> = mutableSetOf(),
    // var resetTiles: Boolean = false
)


