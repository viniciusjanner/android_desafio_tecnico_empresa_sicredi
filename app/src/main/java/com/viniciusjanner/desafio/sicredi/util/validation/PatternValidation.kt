package com.viniciusjanner.desafio.sicredi.util.validation

import android.content.Context
import android.widget.EditText
import com.google.android.material.textfield.TextInputLayout
import com.viniciusjanner.desafio.sicredi.R

class PatternValidation(
    private val textInputLayout: TextInputLayout
) : Validator {

    private val context: Context = this.textInputLayout.context

    private val editText: EditText = this.textInputLayout.editText!!

    private fun validate(): Boolean {
        val text: String = editText.text.toString()
        if (text.isEmpty()) {
            textInputLayout.error = context.resources.getString(R.string.common_required_field)
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
}
