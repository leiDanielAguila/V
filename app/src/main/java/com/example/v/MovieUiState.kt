package com.example.v

const val boxCountMovie1 = 81 //  change to 81
const val easyMovieHintCount = 4 // hint notes count
const val scoreIncrease = 10 // score increase

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

val frozen = setOf(19, 20, 21, 22, 23, 24)
val moana = setOf(76, 77, 78, 79, 80)
val brave = setOf(11, 20, 29, 38, 47)
val encanto = setOf(23, 32, 41, 50, 59, 68, 77)
val numbering = setOf(2, 14, 18, 75,)


val movieEasyWords = mapOf(
        frozen to "frozen",
        moana to "moana",
        encanto to "encanto",
        brave to "brave",
        numbering to numberingSystem
    )

val movieEasyHint = mapOf(
    1 to "A scottish princess",
    2 to "A magical ice queen",
    3 to "A family with superpowers",
    4 to "journey across the ocean"
)

data class MovieUiState (
    var userInput: String = "",
    var userScore:Int = 0,
    var wordTileStorage: MutableSet<Int> = mutableSetOf(),
    var isGameOver: Boolean = false,
    var isCorrect: Boolean = false,
)


