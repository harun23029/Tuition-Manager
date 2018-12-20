package com.example.coder87.tuitionmanager

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.CheckBox
import android.widget.Toast

class TermsAndConditions : Activity() {
    private lateinit var agreeBox:CheckBox
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
}
