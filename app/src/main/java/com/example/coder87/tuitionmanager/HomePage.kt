package com.example.coder87.tuitionmanager

import android.content.Intent
import android.media.MediaPlayer
import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.*
import android.widget.ScrollView
import android.widget.TextView
import de.hdodenhof.circleimageview.CircleImageView
import kotlinx.android.synthetic.main.activity_home_page2.*
import kotlinx.android.synthetic.main.notifications_demo_student.view.*
import kotlinx.android.synthetic.main.notifications_demo_tutor.view.*
import kotlinx.android.synthetic.main.post_demo_student.view.*
import kotlinx.android.synthetic.main.post_demo_tutor.view.*
import kotlinx.android.synthetic.main.profile_demo_student.view.*
import kotlinx.android.synthetic.main.profile_demo_teacher.view.*

class HomePage : AppCompatActivity() {
    private lateinit var homePage: ScrollView
    private lateinit var profilePageTutor: ScrollView
    private lateinit var profilePageStudent: ScrollView
    private lateinit var notificationsPageTutor: ScrollView
    private lateinit var notificationsPageStudent:ScrollView
    private lateinit var mp: MediaPlayer

    private lateinit var ppTutor:CircleImageView

    private var selectedSubmenu: Int=1
    private  var signer:String="tutor"

    private var postsTutor=ArrayList<PostTutor>()
    private var postsStudent=ArrayList<PostStudent>()
    private var profileTutor=ArrayList<ProfileTutor>()
    private var profileStudent=ArrayList<ProfileStudent>()
    private var notificationTutor=ArrayList<NotificationTutor>()
    private var notificationStudent=ArrayList<NotificationStudent>()

    private fun bindWidgets()
    {
        homePage=findViewById(R.id.home_page)
        profilePageTutor=findViewById(R.id.profile_page_tutor)
        profilePageStudent=findViewById(R.id.profile_page_student)
        notificationsPageTutor=findViewById(R.id.notifications_page_tutor)
        notificationsPageStudent=findViewById(R.id.notifications_page_student)
    }
     fun createPost(view: View)
    {
        // CHeck who is the user student/tutor
        startActivity(Intent(this,CreatePostTutor::class.java))
    }

    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.navigation_profile -> {
                homePage.visibility=View.INVISIBLE
                notificationsPageTutor.visibility=View.INVISIBLE
                notificationsPageStudent.visibility=View.INVISIBLE
                if(signer=="tutor")
                {
                    retrieveProfileTutor()
                    prepareProfileTutor()
                    profilePageStudent.visibility=View.INVISIBLE
                    profilePageTutor.visibility=View.VISIBLE
                }
                else{
                    retrieveProfileStudent()
                    prepareProfileStudent()
                    profilePageStudent.visibility=View.VISIBLE
                    profilePageTutor.visibility=View.INVISIBLE
                }


                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_home -> {
                profilePageStudent.visibility=View.INVISIBLE
                profilePageTutor.visibility= View.INVISIBLE
                notificationsPageTutor.visibility= View.INVISIBLE
                notificationsPageStudent.visibility=View.INVISIBLE
                homePage.visibility= View.VISIBLE
                if(selectedSubmenu==1){
                    retrievePostsStudent()
                    prepareHomePagePostStudent()
                }
                else{
                    retrievepostsTutor()
                    prepareHomePagePostTutor()
                }

                return@OnNavigationItemSelectedListener true
            }

            R.id.navigation_notifications -> {
                homePage.visibility=View.INVISIBLE
                profilePageTutor.visibility=View.INVISIBLE
                profilePageStudent.visibility=View.INVISIBLE
                if(signer=="tutor")
                {
                    retrieveNotificationTutor()
                    prepareNotificationTutor()
                    notificationsPageTutor.visibility=View.VISIBLE
                }
                else{
                    retrieveNotificationStudent()
                    prepareNotificationStudent()
                    notificationsPageStudent.visibility=View.VISIBLE
                }


                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home_page2)
        bindWidgets()
        retrievepostsTutor()
        prepareHomePagePostTutor()
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.home_page_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_logout -> {
                startActivity(Intent(this,
                        FirstPage::class.java))
                true
            }
            R.id.submenu_tuition-> {
                selectedSubmenu=1
                retrievePostsStudent()
                prepareHomePagePostStudent()
                true
            }
            R.id.submenu_tutor->{
                selectedSubmenu=2
                retrievepostsTutor()
                prepareHomePagePostTutor()
                true
            }
            
            else -> super.onOptionsItemSelected(item)
        }

    }
    private fun retrievepostsTutor() {
        // TODO: fetch card list from database/server
         postsTutor.clear()
         postsTutor.add(PostTutor("Harun-or-Rashid posted 25 mins ago","Tuition Wanted","Mirpur-10,Sewrapara,Kazipara,Taltola","Class 9-12","Physics,Math,ICT","3 days per week","8000-1200 TK","0","0"))
         postsTutor.add(PostTutor("Rajib Hossain posted 10 hour 25 mins ago","Tuition Wanted","Dhanmondi,Jigatole","Class 9-12","Chemistry,Biology","3 days per week","8000-1200 TK","0","0"))
         postsTutor.add(PostTutor("Alamin Hossain posted  just now","Tuition Wanted","Farmgate,Tejgoan","Class 9-12","English,ICT","3 days per week","8000-1200 TK","0","0"))
    }
    private fun prepareHomePagePostTutor() {
        val cardsRecyclerView: RecyclerView = findViewById(R.id.home_page_post_holder)
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

        val timePost: TextView=view.post_time_tutor
        val typePost: TextView=view.post_type_tutor
        val locationPost: TextView=view.post_location_tutor
        val classPost: TextView=view.post_class_tutor
        val subjectPost: TextView=view.post_subjects_tutor
        val daysPost: TextView=view.post_days_tutor
        val salaryPost: TextView=view.post_salary_tutor
        val thumbUpPost:TextView=view.post_thumbsup_tutor
        val thumbDownPost:TextView=view.post_thumbsdown_tutor

    }


    private fun retrievePostsStudent() {
        // TODO: fetch card list from database/server
        postsStudent.clear()
        postsStudent.add(PostStudent("Golam Mourshid posted 1h ago","Tutor Wanted","Class 10","Physics,Math","Uttara","4 days per week","Preferable University of tutor: DU,BUET","8000TK","0","0"))
        postsStudent.add(PostStudent("Golam Mourshid posted 2d ago","Tutor Wanted","Class 10","Physics,Math","Uttara","4 days per week","Preferable University of tutor: DU,BUET","8000TK","0","0"))
        postsStudent.add(PostStudent("Golam Mourshid posted 10 h ago","Tutor Wanted","Class 10","Physics,Math","Uttara","4 days per week","Preferable University of tutor: DU,BUET","8000TK","0","0"))

    }
    private fun prepareHomePagePostStudent() {
        val cardsRecyclerView: RecyclerView = findViewById(R.id.home_page_post_holder)
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

        val timePost: TextView=view.post_time_student
        val typePost: TextView=view.post_type_student
        val locationPost: TextView=view.post_location_student
        val classPost: TextView=view.post_class_student
        val subjectPost: TextView=view.post_subjects_student
        val daysPost: TextView=view.post_days_student
        val salaryPost: TextView=view.post_salary_student
        val universityPost:TextView=view.post_university_student
        val thumbUpPost:TextView=view.post_thumbsup_student
        val thumbDownPost:TextView=view.post_thumbsup_student
    }

    fun playSound(view: View) {
        mp = MediaPlayer.create (this, R.raw.like)
        mp.start()
    }
    fun openMap(view: View){
            startActivity(Intent(this,
                    Map::class.java))
    }
    fun viewProfileTutor(view: View){
        startActivity(Intent(this,
                ViewProfileTutor::class.java))
    }
    fun viewProfileStudent(view: View){
        startActivity(Intent(this,
                ViewProfileStudent::class.java))
    }
    fun viewPostTutor(view: View){
        startActivity(Intent(this,
                ViewPostTutor::class.java))
    }
    fun viewPostStudent(view: View){
        startActivity(Intent(this,
                ViewPostStudent::class.java))
    }


    private fun retrieveProfileTutor() {
        // TODO: fetch card list from database/server
        profileTutor.clear()
        profileTutor.add(ProfileTutor("Harun-or-Rashid","University of Dhaka","CSE","3rd year","4 year experience","Male","441/1F west Sewrapara Mirpur,Dhaka.","01871445680","harunducse23rd@gmail.com"))

    }
    private fun prepareProfileTutor() {
        val cardsRecyclerView: RecyclerView = findViewById(R.id.tutor_profile_holder)
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

        val name: TextView=view.name_profile_tutor
        val university: TextView=view.university_profile_tutor
        val dept: TextView=view.dept_profile_tutor
        val year: TextView=view.year_profile_tutor
        val experience: TextView=view.exp_profile_tutor
        val gender: TextView=view.gender_profile_tutor
        val address: TextView=view.address_profile_tutor
        val phoneNo:TextView=view.phone_profile_tutor
        val email:TextView=view.email_profile_tutor

    }

    private fun retrieveProfileStudent() {
        // TODO: fetch card list from database/server
        profileStudent.clear()
        profileStudent.add(ProfileStudent("Harun-or-Rashid","Dhaka College","12","Science","Male","441/1F west Sewrapara Mirpur,Dhaka.","01871445680"))

    }
    private fun prepareProfileStudent() {
        val cardsRecyclerView: RecyclerView = findViewById(R.id.student_profile_holder)
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

        val name: TextView=view.name_profile_student
        val school: TextView=view.school_profile_student
        val clas: TextView=view.class_profile_student
        val section: TextView=view.section_profile_student
        val gender: TextView=view.gender_profile_student
        val address: TextView=view.address_profile_student
        val phoneNo:TextView=view.phone_profile_student

    }


    private fun retrieveNotificationTutor() {
        // TODO: fetch card list from database/server
        notificationTutor.clear()
        notificationTutor.add(NotificationTutor("Posted by Alamin Hossain"))
        notificationTutor.add(NotificationTutor("Posted by Harun Ujjaman Faisal"))
        notificationTutor.add(NotificationTutor("Posted by Rajib Hossain"))

    }
    private fun prepareNotificationTutor() {
        val cardsRecyclerView: RecyclerView = findViewById(R.id.tutor_notification_holder)
        cardsRecyclerView.layoutManager = LinearLayoutManager(this)
        cardsRecyclerView.adapter = NotificationTutorAdapter(notificationTutor)
    }

    inner class NotificationTutorAdapter(private val cards : ArrayList<NotificationTutor>) : RecyclerView.Adapter<NotificationTutorItemViewHolder>() {

        override fun getItemCount(): Int {
            return cards.size
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotificationTutorItemViewHolder {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.notifications_demo_tutor, parent, false)

            return NotificationTutorItemViewHolder(view)
        }

        override fun onBindViewHolder(holder: NotificationTutorItemViewHolder, position: Int) {
            val card = cards[position]

            holder.postedByTutor.text = card.postedByTutor

        }
    }


    class NotificationTutorItemViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        val postedByTutor: TextView=view.notification_posted_by_tutor
    }

    private fun retrieveNotificationStudent() {
        // TODO: fetch card list from database/server
        notificationStudent.clear()
        notificationStudent.add(NotificationStudent("Posted by Harun Ujjaman Faisal"))
        notificationStudent.add(NotificationStudent("Posted by Harun Ujjaman Faisal"))
        notificationStudent.add(NotificationStudent("Posted by Harun Ujjaman Faisal"))
        notificationStudent.add(NotificationStudent("Posted by Harun Ujjaman Faisal"))
        notificationStudent.add(NotificationStudent("Posted by Harun Ujjaman Faisal"))
        notificationStudent.add(NotificationStudent("Posted by Harun Ujjaman Faisal"))

    }
    private fun prepareNotificationStudent() {
        val cardsRecyclerView: RecyclerView = findViewById(R.id.student_notification_holder)
        cardsRecyclerView.layoutManager = LinearLayoutManager(this)
        cardsRecyclerView.adapter = NotificationStudentAdapter(notificationStudent)
    }

    inner class NotificationStudentAdapter(private val cards : ArrayList<NotificationStudent>) : RecyclerView.Adapter<NotificationStudentItemViewHolder>() {

        override fun getItemCount(): Int {
            return cards.size
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotificationStudentItemViewHolder {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.notifications_demo_student, parent, false)

            return NotificationStudentItemViewHolder(view)
        }

        override fun onBindViewHolder(holder: NotificationStudentItemViewHolder, position: Int) {
            val card = cards[position]

            holder.postedByStudent.text = card.postedByStudent

        }
    }


    class NotificationStudentItemViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        val postedByStudent: TextView=view.notification_posted_by_student


    }





}
