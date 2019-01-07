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
import kotlinx.android.synthetic.main.post_demo_student.view.*

class ViewPostStudent : Activity() {
    private var postsStudent=ArrayList<PostStudent>()
    private lateinit var mp: MediaPlayer
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_post_student)
        retrievePostsStudent()
        prepareHomePagePostStudent()
    }
    private fun retrievePostsStudent() {
        // TODO: fetch card list from database/server
        postsStudent.clear()
        postsStudent.add(PostStudent("Golam Mourshid posted 1h ago","Tutor Wanted","Class 10","Physics,Math","Uttara","4 days per week","Preferable University of tutor: DU,BUET","8000TK","0","0"))

    }
    private fun prepareHomePagePostStudent() {
        val cardsRecyclerView: RecyclerView = findViewById(R.id.view_post_student_holder)
        cardsRecyclerView.layoutManager = LinearLayoutManager(this)
        cardsRecyclerView.adapter = PostStudentAdapter(postsStudent)
    }

    inner class PostStudentAdapter(private val cards : ArrayList<PostStudent>) : RecyclerView.Adapter<PostStudentItemViewHolder>() {

        override fun getItemCount(): Int {
            return cards.size
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostStudentItemViewHolder {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.post_demo_student, parent, false)

            return PostStudentItemViewHolder(view)
        }

        override fun onBindViewHolder(holder: PostStudentItemViewHolder, position: Int) {
            val card = cards[position]

            holder.timePost.text = card.timePost
            holder.typePost.text = card.typePost
            holder.locationPost.text = card.locationPost
            holder.subjectPost.text = card.subjectPost
            holder.classPost.text = card.classPost
            holder.daysPost.text = card.daysPost
            holder.salaryPost.text = card.salaryPost
            holder.universityPost.text=card.universityPost
            holder.thumbUpPost.text=card.thumbUpPost
            holder.thumbDownPost.text=card.thumbDownPost

        }
    }


    class PostStudentItemViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        val timePost: TextView =view.post_time_student
        val typePost: TextView =view.post_type_student
        val locationPost: TextView =view.post_location_student
        val classPost: TextView =view.post_class_student
        val subjectPost: TextView =view.post_subjects_student
        val daysPost: TextView =view.post_days_student
        val salaryPost: TextView =view.post_salary_student
        val universityPost: TextView =view.post_university_student
        val thumbUpPost: TextView =view.post_thumbsup_student
        val thumbDownPost: TextView =view.post_thumbsup_student
    }

    fun playSound(view: View) {
        mp = MediaPlayer.create (this, R.raw.like)
        mp.start()
    }
    fun openMap(view: View){
        startActivity(Intent(this,
                Map::class.java))
    }
    fun viewProfileStudent(view: View){
        startActivity(Intent(this,
                ViewProfileStudent::class.java))
    }
}
