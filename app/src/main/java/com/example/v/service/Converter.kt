package com.example.v.service

import androidx.room.TypeConverter

class Converters {

    @TypeConverter
    fun fromMutableSet(set: MutableSet<Int>): String {
        return set.joinToString(separator = ",") // Convert set to a comma-separated string
    }

    @TypeConverter
    fun toMutableSet(data: String): MutableSet<Int> {
        return data.split(",").map { it.toInt() }.toMutableSet() // Convert string back to MutableSet<Int>
    }
}