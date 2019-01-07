package com.example.coder87.tuitionmanager

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View

class CreatePostTuition : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_post_tuition)
    }
    fun backToHome(view: View) {
        startActivity(Intent(this,
                HomePage::class.java))
    }
}
