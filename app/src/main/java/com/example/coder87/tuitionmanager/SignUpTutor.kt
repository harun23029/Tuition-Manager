package com.example.coder87.tuitionmanager

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Spinner
import org.w3c.dom.Text
import java.io.IOException

class SignUpTutor : Activity() {
    private lateinit var nameInput: EditText
    private lateinit var universityInput: EditText
    private lateinit var deptInput: EditText
    private lateinit var yearInput: Spinner
    private lateinit var genderInput: Spinner
    private lateinit var addressInput: EditText
    private lateinit var phoneInput: EditText
    private lateinit var emailInput: EditText
    private lateinit var experienceInput: EditText
    private lateinit var expectedArea: EditText

    private var imageview: ImageView? = null
    private var selectButton: Button? =null
    private val GALLERY = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up_tutor)

        selectButton=findViewById(R.id.select_pp__tutor) as Button
        imageview = findViewById(R.id.pp_tutor) as ImageView

        selectButton!!.setOnClickListener{ chooseImageFromGallery() }
    }
    private fun bindWidgets() {
        nameInput=findViewById(R.id.tutor_name_input)
        universityInput=findViewById(R.id.tutor_university_input)
        deptInput=findViewById(R.id.tutor_dept_input)
        yearInput=findViewById(R.id.tutor_year_input)
        genderInput=findViewById(R.id.tutor_gender_input)
        addressInput=findViewById(R.id.tutor_address_input)
        phoneInput=findViewById(R.id.tutor_phone_input)
        emailInput=findViewById(R.id.tutor_email_input)
        experienceInput=findViewById(R.id.tutor_experience_input)
        expectedArea=findViewById(R.id.tutor_expected_area)
    }
     fun signUpTutor(view: View) {
        if (validateInput()) {
            startActivity(Intent(this,
                    TermsAndConditions::class.java))
        }
    }

    private fun validateInput(): Boolean {
        var allInputsValid = true
        bindWidgets();
        arrayOf(nameInput,universityInput,deptInput,addressInput,phoneInput,emailInput,experienceInput,expectedArea)
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
    public fun chooseImageFromGallery() {
        val galleryIntent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        startActivityForResult(galleryIntent, GALLERY)
    }



    public override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (data != null)
        {
            val contentURI = data!!.data
            try {
                val bitmap = MediaStore.Images.Media.getBitmap(this.contentResolver, contentURI)
                //saveImage(bitmap)
                imageview!!.setImageBitmap(bitmap)
            }
            catch (e: IOException)
            {
                e.printStackTrace()
            }
        }

    }



}
