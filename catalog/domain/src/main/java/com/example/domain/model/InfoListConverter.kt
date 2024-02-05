package com.example.domain.model

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class InfoListConverter {
    @TypeConverter
    fun fromString(value: String?): List<Info>? {
        if (value == null) {
            return null
        }
        val listType = object : TypeToken<List<Info>>() {}.type
        return Gson().fromJson(value, listType)
    }

    @TypeConverter
    fun toString(list: List<Info>?): String? {
        if (list == null) {
            return null
        }
        return Gson().toJson(list)
    }

    @TypeConverter
    fun fromStringFeedback(value: String?): Feedback? {
        if (value == null) {
            return null
        }
        val type = object : TypeToken<Feedback>() {}.type
        return Gson().fromJson(value, type)
    }

    @TypeConverter
    fun toStringFeedback(feedback: Feedback?): String? {
        if (feedback == null) {
            return null
        }
        return Gson().toJson(feedback)
    }

    @TypeConverter
    fun fromPrice(price: Price?): String? {
        if (price == null) {
            return null
        }
        return Gson().toJson(price)
    }

    @TypeConverter
    fun toPrice(value: String?): Price? {
        if (value == null) {
            return null
        }
        return Gson().fromJson(value, Price::class.java)
    }

    @TypeConverter
    fun fromStringTagg(value: String?): List<String>? {
        if (value == null) {
            return null
        }
        val listType = object : TypeToken<List<String>>() {}.type
        return Gson().fromJson(value, listType)
    }

    @TypeConverter
    fun toStringTag(list: List<String>?): String? {
        if (list == null) {
            return null
        }
        return Gson().toJson(list)
    }
}