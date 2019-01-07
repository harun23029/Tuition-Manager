package com.example.coder87.tuitionmanager

import android.app.Activity
import android.content.Intent
import android.media.MediaPlayer
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import kotlinx.android.synthetic.main.post_demo_tutor.view.*

class ViewPostTutor : Activity() {
    private var postsTutor=ArrayList<PostTutor>()
    private lateinit var mp: MediaPlayer
    //private lateinit var deleteButton:TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_post_tutor)
        //deleteButton=findViewById(R.id.delete_button_post_tutor)
        //deleteButton.visibility=View.INVISIBLE
        retrievepostsTutor()
        prepareHomePagePostTutor()
    }

    private fun retrievepostsTutor() {
        // TODO: fetch card list from database/server
        postsTutor.clear()
        postsTutor.add(PostTutor("Harun-or-Rashid posted 25 mins ago","Tuition Wanted","Mirpur-10,Sewrapara,Kazipara,Taltola","Class 9-12","Physics,Math,ICT","3 days per week","8000-1200 TK","0","0"))
    }
    private fun prepareHomePagePostTutor() {
        val cardsRecyclerView: RecyclerView = findViewById(R.id.view_post_tutor_holder)
        cardsRecyclerView.layoutManager = LinearLayoutManager(this)
        cardsRecyclerView.adapter = PostTutorAdapter(postsTutor)
    }

    inner class PostTutorAdapter(private val cards : ArrayList<PostTutor>) : RecyclerView.Adapter<PostTutorItemViewHolder>() {

        override fun getItemCount(): Int {
            return cards.size
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostTutorItemViewHolder {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.post_demo_tutor, parent, false)

            return PostTutorItemViewHolder(view)
        }

        override fun onBindViewHolder(holder: PostTutorItemViewHolder, position: Int) {
            val card = cards[position]

            holder.timePost.text = card.timePost
            holder.typePost.text = card.typePost
            holder.locationPost.text = card.locationPost
            holder.subjectPost.text = card.subjectPost
            holder.classPost.text = card.classPost
            holder.daysPost.text = card.daysPost
            holder.salaryPost.text = card.salaryPost
            holder.thumbUpPost.text=card.thumbUpPost
            holder.thumbDownPost.text=card.thumbDownPost
        }
    }


    class PostTutorItemViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        val timePost: TextView =view.post_time_tutor
        val typePost: TextView =view.post_type_tutor
        val locationPost: TextView =view.post_location_tutor
        val classPost: TextView =view.post_class_tutor
        val subjectPost: TextView =view.post_subjects_tutor
        val daysPost: TextView =view.post_days_tutor
        val salaryPost: TextView =view.post_salary_tutor
        val thumbUpPost: TextView =view.post_thumbsup_tutor
        val thumbDownPost: TextView =view.post_thumbsdown_tutor

    }
    fun viewProfileTutor(view: View){
        startActivity(Intent(this,
                ViewProfileTutor::class.java))
    }
    fun playSound(view: View) {
        mp = MediaPlayer.create (this, R.raw.like)
        mp.start()
    }

}
