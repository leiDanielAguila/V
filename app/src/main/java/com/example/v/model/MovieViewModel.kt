package com.example.v.model

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.v.data.MovieUiState
import com.example.v.service.SoundManager
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class MovieViewModel: ViewModel() {
    private val _movieUiState = MutableStateFlow(MovieUiState())
    val movieUiState: StateFlow<MovieUiState> = _movieUiState.asStateFlow()


    internal val movieDisneyEasyGridCount = 12
    internal val movieDisneyMediumGridCount = 10
    internal val gameTilesCount = 156
    internal val hintNoteCount = 10
    internal val hintNoteDisneyMedium = 6
    internal val gameTilesDisneyMediumCount = 160
    private val scoreIncrease = 10
    private var updatedScore = 0

    private var usedWords: MutableSet<String> = mutableSetOf()
    private var wordTileStorage: MutableSet<Int> = mutableSetOf()

    var userInput by mutableStateOf("")

    val movieEasyTiles = mapOf( 18 to 'm', 30 to 'o', 32 to 'm',  42 to 'a',
        44 to 'u', 52 to 't', 53 to 'a', 54 to 'n', 55 to 'g', 56 to 'l', 57 to 'e', 58 to 'd',
        63 to 'r', 64 to 'a', 65 to 'y', 66 to 'a', 68 to 'a', 71 to 'c', 76 to 'r',  80 to 'n',
        81 to 'e', 82 to 'm', 83 to 'o',  85 to 'f', 86 to 'r', 87 to '0', 88 to 'z', 89 to 'e', 90 to 'n',
        95 to 'o', 100 to 'a', 107 to 'o',109 to 'l', 110 to 'i', 111 to 'o', 112 to 'n',113 to 'k', 114 to 'i',115 to 'n',
        116 to 'g', 121 to 'u', 133 to 'c', 145 to 'a')

    private val moana = setOf(18,30,42,54,66)
    private val mulan = setOf(32,44,56,68,80)
    private val tangled = setOf(52, 53, 54, 55, 56, 57, 58)
    private val frozen = setOf(85,86, 87, 88,89, 90)
    private val tarzan = setOf(52, 64, 76, 88, 100, 112)
    private val lionking = setOf(109, 110, 111, 112, 113, 114, 115, 116)
    private val coco = setOf(71, 83, 95, 107)
    private val nemo = setOf(80, 81, 82, 83)
    private val luca = setOf(109, 121, 133, 145)
    private val raya = setOf(63, 64, 65, 66)

    val movieEasyWords = mapOf(
        moana to "moana",
        mulan to "mulan",
        tangled to "tangled",
        frozen to "frozen",
        tarzan to "tarzan",
        lionking to "lionking",
        coco to "coco",
        nemo to "nemo",
        luca to "luca",
        raya to "raya"
    )

    val movieEasyYears = mapOf(
        0 to "2016",
        1 to "1999",
        2 to "2010",
        3 to "2021",
        4 to "2013",
        5 to "1994",
        6 to "1998",
        7 to "2003",
        8 to "2017",
        9 to "2021"
    )

     val movieEasyHints = mapOf(
        0 to "A brave Polynesian girl sets sail to save her island, guided by an ancient legend",
        1 to "Raised by gorillas in the jungle, a man discovers his human heritage and love",
        2 to "A runaway princess with magical hair teams up with a thief",
        3 to "A skilled warrior seeks the last dragon to save her kingdom",
        4 to "Two sisters, one with ice powers, navigate love and family",
        5 to "A lion cub must reclaim his rightful place as king after the death of his father",
        6 to "A young woman disguises herself as a man to take her father's place in the Chinese army",
        7 to "A clownfish ventures across the ocean to find his lost son",
        8 to "A young boy embarks on a journey to the Land of the Dead to uncover his family's secret",
        9 to "A sea monster hides his true identity while experiencing a life-changing summer"
    )

    val movieDisneyMediumTiles = mapOf(14 to 't', 24 to 'o', 34 to 'y', 44 to 's', 54 to 't', 64 to 'o', 74 to 'r', 84 to 'y', 22 to 'z', 23 to 'o',
        24 to 'o', 25 to 't', 26 to 'o', 27 to 'p', 28 to 'i', 29 to 'a', 28 to 'i', 38 to 'n', 48 to 'c', 58 to 'r', 68 to 'e' , 78 to 'd', 88 to 'i',
        98 to 'b', 108 to 'l', 118 to 'e', 128 to 's', 72 to 'd', 73 to 'o', 74 to 'r', 75 to 'y', 121 to 'm', 122 to 'o', 123 to 'n' ,124 to 's',
        125 to 't', 126 to 'e', 127 to 'r', 128 to 's', 117 to 'b', 127 to 'r', 137 to 'a', 147 to 'v', 157 to 'e')

    val movieDisneyMediumNumberTiles = mapOf(4 to '1', 21 to '2', 71 to '4', 107 to '6', 18 to '3', 120 to '5')

    private val toystory: Set<Int> = setOf(14,24,34,44,54,64,74,84)
    private val zootopia: Set<Int> = setOf(22,23,24,25,26,27,28,29)
    private val incredibles: Set<Int> = setOf(28,38,48,58,68,78,88,98,108,118,128)
    private val dory: Set<Int> = setOf(72,73,74,75)
    private val monsters: Set<Int> = setOf(121,122,123,124,125,126,127,128)
    private val brave: Set<Int> = setOf(117,127,137,147,157)

    val movieDisneyMediumWords = mapOf(
        toystory to "toystory",
        zootopia to "zootopia",
        incredibles to "incredibles",
        dory to "dory",
        monsters to "monsters",
        brave to "brave"
    )
    val movieDisneyMediumYears = mapOf(
        0 to "1995",
        1 to "2016",
        2 to "2004",
        3 to "2016",
        4 to "2001",
        5 to "2012"
    )

    val movieDisneyMediumHints = mapOf(
        0 to "The secret life of toys.",
        1 to "A bunny cop and a fox solve a mystery.",
        2 to "A superhero family saves the day.",
        3 to "A forgetful fish",
        4 to "Monsters scare to power their world",
        5 to "an irish princess with a bear mother"
    )


    val movieEasyNumberTiles = mapOf(6 to '1', 20 to '7',40 to '2', 51 to '3',59 to '9',62 to '4',79 to '8',84 to '5',108 to '6', 97 to "10")

    fun updateUserInput(newUserInput: String) {
        userInput = newUserInput.lowercase().replace("\\s".toRegex(), "")
    }

    private fun clearUserInput() {
        userInput = ""
    }

    fun checkUserInput(
        movieWords: Map<Set<Int>, String>,
    ) {
        if (userInput in movieWords.values && userInput !in usedWords) {
            updatedScore = _movieUiState.value.userScore.plus(scoreIncrease)
            usedWords.add(userInput)
            findWord(movieWords)
            updateState()
            SoundManager.correctSound()
            clearUserInput()
        } else if (userInput in usedWords || userInput.isBlank()) {
            SoundManager.usedWord()
            clearUserInput()
        } else {
            SoundManager.wrongSound()
            clearUserInput()
           // removeOneLife()
        }
    }

    private fun findWord(
        movieWords: Map<Set<Int>, String>
    ) {
        for ((key, value) in movieWords) {
            if (userInput == value) {
                wordTileStorage.addAll(key)
            }
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

    fun resetGame() {
        _movieUiState.value = MovieUiState(
            userInput = "",
            userScore = 0,
            wordTileStorage = mutableSetOf(),
            isGameOverAndWin  = false,
            isGameOverAndLose = false,
            gameLives = 3,
        )
        usedWords.clear()
        wordTileStorage.clear()
    }
}