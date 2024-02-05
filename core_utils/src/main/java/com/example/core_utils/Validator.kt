package com.example.core_utils

import android.text.Editable
import android.text.TextWatcher
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout


fun validateName(nameEditText: TextInputEditText, nameLayout: TextInputLayout): Boolean {
    val namePattern = Regex("[а-яА-ЯёЁ]+")

    return when {
        nameEditText.text.toString().trim().isEmpty() -> {
            nameLayout.error = "Ошибка"
            false
        }

        !nameEditText.text.toString().trim().matches(namePattern) -> {
            nameLayout.error = "Имя и фамилия должны содержать только кирилицу"
            false
        }

        else -> {
            nameLayout.error = null
            true
        }
    }
}

fun getTextWatcherForName(nameEditText: TextInputEditText, nameLayout: TextInputLayout): TextWatcher {
    return object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

        override fun afterTextChanged(s: Editable?) {
            validateName(nameEditText, nameLayout)
        }
    }
}


fun getTextWatcherForPhoneNumber(phoneEditText: TextInputEditText, phoneLayout: TextInputLayout): TextWatcher {
    return object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

        override fun afterTextChanged(s: Editable?) {
            validatePhoneNumber(phoneEditText, phoneLayout)
        }
    }
}

fun validatePhoneNumber(phone: TextInputEditText, phoneLayout: TextInputLayout): Boolean {
    val phonePattern = Regex("^\\+?7[0-9]{10}$")

    var phoneNumber = phone.text.toString().trim()

    // Если номер телефона не начинается с "+7", добавим его
    if (!phoneNumber.startsWith("+7")) {
        phoneNumber = "+7$phoneNumber"
        phone.setText(phoneNumber)
        phone.setSelection(phoneNumber.length)  // Установка курсора в конец
    }

    return when {
        phoneNumber.isEmpty() -> {
            phoneLayout.error = "Ошибка"
            false
        }

        !phoneNumber.matches(phonePattern) -> {
            phoneLayout.error = "Некорректный номер телефона"
            false
        }

        else -> {
            phoneLayout.error = null
            true
        }
    }
}