package com.example.coder87.tuitionmanager

import java.io.Serializable

data class NotificationStudent(
        val id:String,
        val postedByStudent: String
) : Serializable