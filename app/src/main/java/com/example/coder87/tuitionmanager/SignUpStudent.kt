package com.example.coder87.tuitionmanager

import android.app.Activity
import android.app.RemoteInput
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.widget.*
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_sign_up_student.*
import java.io.IOException
const val NameStudent="name"
const val SchoolStudent="school"
const val AddressStudent="address"
const val ClassStudent="class"
const val SectionStudent="section"
const val GenderStudent="male"
const val PhoneStudent="phone"

class SignUpStudent : Activity() {

    private lateinit var nameInput: EditText
    private lateinit var schoolInput:EditText
    private lateinit var addressInput:EditText
    private lateinit var classInput:Spinner
    private lateinit var sectionInput: Spinner
    private lateinit var genderInput: Spinner
    private lateinit var phoneInput: EditText


    private var imageview: ImageView? = null
    private var selectButton:Button? =null
    private val GALLERY = 1

    var ep:String=""
    var pass:String=""
    var tp:String=""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up_student)
        selectButton=findViewById(R.id.select_pp__student) as Button
        imageview = findViewById(R.id.pp_student) as ImageView

        selectButton!!.setOnClickListener{ chooseImageFromGallery() }


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
            getValues()
            val intent=Intent(this,TermsAndConditions::class.java)
            intent.putExtra(type,tp)
            intent.putExtra(emailPhone,ep)
            intent.putExtra(password,pass)
            intent.putExtra(NameStudent,nameInput.text.toString())
            intent.putExtra(SchoolStudent,schoolInput.text.toString())
            intent.putExtra(AddressStudent,addressInput.text.toString())
            intent.putExtra(ClassStudent,classInput.selectedItem.toString())
            intent.putExtra(SectionStudent,sectionInput.selectedItem.toString())
            intent.putExtra(GenderStudent,genderInput.selectedItem.toString())
            intent.putExtra(PhoneStudent,phoneInput.text.toString())
            startActivity(intent)
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
    fun getValues(){
        ep=intent.getStringExtra(emailPhone)
        pass=intent.getStringExtra(password)
        tp=intent.getStringExtra(type)
    }





}
