package com.example.coder87.tuitionmanager

import android.app.Activity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import kotlinx.android.synthetic.main.profile_demo_student.view.*

class ViewProfileStudent : Activity() {

    private var profileStudent=ArrayList<ProfileStudent>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_profile_student)
        retrieveProfileStudent()
        prepareProfileStudent()
    }

    private fun retrieveProfileStudent() {
        // TODO: fetch card list from database/server
        profileStudent.clear()
        profileStudent.add(ProfileStudent("Harun-or-Rashid","Dhaka College","12","Science","Male","441/1F west Sewrapara Mirpur,Dhaka.","01871445680"))

    }
    private fun prepareProfileStudent() {
        val cardsRecyclerView: RecyclerView = findViewById(R.id.view_student_profile_holder)
        cardsRecyclerView.layoutManager = LinearLayoutManager(this)
        cardsRecyclerView.adapter = ProfileStudentAdapter(profileStudent)
    }

    inner class ProfileStudentAdapter(private val cards : ArrayList<ProfileStudent>) : RecyclerView.Adapter<ProfileStudentItemViewHolder>() {

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

        val name: TextView =view.name_profile_student
        val school: TextView =view.school_profile_student
        val clas: TextView =view.class_profile_student
        val section: TextView =view.section_profile_student
        val gender: TextView =view.gender_profile_student
        val address: TextView =view.address_profile_student
        val phoneNo: TextView =view.phone_profile_student

    }
}
