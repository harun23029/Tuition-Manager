package com.example.coder87.tuitionmanager

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View

class FirstPage : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_firstpage)
    }
    fun signIn(view: View) {
        startActivity(Intent(this,
                SignIn::class.java))
    }
    fun signerType(view: View) {
        startActivity(Intent(this,
                SignerType::class.java))
    }
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_first_page, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.credit_first_page -> {
                startActivity(Intent(this,
                        Credits::class.java))
                true
            }
            else -> super.onOptionsItemSelected(item)
        }

    }
}
