package com.example.coder87.tuitionmanager

import android.app.Activity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import kotlinx.android.synthetic.main.profile_demo_teacher.view.*

class ViewProfileTutor : Activity() {

    private var profileTutor=ArrayList<ProfileTutor>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_profile_tutor)
        retrieveProfileTutor()
        prepareProfileTutor()
    }

    private fun retrieveProfileTutor() {
        // TODO: fetch card list from database/server
        profileTutor.clear()
        profileTutor.add(ProfileTutor("Harun-or-Rashid","University of Dhaka","CSE","3rd year","4 year experience","Male","441/1F west Sewrapara Mirpur,Dhaka.","01871445680","harunducse23rd@gmail.com"))

    }
    private fun prepareProfileTutor() {
        val cardsRecyclerView: RecyclerView = findViewById(R.id.view_tutor_profile_holder)
        cardsRecyclerView.layoutManager = LinearLayoutManager(this)
        cardsRecyclerView.adapter = ProfileTutorAdapter(profileTutor)
    }

    inner class ProfileTutorAdapter(private val cards : ArrayList<ProfileTutor>) : RecyclerView.Adapter<ProfileTutorItemViewHolder>() {

        override fun getItemCount(): Int {
            return cards.size
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProfileTutorItemViewHolder {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.profile_demo_teacher, parent, false)

            return ProfileTutorItemViewHolder(view)
        }

        override fun onBindViewHolder(holder: ProfileTutorItemViewHolder, position: Int) {
            val card = cards[position]

            holder.name.text = card.name
            holder.university.text = card.university
            holder.dept.text = card.dept
            holder.year.text = card.year
            holder.experience.text = card.experience
            holder.gender.text = card.gender
            holder.address.text = card.address
            holder.phoneNo.text=card.phoneNo
            holder.email.text=card.email
        }
    }


    class ProfileTutorItemViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        val name: TextView =view.name_profile_tutor
        val university: TextView =view.university_profile_tutor
        val dept: TextView =view.dept_profile_tutor
        val year: TextView =view.year_profile_tutor
        val experience: TextView =view.exp_profile_tutor
        val gender: TextView =view.gender_profile_tutor
        val address: TextView =view.address_profile_tutor
        val phoneNo: TextView =view.phone_profile_tutor
        val email: TextView =view.email_profile_tutor

    }
}
