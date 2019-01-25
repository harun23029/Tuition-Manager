package com.example.coder87.tuitionmanager

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import com.google.firebase.database.FirebaseDatabase

class FirstPage : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_firstpage)
    }
    fun signIn(view: View) {
        startActivity(Intent(this,
                SignIn::class.java))
        finish()
    }
    fun signerType(view: View) {
        startActivity(Intent(this,
                SignerType::class.java))
        finish()
    }
    override fun onBackPressed() {
        System.exit(0)
    }

}
