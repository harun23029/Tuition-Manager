package com.example.coder87.tuitionmanager

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.EditText
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*

class CreatePostTutor : Activity() {

    var signer=""
    var signerId=""

    private lateinit var locations:EditText
    private lateinit var classes:EditText
    private lateinit var subjects:EditText
    private lateinit var days:EditText
    private lateinit var salary:EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_post_tutor)
        getValues()
    }
    fun bindWidgets(){
        locations=findViewById(R.id.create_post_tutor_location)
        classes=findViewById(R.id.create_post_tutor_class)
        subjects=findViewById(R.id.create_post_tutor_subject)
        days=findViewById(R.id.create_post_tutor_days)
        salary=findViewById(R.id.create_post_tutor_salary)
    }
    fun backToHome(view: View){
        val intent=Intent(this,HomePage::class.java)
        intent.putExtra(emailPhone,signerId)
        intent.putExtra(type,signer)
        startActivity(intent)
    }
    fun getValues(){
        signer="Tutor"
        signerId=intent.getStringExtra(emailPhone)
    }
    fun savePost(view: View){
        bindWidgets()
        val database=FirebaseDatabase.getInstance().getReference("Tutor").child(signerId)
        val ref= FirebaseDatabase.getInstance().getReference("Tuition Wanted").child(signerId)
        database.addListenerForSingleValueEvent(object: ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {

            }

            override fun onDataChange(p0: DataSnapshot) {
                var name=p0.child("Name").getValue().toString()

                ref.child("Name").setValue(name)
                ref.child("Location").setValue(locations.text.toString())
                ref.child("Class").setValue(classes.text.toString())
                ref.child("Subjects").setValue(subjects.text.toString())
                ref.child("Days").setValue(days.text.toString())
                ref.child("Salary").setValue(salary.text.toString())
                ref.child("Thumbs Up").setValue(0)
                ref.child("Thumbs Down").setValue(0)
                ref.child("Phone").setValue(signerId)

                val sdf = SimpleDateFormat("dd/M/yyyy hh:mm:ss")
                val currentDate = sdf.format(Date())
                ref.child("Date").setValue(currentDate)

                goHome()




            }

        })
    }
    fun goHome(){
        val intent=Intent(this,HomePage::class.java)
        intent.putExtra(emailPhone,signerId)
        intent.putExtra(type,signer)
        startActivity(intent)
    }
}
