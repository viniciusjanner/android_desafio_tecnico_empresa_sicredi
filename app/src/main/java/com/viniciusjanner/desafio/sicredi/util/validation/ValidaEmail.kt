package com.viniciusjanner.desafio.sicredi.util.validation

import android.widget.EditText
import com.google.android.material.textfield.TextInputLayout

class ValidaEmail(
    private val textInputLayout: TextInputLayout
) : Validator {

    private val editText: EditText = this.textInputLayout.editText!!

    private val patternValidation: PatternValidation = PatternValidation(this.textInputLayout)

    private fun valida(email: String): Boolean {
        if (isEmailValid(email)) {
            return true
        }
        textInputLayout.error = "E-mail inválido"
        return false
    }

    override fun isValid(): Boolean {
        if (!patternValidation.isValid) {
            return false
        }
        val email: String = editText.text.toString()
        return valida(email)
    }

    private fun isEmailValid(email: String): Boolean {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }
}