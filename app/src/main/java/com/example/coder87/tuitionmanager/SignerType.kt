package com.example.coder87.tuitionmanager

import android.app.Activity
import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.widget.*
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
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
            if (rbTutor.isChecked && validateInput())
            {
                val progress = ProgressDialog(this@SignerType).apply {
                    setTitle("Checking Account")
                    setCancelable(false)
                    setCanceledOnTouchOutside(false)

                }
                if(progress!=null) progress.show()
                val firebaseDatabase=FirebaseDatabase.getInstance().getReference("Tutor")
                firebaseDatabase.addListenerForSingleValueEvent(object: ValueEventListener {
                    override fun onCancelled(p0: DatabaseError) {
                    }

                    override fun onDataChange(p0: DataSnapshot) {
                        var hadAccount=false
                        for(h in p0.children){
                            val uname=h.key.toString()
                            if(uname==phoneInput.text.toString()){
                                if(progress!=null) progress.dismiss()
                                printToast("You already have an account")
                                hadAccount=true
                                break
                            }
                        }
                        if(hadAccount==false){
                            if(progress!=null) progress.dismiss()
                            callSignUpTutor()
                        }
                    }

                })

            }
            else if (rbStudent.isChecked && validateInput())
            {
                val progress = ProgressDialog(this@SignerType).apply {
                    setTitle("Checking Account")
                    setCancelable(false)
                    setCanceledOnTouchOutside(false)
                }
                if(progress!=null) progress.show()
                val firebaseDatabase=FirebaseDatabase.getInstance().getReference("Student")
                firebaseDatabase.addListenerForSingleValueEvent(object: ValueEventListener {
                    override fun onCancelled(p0: DatabaseError) {
                    }

                    override fun onDataChange(p0: DataSnapshot) {
                        var hadAccount=false
                        for(h in p0.children){
                            val uname=h.key.toString()
                            if(uname==phoneInput.text.toString()){
                                if(progress!=null) progress.dismiss()
                                printToast("You already have an account")
                                hadAccount=true
                                break
                            }
                        }
                        if(hadAccount==false){
                            if(progress!=null) progress.dismiss()
                            callSignUpStudent()
                        }
                    }

                })
            }
            else if(rbTutor.isChecked==false && rbStudent.isChecked==false ){
                printToast();
            }

    }
    private fun callSignUpTutor(){
        val intent=Intent(this,VerifyNewAccount::class.java)
        intent.putExtra(type,"Tutor")
        intent.putExtra(emailPhone,phoneInput.text.toString())
        intent.putExtra(password,passwordInput.text.toString())
        startActivity(intent)
        finish()
    }
    private fun callSignUpStudent(){
        val intent=Intent(this, VerifyNewAccount::class.java)
        intent.putExtra(type,"Student")
        intent.putExtra(emailPhone,phoneInput.text.toString())
        intent.putExtra(password,passwordInput.text.toString())
        startActivity(intent)
        finish()
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
        if(phoneInput.text.isEmpty()||phoneInput.text.toString().length<11)
        {
            showError(phoneInput,R.string.email_phone_messege)
            allInputsValid=false
        }
        if(passwordInput.text.isEmpty())
        {
            showError(passwordInput,R.string.password_messege)
            allInputsValid=false
        }
        if(passwordInput.text.toString().length<6)
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
    fun printToast(s:String){

        val toast = Toast.makeText(this, s, Toast.LENGTH_SHORT)
        toast.show()
    }
    override fun onBackPressed() {

        finish()
        val intent = Intent(this,FirstPage::class.java)
        startActivity(intent)
    }


}
