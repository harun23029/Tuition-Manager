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
import de.hdodenhof.circleimageview.CircleImageView
import kotlinx.android.synthetic.main.profile_demo_teacher.view.*

class ViewProfileTutor : Activity() {

    private var profileTutor=ArrayList<ProfileTutor>()
    var signer=""
    var sigenrId=""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_profile_tutor)
        getValues()
        retrieveProfileTutor()
    }

    private fun retrieveProfileTutor() {

        profileTutor.clear()
        val firebaseStorage = FirebaseStorage.getInstance()
        var propic = firebaseStorage.getReference().child("Tutor/"+sigenrId+".jpg").downloadUrl
        // Glide.with(this@HomePage).asBitmap().load(propic).into(picture_profile_student)

        var dataBase= FirebaseDatabase.getInstance().getReference(signer).child(sigenrId)
        dataBase.addListenerForSingleValueEvent(object: ValueEventListener {
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

            val firebase = FirebaseDatabase.getInstance().getReference("Tutor").child(sigenrId)
            firebase.addListenerForSingleValueEvent(object:ValueEventListener{
                override fun onCancelled(p0: DatabaseError) {

                }

                override fun onDataChange(p0: DataSnapshot) {
                    var url=p0.child("Profile Picture").getValue().toString()
                    GlideApp.with(this@ViewProfileTutor).load(url).into(holder.pp);

                }

            })
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
        val pp:CircleImageView=view.picture_profile_tutor

    }
    fun getValues(){
        signer=intent.getStringExtra(type)
        sigenrId=intent.getStringExtra(emailPhone)
    }
}
