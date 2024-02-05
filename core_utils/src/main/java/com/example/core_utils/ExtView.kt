package com.example.core_utils

import android.R
import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Build
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.RadioButton
import android.widget.Toast
import com.bumptech.glide.Glide
import com.google.android.material.textfield.TextInputLayout
import java.util.regex.Pattern

fun Context.showToast(msg: String) {
    Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
}

fun ImageView.loadImage(image: Int) {
    Glide.with(this).load(image).into(this)
}




