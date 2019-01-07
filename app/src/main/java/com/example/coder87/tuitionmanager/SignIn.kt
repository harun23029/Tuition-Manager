package com.example.coder87.tuitionmanager

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.EditText

class SignIn : Activity() {
    private lateinit var phoneInput:EditText
    private lateinit var passwordInput:EditText
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signin)
    }
    private fun bindWidgets()
    {
        phoneInput=findViewById(R.id.email_phone_signin)
        passwordInput=findViewById(R.id.password_signin)
    }
    fun signerType(view: View) {
        startActivity(Intent(this,
                SignerType::class.java))
    }
    fun logIn(view: View) {
        if(validateInput())
        {
            startActivity(Intent(this,
                    HomePage::class.java))
        }

    }
    private fun validateInput(): Boolean {
        var allInputsValid = true
        bindWidgets()
        if(phoneInput.text.isEmpty())
        {
            showError(phoneInput,R.string.email_phone_messege)
            allInputsValid=false
        }
        if(passwordInput.text.isEmpty())
        {
            showError(passwordInput,R.string.password_messege)
            allInputsValid=false
        }
        return allInputsValid
    }
    private fun showError(field: EditText, messageRes: Int) {
        field.error = getString(messageRes)
    }


}
