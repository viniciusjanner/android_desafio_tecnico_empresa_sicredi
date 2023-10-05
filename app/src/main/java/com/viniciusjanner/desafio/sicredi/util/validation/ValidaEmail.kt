package com.viniciusjanner.desafio.sicredi.util.validation

import android.content.Context
import android.widget.EditText
import com.google.android.material.textfield.TextInputLayout
import com.viniciusjanner.desafio.sicredi.R

class ValidaEmail(
    private val textInputLayout: TextInputLayout
) : Validator {

    private val context: Context = this.textInputLayout.context

    private val editText: EditText = this.textInputLayout.editText!!

    private val patternValidation: PatternValidation = PatternValidation(this.textInputLayout)

    private fun valida(email: String): Boolean {
        if (isEmailValid(email)) {
            return true
        }
        textInputLayout.error = context.resources.getString(R.string.common_invalid_email)
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
