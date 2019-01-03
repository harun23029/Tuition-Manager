package com.example.coder87.tuitionmanager

import java.io.Serializable

data class PostTutor(
        val timePost: String,
        val typePost: String,
        val locationPost: String,
        val classPost: String,
        val subjectPost: String,
        val daysPost:String,
        val salaryPost:String,
        val thumbUpPost:String,
        val thumbDownPost:String
) : Serializable