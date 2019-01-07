package com.example.coder87.tuitionmanager

import android.graphics.Picture
import java.io.Serializable

data class ProfileTutor(
        val name: String,
        val university: String,
        val dept: String,
        val year: String,
        val experience:String,
        val gender:String,
        val address:String,
        val phoneNo:String,
        val email: String
) : Serializable