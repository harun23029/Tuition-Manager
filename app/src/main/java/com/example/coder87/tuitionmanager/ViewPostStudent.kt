package com.example.coder87.tuitionmanager

import android.app.Activity
import android.content.Intent
import android.media.MediaPlayer
import android.os.Bundle
import android.support.v7.widget.CardView
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
import kotlinx.android.synthetic.main.post_demo_student.view.*
import java.text.SimpleDateFormat
import java.util.*

class ViewPostStudent : Activity() {
    private var postsStudent=ArrayList<PostStudent>()
    private lateinit var mp: MediaPlayer
    var signer=""
    var sigenrId=""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_post_student)
        getValues()
        retrievePostsStudent()
        prepareHomePagePostStudent()
    }
    private fun retrievePostsStudent() {

        postsStudent.clear()


        var dataBase= FirebaseDatabase.getInstance().getReference("Tutor Wanted")
        dataBase.addListenerForSingleValueEvent(object: ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {

            }

            override fun onDataChange(p0: DataSnapshot) {

                for(h in p0.children){
                    var name=h.child("Name").getValue().toString()
                    var location=h.child("Location").getValue().toString()
                    var clas=h.child("Class").getValue().toString()
                    var subject=h.child("Subjects").getValue().toString()
                    var day=h.child("Days").getValue().toString()
                    var salary=h.child("Salary").getValue().toString()
                    var university=h.child("University").getValue().toString()
                    var thumbsup=h.child("Thumbs Up").getValue().toString()
                    var thumbsdown=h.child("Thumbs Down").getValue().toString()
                    var id=h.child("Phone").getValue().toString()
                    val postdate=h.child("Date").getValue().toString()

                    val sdf = SimpleDateFormat("dd/M/yyyy hh:mm:ss")
                    val currentDate = sdf.format(Date())



                    postsStudent.add(PostStudent(id,name+"  posted 1h ago","Tutor Wanted",clas,subject,location,day,university,salary,thumbsup,thumbsdown))
                }
                prepareHomePagePostStudent()

            }


        })


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

            holder.thumbUpPost.setOnClickListener {
                playSound()
                val database=FirebaseDatabase.getInstance().getReference("Tutor Wanted").child(card.id)
                database.addListenerForSingleValueEvent(object:ValueEventListener{
                    override fun onCancelled(p0: DatabaseError) {

                    }

                    override fun onDataChange(p0: DataSnapshot) {
                        var thumbsUp=p0.child("Thumbs Up").getValue().toString()
                        var like=thumbsUp.toInt()
                        like++
                        database.child("Thumbs Up").setValue(""+like+"")
                        retrievePostsStudent()
                    }

                })
            }
            holder.thumbDownPost.setOnClickListener {
                playSound()
                val database=FirebaseDatabase.getInstance().getReference("Tutor Wanted").child(card.id)
                database.addListenerForSingleValueEvent(object:ValueEventListener{
                    override fun onCancelled(p0: DatabaseError) {

                    }

                    override fun onDataChange(p0: DataSnapshot) {
                        var thumbsUp=p0.child("Thumbs Down").getValue().toString()
                        var like=thumbsUp.toInt()
                        like++
                        database.child("Thumbs Down").setValue(""+like+"")
                        retrievePostsStudent()
                    }

                })
            }
            holder.viewProfile.setOnClickListener {
                viewProfileStudent(card.id)
            }

        }
    }


    class PostStudentItemViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        val timePost: TextView=view.post_time_student
        val typePost: TextView=view.post_type_student
        val locationPost: TextView=view.post_location_student
        val classPost: TextView=view.post_class_student
        val subjectPost: TextView=view.post_subjects_student
        val daysPost: TextView=view.post_days_student
        val salaryPost: TextView=view.post_salary_student
        val universityPost:TextView=view.post_university_student
        val thumbUpPost:TextView=view.post_thumbsup_student
        val thumbDownPost:TextView=view.post_thumbsdown_student

        val viewProfile: CardView =view.view_profile_student
    }

    fun playSound() {
        mp = MediaPlayer.create (this, R.raw.like)
        mp.start()
    }
    fun openMap(view: View){
        startActivity(Intent(this,
                Map::class.java))
    }
    fun viewProfileStudent(posterId: String){
        val intent=Intent(this,ViewProfileStudent::class.java)
        intent.putExtra(emailPhone,posterId)
        intent.putExtra(type,"Student")
        startActivity(intent)
    }
    fun getValues(){
        signer=intent.getStringExtra(type)
        sigenrId=intent.getStringExtra(emailPhone)
    }
}
