package com.example.coder87.tuitionmanager

import android.media.Image
import java.io.Serializable

data class NotificationTutor(
        val id:String,
        val postedByTutor: String
) : Serializable