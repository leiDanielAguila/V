package com.example.v.data

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity
data class MovieUiState (@PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    var userInput: String = "",
    var userScore:Int = 0,
    var wordTileStorage: MutableSet<Int> = mutableSetOf(),
    var isGameOverAndWin: Boolean = false,
    var isGameOverAndLose: Boolean = false,
    var isCorrect: Boolean = false,
    var gameLives: Int = 3,
    var isDyslexicFontOn: Boolean = false,
)


