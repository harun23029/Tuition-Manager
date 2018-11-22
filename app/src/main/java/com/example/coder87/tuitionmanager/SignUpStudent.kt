package com.example.coder87.tuitionmanager

import android.app.Activity
import android.app.RemoteInput
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.Spinner

class SignUpStudent : Activity() {

    private lateinit var nameInput: EditText
    private lateinit var schoolInput:EditText
    private lateinit var addressInput:EditText
    private lateinit var classInput:Spinner
    private lateinit var sectionInput: Spinner
    private lateinit var genderInput: Spinner
    private lateinit var phoneInput: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up_student)
    }

    private fun bindWidgets() {
        nameInput=findViewById(R.id.student_name_input)
        schoolInput=findViewById(R.id.student_school_input)
        classInput=findViewById(R.id.student_class_input)
        sectionInput=findViewById(R.id.student_section_input)
        genderInput=findViewById(R.id.student_gender_input)
        addressInput=findViewById(R.id.student_address_input)
        phoneInput=findViewById(R.id.student_phone_input)
    }
    fun signUpStudent(view: View) {
        if (validateInput()) {
            startActivity(Intent(this,
                    HomePage::class.java))
        }
    }
    private fun validateInput(): Boolean {
        var allInputsValid = true
        bindWidgets();
        arrayOf(nameInput,schoolInput,addressInput,phoneInput)
                .forEach { input ->
                    if (input.text.isEmpty()) {
                        showError(input, R.string.required)
                        allInputsValid = false
                    }
                }

        return allInputsValid
    }

    private fun showError(field: EditText, messageRes: Int) {
        field.error = getString(messageRes)
    }
}
