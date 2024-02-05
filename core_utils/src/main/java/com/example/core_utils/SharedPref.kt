package com.example.core_utils

import android.content.Context
import android.content.SharedPreferences
import android.media.Image
import android.net.Uri
import android.text.Html.ImageGetter
import android.util.Log

class SharedPref(context: Context) {

    private val sharedPreferences: SharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)

    companion object {
        private const val PREF_NAME = "MyPrefs"
        private const val SAVE_NAME = "nameUser"
        private const val SAVE_NUMBER = "number"
        private const val KEY_HEART_VISIBILITY = "heartVisibility"
    }

    var isSaveNameUser:Boolean
        get() =  sharedPreferences.getBoolean(SAVE_NAME, false)
        set(value) = sharedPreferences.edit().putBoolean(SAVE_NAME, value).apply()


    var savedName: String
        get()  = sharedPreferences.getString("name", "") ?: ""
        set(value)= sharedPreferences.edit().putString("name", value).apply()

    var savedSecondName: String
        get()  = sharedPreferences.getString("secondName", "") ?: ""
        set(value) = sharedPreferences.edit().putString("secondName", value).apply()


    var savedNumber: String
        get()  = sharedPreferences.getString(SAVE_NUMBER, "") ?: ""
        set(value) = sharedPreferences.edit().putString(SAVE_NUMBER, value).apply()

    fun saveHeartVisibility(productId: String, isVisible: Boolean) {
        sharedPreferences.edit().putBoolean("$KEY_HEART_VISIBILITY-$productId", isVisible).apply()
    }


    fun getHeartVisibility(productId: String): Boolean {
        return sharedPreferences.getBoolean("$KEY_HEART_VISIBILITY-$productId", false)
    }

    fun deleteUserInfo() {
        sharedPreferences.edit()
            .remove(SAVE_NAME)
            .remove("name")
            .remove("secondName")
            .remove(SAVE_NUMBER)
            .apply()
    }
}



