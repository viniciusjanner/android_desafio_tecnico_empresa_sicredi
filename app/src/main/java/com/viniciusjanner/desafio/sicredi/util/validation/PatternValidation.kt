package com.viniciusjanner.desafio.sicredi.util.validation

import android.widget.EditText
import com.google.android.material.textfield.TextInputLayout

class PatternValidation(
    private val textInputLayout: TextInputLayout
) : Validator {

    private val editText: EditText = this.textInputLayout.editText!!

    private fun validate(): Boolean {
        val text: String = editText.text.toString()
        if (text.isEmpty()) {
            textInputLayout.error = REQUIRED_FIELD
            return false
        }
        return true
    }

    override fun isValid(): Boolean {
        if (!validate()) {
            return false
        }
        removeError()
        return true
    }

    private fun removeError() {
        textInputLayout.error = null
        textInputLayout.isErrorEnabled = false
    }

    companion object {
        private const val REQUIRED_FIELD = "Campo obrigatório"
    }
}
