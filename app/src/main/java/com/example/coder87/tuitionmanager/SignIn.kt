package com.example.coder87.tuitionmanager

import android.app.Activity
import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.RadioButton
import android.widget.Toast
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_signertype.*
import org.jetbrains.annotations.NotNull
import javax.security.auth.callback.Callback

class SignIn : Activity() {
    private lateinit var phoneInput:EditText
    private lateinit var passwordInput:EditText
    private lateinit var rbTutor:RadioButton
    private lateinit var rbStudent:RadioButton



    var validity=""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signin)
    }
    private fun bindWidgets()
    {
        phoneInput=findViewById(R.id.email_phone_signin)
        passwordInput=findViewById(R.id.password_signin)
        rbTutor=findViewById(R.id.radioButton_tutor_signin)
        rbStudent=findViewById(R.id.radioButton_student_signin)
    }
    fun signerType(view: View) {
        startActivity(Intent(this,
                SignerType::class.java))
    }
    fun logIn(view: View) {
        try{
            if(validateInput())
            {
                checkAccount()
            }
        }catch (ex: NullPointerException){
            print(ex.message)
        }

    }
    fun checkAccount(){
        bindWidgets()
        val progress = ProgressDialog(this).apply {
            setTitle("Logging In....")
            setCancelable(false)
            setCanceledOnTouchOutside(false)
            show()
        }
        validity="false"
        var databaseTutor=FirebaseDatabase.getInstance().getReference("Tutor").child(phoneInput.text.toString())
        val databaseStudent=FirebaseDatabase.getInstance().getReference("Student").child(phoneInput.text.toString())

        var password=""
        if(rbTutor.isChecked){
            databaseTutor.addListenerForSingleValueEvent(object:ValueEventListener{
                override fun onCancelled(p0: DatabaseError) {

                }

                override fun onDataChange(p0: DataSnapshot) {
                    password= p0.child("Password").getValue().toString()
                    if(password==passwordInput.text.toString()){
                        progress.dismiss()
                        callHomePage()
                    }
                    else{
                        progress.dismiss()
                        printToast("Phone number or password didn't match")
                    }

                }

            })
        }
        else if(rbStudent.isChecked){
            databaseStudent.addListenerForSingleValueEvent(object:ValueEventListener{
                override fun onCancelled(p0: DatabaseError) {

                }

                override fun onDataChange(p0: DataSnapshot) {
                    password= p0.child("Password").getValue().toString()
                    if(password==passwordInput.text.toString()){
                        progress.dismiss()
                        callHomePage()
                    }
                    else{
                        progress.dismiss()
                        printToast("Phone number or password didn't match")
                    }

                }

            })
        }
        else{
            printToast("Please select account type")
        }

    }

    private fun validateInput(): Boolean {
        var allInputsValid = true
        bindWidgets()
        if(phoneInput.text.isEmpty()||phoneInput.text.toString().length<11)
        {
            showError(phoneInput,R.string.email_phone_messege)
            allInputsValid=false
        }
        if(passwordInput.text.isEmpty() || passwordInput.text.toString().length<6)
        {
            showError(passwordInput,R.string.password_messege)
            allInputsValid=false
        }
        if(rbTutor.isChecked==false&&rbStudent.isChecked==false){
            printToast("Please select account type")
            allInputsValid=false
        }
        return allInputsValid
    }
    private fun showError(field: EditText, messageRes: Int) {
        field.error = getString(messageRes)
    }

    fun printToast(s:String){

        val toast = Toast.makeText(this, s, Toast.LENGTH_SHORT)
        toast.show()
    }
    fun callHomePage(){

        val intent=Intent(this,HomePage::class.java)
        if(rbTutor.isChecked){
            intent.putExtra(type,"Tutor")
        }
        else if(rbStudent.isChecked){
            intent.putExtra(type,"Student")
        }
        intent.putExtra(emailPhone,phoneInput.text.toString())
        startActivity(intent)
        finish()
    }
    fun forgottenPassword(view: View){
        startActivity(Intent(this,FindAccountToChangePassword::class.java))
    }
    override fun onBackPressed() {

        finish()
        val intent = Intent(this,FirstPage::class.java)
        startActivity(intent)
    }


}
