package com.example.coder87.tuitionmanager

import android.content.Intent
import android.media.MediaPlayer
import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.CardView
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.*
import android.widget.ImageView
import android.widget.ScrollView
import android.widget.TextView
import android.widget.Toast
import com.bumptech.glide.Glide
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.storage.FirebaseStorage
import de.hdodenhof.circleimageview.CircleImageView
import kotlinx.android.synthetic.main.activity_home_page2.*
import kotlinx.android.synthetic.main.notifications_demo_student.*
import kotlinx.android.synthetic.main.notifications_demo_student.view.*
import kotlinx.android.synthetic.main.notifications_demo_tutor.view.*
import kotlinx.android.synthetic.main.post_demo_student.view.*
import kotlinx.android.synthetic.main.post_demo_tutor.*
import kotlinx.android.synthetic.main.post_demo_tutor.view.*
import kotlinx.android.synthetic.main.profile_demo_student.*
import kotlinx.android.synthetic.main.profile_demo_student.view.*
import kotlinx.android.synthetic.main.profile_demo_teacher.view.*
import java.text.SimpleDateFormat
import java.time.temporal.ChronoUnit
import java.util.*

class HomePage : AppCompatActivity() {
    private lateinit var homePage: ScrollView
    private lateinit var profilePageTutor: ScrollView
    private lateinit var profilePageStudent: ScrollView
    private lateinit var notificationsPageTutor: ScrollView
    private lateinit var notificationsPageStudent:ScrollView
    private lateinit var mp: MediaPlayer






    private var selectedSubmenu: Int=1
    private  var signer:String=""
    private var sigenrId:String=""


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
        if(signer=="Tutor")
        {
            val intent=Intent(this,CreatePostTutor::class.java)
            intent.putExtra(emailPhone,sigenrId)
            startActivity(intent)
        }
        else if(signer=="Student"){
            val intent=Intent(this,CreatePostTuition::class.java)
            intent.putExtra(emailPhone,sigenrId)
            startActivity(intent)
        }
    }

    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.navigation_profile -> {
                homePage.visibility=View.INVISIBLE
                notificationsPageTutor.visibility=View.INVISIBLE
                notificationsPageStudent.visibility=View.INVISIBLE
                if(signer=="Tutor")
                {
                    retrieveProfileTutor()
                    profilePageStudent.visibility=View.INVISIBLE
                    profilePageTutor.visibility=View.VISIBLE
                }
                else{
                    retrieveProfileStudent()
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
                }
                else{
                    retrievepostsTutor()
                }

                return@OnNavigationItemSelectedListener true
            }

            R.id.navigation_notifications -> {
                homePage.visibility=View.INVISIBLE
                profilePageTutor.visibility=View.INVISIBLE
                profilePageStudent.visibility=View.INVISIBLE
                if(signer=="Tutor")
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
        getValues()
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

        postsTutor.clear()
        var dataBase=FirebaseDatabase.getInstance().getReference("Tuition Wanted")
        dataBase.addListenerForSingleValueEvent(object:ValueEventListener{
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
                    var thumbsup=h.child("Thumbs Up").getValue().toString()
                    var thumbsdown=h.child("Thumbs Down").getValue().toString()
                    var phone=h.child("Phone").getValue().toString()
                    val postdate=h.child("Date").getValue().toString()
                    val sdf = SimpleDateFormat("dd/M/yyyy hh:mm:ss")
                    val currentDate = sdf.format(Date())

                    postsTutor.add(PostTutor(phone,name+"  posted 1h ago","Tuition Wanted",location,clas,subject,day,salary,thumbsup,thumbsdown))

                }
                prepareHomePagePostTutor()

            }


        })


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

            holder.thumbUpPost.setOnClickListener {
                playSound()
                val database=FirebaseDatabase.getInstance().getReference("Tuition Wanted").child(card.id)
                database.addListenerForSingleValueEvent(object:ValueEventListener{
                    override fun onCancelled(p0: DatabaseError) {

                    }

                    override fun onDataChange(p0: DataSnapshot) {
                        var thumbsUp=p0.child("Thumbs Up").getValue().toString()
                        var like=thumbsUp.toInt()
                        like++
                        database.child("Thumbs Up").setValue(""+like+"")
                        retrievepostsTutor()
                    }

                })
            }
            holder.thumbDownPost.setOnClickListener {
                playSound()
                val database=FirebaseDatabase.getInstance().getReference("Tuition Wanted").child(card.id)
                database.addListenerForSingleValueEvent(object:ValueEventListener{
                    override fun onCancelled(p0: DatabaseError) {

                    }

                    override fun onDataChange(p0: DataSnapshot) {
                        var thumbsUp=p0.child("Thumbs Down").getValue().toString()
                        var like=thumbsUp.toInt()
                        like++
                        database.child("Thumbs Down").setValue(""+like+"")
                        retrievepostsTutor()
                    }

                })
            }


            holder.viewProfile.setOnClickListener {
                viewProfileTutor(card.id)
            }
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

        val viewProfile:CardView=view.view_profile_tutor

    }


    private fun retrievePostsStudent() {

        postsStudent.clear()


        var dataBase=FirebaseDatabase.getInstance().getReference("Tutor Wanted")
        dataBase.addListenerForSingleValueEvent(object:ValueEventListener{
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

        val viewProfile:CardView=view.view_profile_student
    }

    fun playSound() {
        mp = MediaPlayer.create (this, R.raw.like)
        mp.start()
    }
    fun openMap(view: View){
            startActivity(Intent(this,
                    Map::class.java))
    }
    fun viewProfileTutor(posterId:String){
        val intent=Intent(this,ViewProfileTutor::class.java)
        intent.putExtra(emailPhone,posterId)
        intent.putExtra(type,"Tutor")
        startActivity(intent)
    }
    fun viewProfileStudent(posterId: String){
        val intent=Intent(this,ViewProfileStudent::class.java)
        intent.putExtra(emailPhone,posterId)
        intent.putExtra(type,"Student")
        startActivity(intent)
    }
    fun viewPostTutor(posterId: String){
        val intent=Intent(this,ViewPostTutor::class.java)
        intent.putExtra(emailPhone,posterId)
        intent.putExtra(type,signer)
        startActivity(Intent(intent))
    }
    fun viewPostStudent(posterId: String){
        val intent=Intent(this,ViewPostStudent::class.java)
        intent.putExtra(emailPhone,posterId)
        intent.putExtra(type,signer)
        startActivity(Intent(intent))
    }



    private fun retrieveProfileTutor() {
        profileTutor.clear()
        val firebaseStorage = FirebaseStorage.getInstance()
        var propic = firebaseStorage.getReference().child("Tutor/"+sigenrId+".jpg").downloadUrl
       // Glide.with(this@HomePage).asBitmap().load(propic).into(picture_profile_student)

        var dataBase=FirebaseDatabase.getInstance().getReference(signer).child(sigenrId)
        dataBase.addListenerForSingleValueEvent(object:ValueEventListener{
            override fun onCancelled(p0: DatabaseError) {

            }

            override fun onDataChange(p0: DataSnapshot) {
                var name=p0.child("Name").getValue().toString()
                var university=p0.child("University").getValue().toString()
                var dept=p0.child("Department").getValue().toString()
                var year=p0.child("Year").getValue().toString()
                var exp=p0.child("Experience").getValue().toString()
                var gender=p0.child("Gender").getValue().toString()
                var address=p0.child("Address").getValue().toString()
                var phone=p0.child("Phone").getValue().toString()
                var email=p0.child("Email").getValue().toString()

                profileTutor.add(ProfileTutor(name,university,dept,year,exp,gender,address,phone,email))
                prepareProfileTutor()


            }

        })


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

        profileStudent.clear()
        val firebaseStorage = FirebaseStorage.getInstance()
        var propic = firebaseStorage.getReference().child("Student/"+sigenrId+".jpg").downloadUrl
        //Glide.with(this@HomePage).asBitmap().load(propic).into(picture_profile_student)


        var dataBase=FirebaseDatabase.getInstance().getReference(signer).child(sigenrId)
        dataBase.addListenerForSingleValueEvent(object:ValueEventListener{
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
        notificationTutor.clear()


        var dataBase=FirebaseDatabase.getInstance().getReference(signer).child(sigenrId)
        dataBase.addListenerForSingleValueEvent(object:ValueEventListener{
            override fun onCancelled(p0: DatabaseError) {

            }

            override fun onDataChange(p0: DataSnapshot) {
                var expectedArea=p0.child("Expected Area").getValue().toString()
                var database=FirebaseDatabase.getInstance().getReference("Tutor Wanted")
                database.addListenerForSingleValueEvent(object :ValueEventListener{
                    override fun onCancelled(p0: DatabaseError) {

                    }

                    override fun onDataChange(p0: DataSnapshot) {


                         for(h in p0.children){
                             var address=h.child("Location").getValue().toString()
                             var name=h.child("Name").getValue().toString()
                             var id=h.child("Phone").getValue().toString()
                             var st=StringTokenizer(expectedArea,",")
                             while (st.hasMoreTokens()){
                                 var s=st.nextToken()
                                 if(s in address){
                                     notificationTutor.add(NotificationTutor(id,"Posted by "+name))
                                    break
                                 }
                             }
                         }
                        prepareNotificationTutor()

                    }

                })
            }

        })



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
            holder.notificationCard.setOnClickListener {
                viewPostStudent(card.id)
            }

        }
    }


    class NotificationTutorItemViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        val postedByTutor: TextView=view.notification_posted_by_tutor
        val notificationCard:CardView=view.notification_card_tutor
    }

    private fun retrieveNotificationStudent() {
        notificationStudent.clear()
        var dataBase=FirebaseDatabase.getInstance().getReference(signer).child(sigenrId)
        dataBase.addListenerForSingleValueEvent(object:ValueEventListener{
            override fun onCancelled(p0: DatabaseError) {

            }

            override fun onDataChange(p0: DataSnapshot) {
                var expectedArea=p0.child("Address").getValue().toString()
                var database=FirebaseDatabase.getInstance().getReference("Tuition Wanted")
                database.addListenerForSingleValueEvent(object :ValueEventListener{
                    override fun onCancelled(p0: DatabaseError) {

                    }

                    override fun onDataChange(p0: DataSnapshot) {


                        for(h in p0.children){
                            var address=h.child("Location").getValue().toString()
                            var name=h.child("Name").getValue().toString()
                            var id=h.child("Phone").getValue().toString()
                            var st=StringTokenizer(address,",")
                            while (st.hasMoreTokens()){
                                var s=st.nextToken()
                                if(s in expectedArea){
                                    notificationStudent.add(NotificationStudent(id,"Posted by "+name))
                                    break
                                }

                            }
                        }
                        prepareNotificationStudent()


                    }

                })
            }

        })


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
            holder.notificationCard.setOnClickListener {
                viewPostTutor(card.id)
            }


        }
    }


    class NotificationStudentItemViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        val postedByStudent: TextView=view.notification_posted_by_student
        val notificationCard:CardView=view.notification_card_student


    }
    fun getValues(){
        signer=intent.getStringExtra(type)
        sigenrId=intent.getStringExtra(emailPhone)
    }
    fun printToast(s:String){

        val toast = Toast.makeText(this, s, Toast.LENGTH_SHORT)
        toast.show()
    }






}
