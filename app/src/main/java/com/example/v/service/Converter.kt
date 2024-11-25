package com.example.v.service

import androidx.room.TypeConverter

class Converters {

    @TypeConverter
    fun fromMutableSet(set: MutableSet<Int>): String {
        return set.joinToString(separator = ",") // Convert set to a comma-separated string
    }

    @TypeConverter
    fun toMutableSet(data: String): MutableSet<Int> {
        return if (data.isNotEmpty()) {
            data.split(",").mapNotNull {
                // Safely parse each element, ignoring invalid or empty values
                it.toIntOrNull()
            }.toMutableSet()
        } else {
            mutableSetOf() // Return empty set if the input is empty
        }
    }
}