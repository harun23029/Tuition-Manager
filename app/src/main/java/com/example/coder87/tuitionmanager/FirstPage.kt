package com.example.coder87.tuitionmanager

import android.app.Activity
import android.content.Intent
import android.net.Uri
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
    fun visitFacebook(view: View){
        val browsIntent = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.google.com/"))
        startActivity(browsIntent)
    }
    override fun onBackPressed() {
        System.exit(0)
    }

}
