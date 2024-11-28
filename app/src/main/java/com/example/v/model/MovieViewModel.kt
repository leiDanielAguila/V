package com.example.v.model

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.v.data.MovieState
import com.example.v.data.MovieUiState
import com.example.v.service.SoundManager
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch


class MovieViewModel(
    private val movieDao: NewMovieDao
): ViewModel() {
    private val _movieUiState = MutableStateFlow(MovieUiState())
    private val _movieState = MutableStateFlow(MovieState())
    val movieUiState: StateFlow<MovieUiState> = _movieUiState.asStateFlow()
    val movieState: StateFlow<MovieState> = _movieState.asStateFlow()


    internal val movieDisneyEasyGridCount = 12
    internal val movieDisneyMediumGridCount = 10
    internal val movieDisneyHardGridCount = 9
    internal val movieSuperHeroEasyGridCount = 14

    internal val hintNoteCount = 10
    internal val hintNoteDisneyMedium = 6
    internal val hintNoteDisneyHard = 4
    internal val hintNoteSuperHeroEasy = 10

    internal val gameTilesCount = 156
    internal val gameTilesDisneyMediumCount = 160
    internal val gameTilesDisneyHardCount = 98
    internal val gameTilesSuperHeroEasyCount = 181

    private var isCorrect = false


    private val scoreIncrease = 10
    private var updatedScore = 0
    private var updatedWordCount = 0

    private var usedWords: MutableSet<String> = mutableSetOf()
    private var wordTileStorage: MutableSet<Int> = mutableSetOf()
    private var disneyHardTileStorage: MutableSet<Int> = mutableSetOf()
    private var disneyEasyTileStorage: MutableSet<Int> = mutableSetOf()
    private var disneyMediumTileStorage: MutableSet<Int> = mutableSetOf()
    private var superheroEasyTileStorage: MutableSet<Int> = mutableSetOf()

    private var disneyEasyWordCount = 0
    private var disneyMediumWordCount = 0
    private var disneyHardWordCount = 0
    private var superheroEasyWordCount = 0

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

    val movieDisneyHardTiles = mapOf(
        13 to 'p', 22 to 'o', 31 to 'c', 40 to 'a', 49 to 'h', 58 to 'o', 67 to 'n',
        76 to 't', 85 to 'a', 94 to 's', 12 to 'u', 28 to 'h', 29 to 'e', 30 to 'r',
        32 to 'u', 33 to 'l', 34 to 'e', 35 to 's', 43 to 'n', 52 to 'c', 61 to 'a',
        70 to 'n', 79 to 't', 88 to 'o'
    )

    val movieDisneyHardNumberTiles = mapOf(4 to "1", 11 to "2", 27 to "3", 25 to "4")

    private val pocahontas: Set<Int> = setOf(13,22,31,40,49,58,67,76,85,94)
    private val up: Set<Int> = setOf(12,13)
    private val hercules: Set<Int> = setOf(28,29,30,31,32,33,34,35)
    private val encanto: Set<Int> = setOf(34,43,52,61,70,79,88)

    val movieDisneyHardWords = mapOf(
        pocahontas to "pocahontas",
        up to "up",
        hercules to "hercules",
        encanto to "encanto"
    )

    val movieDisneyHardYears = mapOf(
        0 to "1995",
        1 to "2009",
        2 to "1997",
        3 to "2021"
    )

    val movieDisneyHardHints = mapOf(
        0 to "An adventurous Native American woman finds love and navigates cultural differences.",
        1 to "A heartfelt journey of an old man, a boy scout, and a floating house.",
        2 to "A god becomes a hero, proving his worth and finding his place on Mount Olympus.",
        3 to "A magical family, a special house, and the power of embracing one’s uniqueness."
    )

    val movieSuperHeroEasyTiles = mapOf( 22 to 'i', 36 to 'r', 50 to 'o', 64 to 'n', 78 to 'm', 92 to 'a', 106 to 'n',
        33 to 'e', 34 to 't', 35 to 'e', 36 to 'r', 37 to 'n', 38 to 'a', 39 to 'l', 40 to 's',
        68 to 's', 82 to 'p', 96 to 'i', 110 to 'd', 124 to 'e', 138 to 'r', 152 to 'm', 166 to 'a', 180 to 'n',
        162 to 'b', 163 to 'a', 164 to 't', 165 to 'm', 166 to 'a', 167 to 'n', 134 to 'j', 135 to 'o', 136 to 'k', 137 to 'e',
        113 to 'h', 114 to 'e', 115 to 'l', 116 to 'l', 117 to 'b', 118 to 'o', 119 to 'y', 90 to 't', 104 to 'h', 132 to 'r',
        72 to 's', 86 to 'u', 100 to 'p', 128 to 'r', 142 to 'm', 156 to 'a', 170 to 'n',
        88 to 'a', 89 to 'n', 91 to 'm', 93 to 'n', 61 to 'a', 62 to 'v', 63 to 'e', 65 to 'g', 66 to 'e', 67 to 'r',
    )

    val movieSuperHeroEasyNumberTiles = mapOf(8 to "1", 32 to "6", 60 to "2", 58 to "10", 112 to "9", 133 to "7", 161 to "8", 54 to "4", 76 to "5", 87 to "3")

    private val ironman: Set<Int> = setOf(22, 36, 50, 64, 78, 92, 106)
    private val avengers: Set<Int> = setOf(61, 62, 63, 64, 65, 66, 67, 68)
    private val eternals: Set<Int> = setOf(33, 34, 35, 36, 37, 38, 39, 40)
    private val spiderman: Set<Int> = setOf(68, 82, 96, 110, 124, 138, 152, 166, 180)
    private val thor: Set<Int> = setOf(90, 104, 118, 132)
    private val joker: Set<Int> = setOf(134, 135, 136, 137, 138)
    private val batman: Set<Int> = setOf(162, 163, 164, 165, 166, 167)
    private val hellboy: Set<Int> = setOf(113, 114, 115, 116, 117, 118, 119)
    private val superman: Set<Int> = setOf(72, 86, 100, 114, 128, 142, 156, 170)
    private val antman: Set<Int> = setOf(88 ,89, 90, 91, 92, 93)

    val movieSuperHeroEasyWords = mapOf(
        ironman to "ironman",
        avengers to "avengers",
        eternals to "eternals",
        spiderman to "spiderman",
        thor to "thor",
        joker to "joker",
        batman to "batman",
        hellboy to "hellboy",
        superman to "superman",
        antman to "antman"
    )


    val movieSuperHeroYears = mapOf(
        0 to "2008",
        1 to "2012",
        2 to "2021",
        3 to "2002",
        4 to "2002",
        5 to "2011",
        6 to "2019",
        7 to "2011",
        8 to "2012",
        9 to "2015",
    )

    val movieSuperHeroHints = mapOf(
        0 to "A billionaire builds a suit to save the world",
        1 to "Earth’s mightiest heroes assemble",
        2 to "Immortal beings protect humanity",
        3 to "Teen with spider powers fights crime",
        4 to "God of Thunder proves himself worthy",
        5 to "The origin of Gotham's clown prince of crime",
        6 to "A vigilante protects Gotham City",
        7 to "A demon fights to save humanity",
        8 to "An alien hero defends Earth",
        9 to "A thief shrinks to save the day",
    )

    init {
        // LOADING THE INITIAL STATE FROM DB
        viewModelScope.launch {
            movieDao.getMovieItem(1).collect { state ->
                Log.d("DAO", "Saving MovieState with id = ${movieState.value.id}")
                _movieState.value = state ?: MovieState()
            }

        }
    }

    private fun mapUiStateToMovieState(uiState: MovieUiState): MovieState {
        Log.d("Mapping", "Mapping UiState: $uiState")

        val newScore = if (isCorrect) {
            _movieState.value.userScore + scoreIncrease
        } else {
            _movieState.value.userScore
        }
        val hardTileStorage = _movieState.value.disneyHardTileStorage + uiState.disneyHardTileStorage
        val mediumTileStorage = _movieState.value.disneyMediumTileStorage + uiState.disneyMediumTileStorage
        val easyTileStorage = _movieState.value.disneyEasyTileStorage + uiState.disneyEasyTileStorage
        val superheroStorage = _movieState.value.superheroEasyTileStorage + uiState.superheroEasyTileStorage
//        val disneyEasyWordCount1 = _movieState.value.disneyEasyWordCount + uiState.disneyEasyWordCount
//        val disneyMediumWordCount2 = _movieState.value.disneyMediumWordCount + uiState.disneyMediumWordCount
//        val disneyHardWordCount3 = _movieState.value.disneyHardWordCount + uiState.disneyHardWordCount
//        val superheroEasyWordCount4 = _movieState.value.superheroEasyWordCount + uiState.superheroEasyWordCount


        val result = MovieState(
            id = _movieState.value.id,
            userScore = newScore, // Increment score
            disneyHardTileStorage = hardTileStorage.toMutableSet(), // Ensure no duplicates
            disneyMediumTileStorage = mediumTileStorage.toMutableSet(), // Ensure no duplicates
            disneyEasyTileStorage = easyTileStorage.toMutableSet(), // Ensure no duplicates
            superheroEasyTileStorage = superheroStorage.toMutableSet(), // Ensure no duplicates
            disneyEasyWordCount = _movieState.value.disneyEasyWordCount,
            disneyMediumWordCount = _movieState.value.disneyMediumWordCount,
            disneyHardWordCount = _movieState.value.disneyHardWordCount,
            superheroEasyWordCount = _movieState.value.superheroEasyWordCount,
            disneyEasyGameLives = _movieState.value.disneyEasyGameLives,
            disneyMediumGameLives = _movieState.value.disneyMediumGameLives,
            disneyHardGameLives = _movieState.value.disneyHardGameLives,
            superheroEasyGameLives = _movieState.value.superheroEasyGameLives,
        )
        Log.d("Mapping", "Resulting MovieState: $result")
        return result
    }

    private fun saveStateToDatabase() {
        viewModelScope.launch {
            try {
                // Log the current UI state before mapping
                Log.d("saveStateToDatabase", "Current UI State: ${_movieUiState.value}")

                // Extract only the updated fields
                val updatedMovieState = mapUiStateToMovieState(_movieUiState.value)

                // Log the mapped movie state
                Log.d("saveStateToDatabase", "Mapped Movie State: $updatedMovieState")

                // Save only the updated fields to the database
                movieDao.upsertMovie(updatedMovieState)

                // Confirm upsert operation
                Log.d("saveStateToDatabase", "Upsert operation successful for $updatedMovieState")
            } catch (e: Exception) {
                // Log any exception that occurs during the upsert process
                Log.e("saveStateToDatabase", "Error during upsert operation", e)
            }
        }
    }

    fun resetDatabaseToDefaultState() { // RESET DB
        viewModelScope.launch {
            movieDao.deleteMovie(MovieState(id = 1)) // Remove current state
        }
    }

    fun updateUserInput(newUserInput: String) {
        userInput = newUserInput.lowercase().replace("\\s".toRegex(), "")
    }

    private fun clearUserInput() {
        userInput = ""
    }

    fun checkUserInput(
        movieWords: Map<Set<Int>, String>,
        movieID: Int
    ) {

        val set = when (movieID) {
            1 -> _movieState.value.disneyEasyTileStorage.toMutableSet()
            2 -> _movieState.value.disneyMediumTileStorage.toMutableSet()
            3 -> _movieState.value.disneyHardTileStorage.toMutableSet()
            4 -> _movieState.value.superheroEasyTileStorage.toMutableSet()
            else -> throw IllegalArgumentException("Invalid movieID: $movieID")
        }

        if (userInput in movieWords.values
            && userInput !in usedWords) {
            updatedScore = _movieUiState.value.userScore.plus(scoreIncrease)
            usedWords.add(userInput)
            isCorrect = true
            findWord(movieWords, movieID)
            updateWordCount(movieID)
            // updatedWordCount = _movieUiState.value.wordCount.plus(1)
            updateState()
            saveStateToDatabase()
            //updateUserScore(updatedScore) // for checking remove if the app crashes
            SoundManager.correctSound()
            clearUserInput()
        } else if (userInput in usedWords
            || userInput.isBlank()
            || set in movieWords.keys
            ) {
            SoundManager.usedWord()
            isCorrect = false
            clearUserInput()
        } else {
            isCorrect = false
            SoundManager.wrongSound()
            clearUserInput()
            removeOneLife(movieID)
            saveStateToDatabase()
        }
    }

    private fun updateWordCount(movieID: Int) {
       when (movieID) {
           1 -> _movieState.value.disneyEasyWordCount+=1
           2 -> _movieState.value.disneyMediumWordCount+=1
           3 -> _movieState.value.disneyHardWordCount+=1
           4 -> _movieState.value.superheroEasyWordCount+=1
       }
    }

    fun checkIfGameIsOver(
       movieWords: Map<Set<Int>, String>,
       movieID: Int,
    ): Int {
        var gameOverStatus = 0
        val currentState = _movieState.value

        val movieIDSet = when(movieID) {
            1 -> currentState.disneyEasyWordCount
            2 -> currentState.disneyMediumWordCount
            3 -> currentState.disneyHardWordCount
            4 -> currentState.superheroEasyWordCount
            else -> throw IllegalArgumentException("Invalid movieID: $movieID")
        }

        val movieLives = when(movieID) {
            1 -> currentState.disneyEasyGameLives
            2 -> currentState.disneyMediumGameLives
            3 -> currentState.disneyHardGameLives
            4 -> currentState.superheroEasyGameLives
            else -> throw IllegalArgumentException("Invalid movieID: $movieID")
        }

        // 1 is gameOver and lose
        // 2 is gameOver and win

        if (movieLives == 0) {
            gameOverStatus = 1
            SoundManager.fail()
        } else if (movieIDSet == movieWords.size) {
            gameOverStatus = 2
            SoundManager.win()
        }

        return gameOverStatus
    }



    private fun findWord(
        movieWords: Map<Set<Int>, String>,
        movieID: Int
    ) {
        for ((key, value) in movieWords) {
            if (userInput == value && movieID == 3) {
                disneyHardTileStorage.addAll(key)
            }
            if (userInput == value && movieID == 2) {
                disneyMediumTileStorage.addAll(key)
            }
            if (userInput == value && movieID == 1) {
                disneyEasyTileStorage.addAll(key)
            }
            if (userInput == value && movieID == 4) {
                superheroEasyTileStorage.addAll(key)
            }
        }
    }

    private fun removeOneLife(movieID: Int) {
        _movieUiState.update { currentState ->
            currentState.copy(
                gameLives = currentState.gameLives - 1,
                isCorrect = false
            )
        }

        when (movieID) {
            1 -> _movieState.value.disneyEasyGameLives-=1
            2 -> _movieState.value.disneyMediumGameLives-=1
            3 -> _movieState.value.disneyHardGameLives-=1
            4 -> _movieState.value.superheroEasyGameLives-=1
            else -> throw IllegalArgumentException("Invalid movieID: $movieID")
        }
    }

    private fun updateState() {
        _movieUiState.update { currentState ->
            currentState.copy(
                userScore = updatedScore,
                disneyHardTileStorage = disneyHardTileStorage,
                disneyEasyTileStorage = disneyEasyTileStorage,
                disneyMediumTileStorage = disneyMediumTileStorage,
                superheroEasyTileStorage = superheroEasyTileStorage,
                wordCount = updatedWordCount,
                isCorrect = isCorrect,
                disneyEasyWordCount = disneyEasyWordCount,
                disneyMediumWordCount = disneyMediumWordCount,
                disneyHardWordCount = disneyHardWordCount,
                superheroEasyWordCount = superheroEasyWordCount
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
            wordCount = 0
        )
        usedWords.clear()
        wordTileStorage.clear()
    }
}