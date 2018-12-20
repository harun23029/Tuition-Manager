package com.example.coder87.tuitionmanager

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View

class CreatePostTutor : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_post_tutor)
    }
    fun backToHome(view: View) {
        startActivity(Intent(this,
                HomePage::class.java))
    }
}
