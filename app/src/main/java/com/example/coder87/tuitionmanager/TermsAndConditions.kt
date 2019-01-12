package com.example.coder87.tuitionmanager

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.*
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import android.widget.Toast
import com.google.android.gms.tasks.Task
import android.support.annotation.NonNull
import com.google.android.gms.tasks.OnCompleteListener
import java.io.FileReader


class TermsAndConditions : Activity() {
    private lateinit var agreeBox:CheckBox

    private lateinit var rbTutor: RadioButton
    private lateinit var rbStudent: RadioButton
    private lateinit var phoneEmailInput: EditText
    private lateinit var passwordInput: EditText

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





    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_terms_and_conditions)

        getValues()


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

        phoneEmailInput=findViewById(R.id.email_phone_signup)
        passwordInput=findViewById(R.id.password_signup)

        rbTutor = findViewById (R.id.radioButton_tutor)
        rbStudent = findViewById (R.id.radioButton_student)

    }

    fun backToFirstPage(view: View) {
        startActivity(Intent(this,
                FirstPage::class.java))
    }
    fun okGoHomePage(view: View) {
        if(checkAgreement()){
            startActivity(Intent(this,
                    HomePage::class.java))
        }
        else{
            printToast()
        }

    }
    fun checkAgreement():Boolean{
       agreeBox=findViewById(R.id.agree_checkbox)
        val checked: Boolean =agreeBox.isChecked
        if(checked) return true
        return false
    }
    fun printToast(){
        val toast = Toast.makeText(this, "Please check I agree", Toast.LENGTH_SHORT)
        toast.show()
    }
    fun getValues(){
        var s:String
        s=intent.getStringExtra(type)
        if(s=="Student")
        {
            s=intent.getStringExtra(emailPhone)
            val ref= FirebaseDatabase.getInstance().getReference("Student").child(s)
            s=intent.getStringExtra(password)
            ref.child("Password").setValue(s)
            s=intent.getStringExtra(NameStudent)
            ref.child("Name").setValue(s)
            s=intent.getStringExtra(SchoolStudent)
            ref.child("School").setValue(s)
            s=intent.getStringExtra(ClassStudent)
            ref.child("Class").setValue(s)
            s=intent.getStringExtra(SectionStudent)
            ref.child("Section").setValue(s)
            s=intent.getStringExtra(GenderStudent)
            ref.child("Gender").setValue(s)
            s=intent.getStringExtra(AddressStudent)
            ref.child("Address").setValue(s)
            s=intent.getStringExtra(PhoneStudent)
            ref.child("Phone").setValue(s)
        }
        if(s=="Tutor")
        {
            s=intent.getStringExtra(emailPhone)
            val ref= FirebaseDatabase.getInstance().getReference("Tutor").child(s)
            s=intent.getStringExtra(password)
            ref.child("Password").setValue(s)
            s=intent.getStringExtra(NameTutor)
            ref.child("Name").setValue(s)
            s=intent.getStringExtra(UniversityTutor)
            ref.child("University").setValue(s)
            s=intent.getStringExtra(DeptTutor)
            ref.child("Department").setValue(s)
            s=intent.getStringExtra(YearTutor)
            ref.child("Year").setValue(s)
            s=intent.getStringExtra(GenderTutor)
            ref.child("Gender").setValue(s)
            s=intent.getStringExtra(AddressTutor)
            ref.child("Address").setValue(s)
            s=intent.getStringExtra(PhoneTutor)
            ref.child("Phone").setValue(s)
            s=intent.getStringExtra(EmailTutor)
            ref.child("Email").setValue(s)
            s=intent.getStringExtra(ExpectedAreaTutor)
            ref.child("Expected Area").setValue(s)
            s=intent.getStringExtra(ExperienceTutor)
            ref.child("Experience").setValue(s)
        }

    }




}
