package com.example.coder87.tuitionmanager

import android.app.Activity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.storage.FirebaseStorage
import kotlinx.android.synthetic.main.profile_demo_student.view.*

class ViewProfileStudent : Activity() {

    private var profileStudent=ArrayList<ProfileStudent>()
    var signer=""
    var sigenrId=""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_profile_student)
        getValues()
        retrieveProfileStudent()
        prepareProfileStudent()
    }

    private fun retrieveProfileStudent() {

        profileStudent.clear()
        val firebaseStorage = FirebaseStorage.getInstance()
        var propic = firebaseStorage.getReference().child("Student/"+sigenrId+".jpg").downloadUrl
        //Glide.with(this@HomePage).asBitmap().load(propic).into(picture_profile_student)


        var dataBase= FirebaseDatabase.getInstance().getReference(signer).child(sigenrId)
        dataBase.addListenerForSingleValueEvent(object: ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {

            }

            override fun onDataChange(p0: DataSnapshot) {
                var name=p0.child("Name").getValue().toString()
                var school=p0.child("School").getValue().toString()
                var clas=p0.child("Class").getValue().toString()
                var section=p0.child("Section").getValue().toString()
                var gender=p0.child("Gender").getValue().toString()
                var address=p0.child("Address").getValue().toString()
                var phone=p0.child("Phone").getValue().toString()

                profileStudent.add(ProfileStudent(name,school,clas,section,gender,address,phone))
                prepareProfileStudent()


            }

        })

    }
    private fun prepareProfileStudent() {
        val cardsRecyclerView: RecyclerView = findViewById(R.id.view_student_profile_holder)
        cardsRecyclerView.layoutManager = LinearLayoutManager(this)
        cardsRecyclerView.adapter = ProfileStudentAdapter(profileStudent)
    }

    inner class ProfileStudentAdapter(private val cards : java.util.ArrayList<ProfileStudent>) : RecyclerView.Adapter<ProfileStudentItemViewHolder>() {

        override fun getItemCount(): Int {
            return cards.size
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProfileStudentItemViewHolder {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.profile_demo_student, parent, false)

            return ProfileStudentItemViewHolder(view)
        }

        override fun onBindViewHolder(holder: ProfileStudentItemViewHolder, position: Int) {
            val card = cards[position]

            holder.name.text = card.name
            holder.school.text = card.school
            holder.clas.text = card.clas
            holder.section.text = card.section
            holder.gender.text = card.gender
            holder.address.text = card.address
            holder.phoneNo.text=card.phoneNo
        }
    }


    class ProfileStudentItemViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        val name: TextView=view.name_profile_student
        val school: TextView=view.school_profile_student
        val clas: TextView=view.class_profile_student
        val section: TextView=view.section_profile_student
        val gender: TextView=view.gender_profile_student
        val address: TextView=view.address_profile_student
        val phoneNo:TextView=view.phone_profile_student

    }
    fun getValues(){
        signer=intent.getStringExtra(type)
        sigenrId=intent.getStringExtra(emailPhone)
    }

}
