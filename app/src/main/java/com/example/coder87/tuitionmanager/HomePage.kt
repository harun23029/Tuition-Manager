package com.example.coder87.tuitionmanager

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.*
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.ScrollView
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_home_page2.*
import kotlinx.android.synthetic.main.post_demo_tutor.view.*

class HomePage : AppCompatActivity() {
    private lateinit var homePage: ScrollView
    private lateinit var profilePageTutor: ScrollView
    private lateinit var profilePageStudent: ScrollView
    private lateinit var notificationsPage: ScrollView

    private  var signer:String="tutor"

    private var posts=ArrayList<PostTutor>()

    private fun bindWidgets()
    {
        homePage=findViewById(R.id.home_page)
        profilePageTutor=findViewById(R.id.profile_page_tutor)
        profilePageStudent=findViewById(R.id.profile_page_student)
        notificationsPage=findViewById(R.id.notifications_page)
    }

    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.navigation_profile -> {
                homePage.visibility=View.INVISIBLE
                notificationsPage.visibility=View.INVISIBLE
                if(signer=="tutor")
                {
                    profilePageStudent.visibility=View.INVISIBLE
                    profilePageTutor.visibility=View.VISIBLE
                }
                else{
                    profilePageStudent.visibility=View.VISIBLE
                    profilePageTutor.visibility=View.INVISIBLE
                }


                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_home -> {
                bindWidgets()
                profilePageStudent.visibility=View.INVISIBLE
                profilePageTutor.visibility= View.INVISIBLE
                notificationsPage.visibility= View.INVISIBLE
                homePage.visibility= View.VISIBLE
                retrievePosts()
                prepareHomePagePost()
                return@OnNavigationItemSelectedListener true
            }

            R.id.navigation_notifications -> {
                bindWidgets()
                homePage.visibility=View.INVISIBLE
                profilePageTutor.visibility=View.INVISIBLE
                profilePageStudent.visibility=View.INVISIBLE
                notificationsPage.visibility=View.VISIBLE
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home_page2)
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.home_page_icon_back, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_logout -> {
                startActivity(Intent(this,
                        FirstPage::class.java))
                true
            }
            else -> super.onOptionsItemSelected(item)
        }

    }
    private fun retrievePosts() {
        // TODO: fetch card list from database/server
         posts.clear()
         posts.add(PostTutor("Harun-or-Rashid posted 25 mins ago","Tuition Wanted","Mirpur-10,Sewrapara,Kazipara,Taltola","Class 9-12","Physics,Math,ICT","3 days per week","8000-1200 TK"))
         posts.add(PostTutor("Rajib Hossain posted 10 hour 25 mins ago","Tuition Wanted","Dhanmondi,Jigatole","Class 9-12","Chemistry,Biology","3 days per week","8000-1200 TK"))
         posts.add(PostTutor("Alamin Hossain posted  just now","Tuition Wanted","Farmgate,Tejgoan","Class 9-12","English,ICT","3 days per week","8000-1200 TK"))
    }
    private fun prepareHomePagePost() {
        val cardsRecyclerView: RecyclerView = findViewById(R.id.home_page_post_holder)
        cardsRecyclerView.layoutManager = LinearLayoutManager(this)
        cardsRecyclerView.adapter = PostTutorAdapter(posts)
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
            holder.locationPost.text = card.locatioPost
            holder.subjectPost.text = card.subjectPost
            holder.classPost.text = card.classPost
            holder.daysPost.text = card.daysPost
            holder.salaryPost.text = card.salaryPost
        }
    }


    class PostTutorItemViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        val timePost: TextView=view.post_time_tutor
        val typePost: TextView=view.post_type_tutor
        val locationPost: TextView=view.post_location_tutor
        val classPost: TextView=view.post_class_tutor
        val subjectPost: TextView=view.post_subjects_tutor
        val daysPost: TextView=view.post_days_tutor
        val salaryPost: TextView=view.post_salary_tutor

    }


}
