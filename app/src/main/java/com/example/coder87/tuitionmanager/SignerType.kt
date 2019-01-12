package com.example.coder87.tuitionmanager

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.widget.*
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_signertype.*
import java.io.File
import java.io.FileWriter
const val emailPhone="email"
const val password="password"
const val type="signerType"

class SignerType : Activity() {
    private lateinit var nextButton: Button
    private lateinit var rbTutor: RadioButton
    private lateinit var rbStudent: RadioButton
    private lateinit var phoneInput:EditText
    private lateinit var passwordInput:EditText
    private lateinit var confirmPassInput:EditText


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signertype)
    }

    fun signUp(view: View) {
         nextButton=findViewById (R.id.next_button_signer_type)
         rbTutor = findViewById (R.id.radioButton_tutor)
         rbStudent = findViewById (R.id.radioButton_student)

        bindWidgets()

        nextButton.setOnClickListener {
            if (rbTutor.isChecked && validateInput())
            {
                val intent=Intent(this,SignUpTutor::class.java)
                intent.putExtra(type,"Tutor")
                intent.putExtra(emailPhone,email_phone_signup.text.toString())
                intent.putExtra(password,password_signup.text.toString())
                startActivity(intent)
            }
            else if (rbStudent.isChecked && validateInput())
            {
                val intent=Intent(this, SignUpStudent::class.java)
                intent.putExtra(type,"Student")
                intent.putExtra(emailPhone,email_phone_signup.text.toString())
                intent.putExtra(password,password_signup.text.toString())
                startActivity(intent)
            }
            else{
                printToast();
            }

        }
    }
    private fun bindWidgets()
    {
        phoneInput=findViewById(R.id.email_phone_signup)
        passwordInput=findViewById(R.id.password_signup)
        confirmPassInput=findViewById(R.id.confirm_password_signup)
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
        if(passwordInput.text.toString()!=confirmPassInput.text.toString())
        {
            showError(confirmPassInput,R.string.confirm_password_messege)
            allInputsValid=false
        }
        return allInputsValid
    }
    private fun showError(field: EditText, messageRes: Int) {
        field.error = getString(messageRes)
    }
    fun printToast(){

        val toast = Toast.makeText(this, "Please select an option",Toast.LENGTH_SHORT)
        toast.show()
    }


}
