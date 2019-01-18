package com.example.coder87.tuitionmanager

import android.app.Activity
import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.os.Handler
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

    var UserId=""
    var UserType=""


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_terms_and_conditions)

    }


    fun backToFirstPage(view: View) {
        startActivity(Intent(this,
                FirstPage::class.java))
    }
    fun okGoHomePage(view: View) {
        if(checkAgreement()){
            val progress = ProgressDialog(this).apply {
                setTitle("Creating Your Account....")
                setCancelable(false)
                setCanceledOnTouchOutside(false)
                show()
            }
           getValues()

            val intent=Intent(this,HomePage::class.java)
            intent.putExtra(emailPhone,UserId)
            intent.putExtra(type,UserType)
            startActivity(intent)
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
        UserType=intent.getStringExtra(type)
        if(UserType=="Student")
        {
            UserId=intent.getStringExtra(emailPhone)
            val ref= FirebaseDatabase.getInstance().getReference("Student").child(UserId)
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
        if(UserType=="Tutor")
        {
            UserId=intent.getStringExtra(emailPhone)
            val ref= FirebaseDatabase.getInstance().getReference("Tutor").child(UserId)
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
            s=intent.getStringExtra(EmailTutor)
            ref.child("Email").setValue(s)
            s=intent.getStringExtra(PhoneTutor)
            ref.child("Phone").setValue(s)
            s=intent.getStringExtra(ExperienceTutor)
            ref.child("Experience").setValue(s)
            s=intent.getStringExtra(ExpectedAreaTutor)
            ref.child("Expected Area").setValue(s)

        }

    }
    fun printToast(s:String){

        val toast = Toast.makeText(this, s, Toast.LENGTH_SHORT)
        toast.show()
    }




}
