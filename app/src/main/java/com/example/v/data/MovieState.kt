package com.example.v.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class MovieState(
    @PrimaryKey val id: Int = 1,
    var userScore: Int = 0,
    var disneyHardTileStorage: MutableSet<Int> = mutableSetOf(),
    var disneyMediumTileStorage: MutableSet<Int> = mutableSetOf(),
    var disneyEasyTileStorage: MutableSet<Int> = mutableSetOf(),
    var superheroEasyTileStorage: MutableSet<Int> = mutableSetOf(),
    var scifiEasyTileStorage: MutableSet<Int> = mutableSetOf(),

    var disneyHardUnlocked: Boolean = false,
    var disneyMediumUnlocked: Boolean = false,
    var superheroEasyUnlocked: Boolean = false,
    var scifiEasyUnlocked: Boolean = false,

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

    var disneyEasyFinished: Boolean = false,
    var disneyMediumFinished: Boolean = false,
    var disneyHardFinished: Boolean = false,
    var superheroEasyFinished: Boolean = false,

    var isDyslexicFontOn: Boolean = false,
)
