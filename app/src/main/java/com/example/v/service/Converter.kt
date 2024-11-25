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

    // Convert a Set<String> to a String
    @TypeConverter
    fun fromStringSet(set: Set<String>): String {
        return set.joinToString(separator = ",") // Convert set to a comma-separated string
    }

    // Convert a String back to a Set<String>
    @TypeConverter
    fun toStringSet(data: String): Set<String> {
        return if (data.isNotEmpty()) {
            data.split(",").map { it.trim() }.toSet() // Safely split and trim each string
        } else {
            emptySet() // Return an empty set if the input is empty
        }
    }
}