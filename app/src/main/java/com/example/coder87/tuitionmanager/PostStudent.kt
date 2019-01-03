package com.example.coder87.tuitionmanager

import java.io.Serializable

data class PostStudent(
        val timePost: String,
        val typePost: String,
        val classPost: String,
        val subjectPost: String,
        val locationPost: String,
        val daysPost:String,
        val universityPost: String,
        val salaryPost:String,
        val thumbUpPost:String,
        val thumbDownPost:String
) : Serializable