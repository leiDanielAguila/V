package com.example.v.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class MovieState(
    @PrimaryKey
    val id: Int = 1,
    var userScore: Int = 0,
    var disneyHardTileStorage: MutableSet<Int> = mutableSetOf(),
    var disneyMediumTileStorage: MutableSet<Int> = mutableSetOf(),
    var disneyEasyTileStorage: MutableSet<Int> = mutableSetOf(),
    var superheroEasyTileStorage: MutableSet<Int> = mutableSetOf(),
    var scifiEasyTileStorage: MutableSet<Int> = mutableSetOf(),

    var disneyMediumUnlocked: Boolean = false,
    var superheroEasyUnlocked: Boolean = false,
    var scifiEasyUnlocked: Boolean = false,

    var isDyslexicFontOn: Boolean = false,
)
