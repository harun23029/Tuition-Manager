package com.example.coder87.tuitionmanager

import java.io.Serializable

data class ProfileStudent(
        val name: String,
        val school: String,
        val clas: String,
        val section: String,
        val gender:String,
        val address:String,
        val phoneNo:String
) : Serializable