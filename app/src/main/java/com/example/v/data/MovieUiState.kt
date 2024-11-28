package com.example.v.data

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity
data class MovieUiState (
    @PrimaryKey
    val id: Int = 1,
    var userInput: String = "",
    var userScore:Int = 0,
    var wordTileStorage: MutableSet<Int> = mutableSetOf(),
    var disneyHardTileStorage: MutableSet<Int> = mutableSetOf(),
    var disneyMediumTileStorage: MutableSet<Int> = mutableSetOf(),
    var disneyEasyTileStorage: MutableSet<Int> = mutableSetOf(),
    var superheroEasyTileStorage: MutableSet<Int> = mutableSetOf(),

    var disneyEasyWordCount: Int = 0,
    var disneyMediumWordCount: Int = 0,
    var disneyHardWordCount: Int = 0,
    var superheroEasyWordCount: Int = 0,
    var scifiEasyWordCount: Int = 0,

    var disneyEasyGameLives: Int = 3,
    var disneyMediumGameLives: Int = 3,
    var disneyHardGameLives: Int = 3,
    var superheroEasyGameLives: Int = 3,
    var scifiEasyGameLives: Int = 3,

    var wordCount: Int = 0,
    var ifGameIsOver: Boolean = false,
    var isGameOverAndWin: Boolean = false,
    var isGameOverAndLose: Boolean = false,
    var isCorrect: Boolean = false,
    var isWrong: Boolean = false,
    var gameLives: Int = 3,
    var isDyslexicFontOn: Boolean = false,
)


