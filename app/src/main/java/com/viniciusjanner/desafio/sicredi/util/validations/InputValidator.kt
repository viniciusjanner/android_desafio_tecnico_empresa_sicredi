package com.viniciusjanner.desafio.sicredi.util.validations

@Suppress("unused")
class InputValidator {

    companion object {

        fun isValidRequired(text: String?): Boolean =
            text.isNullOrEmpty()

        fun isValidName(text: String?): Boolean =
            text.isNullOrEmpty() || text.trim().length < 2

        fun isValidEmail(text: String?): Boolean =
            text.isNullOrEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(text).matches()
    }
}
