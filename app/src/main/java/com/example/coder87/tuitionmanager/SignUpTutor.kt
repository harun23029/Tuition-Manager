package com.example.coder87.tuitionmanager

import android.app.Activity
import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.provider.MediaStore
import android.text.Editable
import android.util.Log
import android.view.View
import android.widget.*
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import org.w3c.dom.Text
import java.io.IOException
const val NameTutor="name"
const val UniversityTutor="school"
const val AddressTutor="address"
const val DeptTutor="class"
const val YearTutor="section"
const val GenderTutor="male"
const val PhoneTutor="phone"
const val EmailTutor="harunducse23@gmail.com"
const val ExperienceTutor="10 yr"
const val ExpectedAreaTutor="mirpur"

class SignUpTutor : Activity() {
    private lateinit var nameInput: EditText
    private lateinit var universityInput: EditText
    private lateinit var deptInput: EditText
    private lateinit var yearInput: Spinner
    private lateinit var genderInput: Spinner
    private lateinit var addressInput: EditText
    private lateinit var phoneInput: EditText
    private lateinit var emailInput: EditText
    private lateinit var experienceInput: EditText
    private lateinit var expectedArea: EditText


    private var imageview: ImageView? = null
    private var selectButton: Button? =null
    private val GALLERY = 1

    var ep:String=""
    var pass:String=""
    var tp:String=""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up_tutor)
        getValues()
        selectButton=findViewById(R.id.select_pp__tutor) as Button
        imageview = findViewById(R.id.pp_tutor) as ImageView
        selectButton!!.setOnClickListener{ chooseImageFromGallery() }
    }
    private fun bindWidgets() {
        nameInput=findViewById(R.id.tutor_name_input)
        universityInput=findViewById(R.id.tutor_university_input)
        deptInput=findViewById(R.id.tutor_dept_input)
        yearInput=findViewById(R.id.tutor_year_input)
        genderInput=findViewById(R.id.tutor_gender_input)
        addressInput=findViewById(R.id.tutor_address_input)
        phoneInput=findViewById(R.id.tutor_phone_input)
        emailInput=findViewById(R.id.tutor_email_input)
        experienceInput=findViewById(R.id.tutor_experience_input)
        expectedArea=findViewById(R.id.tutor_expected_area)




    }
     fun signUpTutor(view: View) {

        if (validateInput()) {
            getValues()
            val intent=Intent(this,TermsAndConditions::class.java)
            intent.putExtra(type,tp)
            intent.putExtra(emailPhone,ep)
            intent.putExtra(password,pass)
            intent.putExtra(NameTutor,nameInput.text.toString())
            intent.putExtra(UniversityTutor,universityInput.text.toString())
            intent.putExtra(DeptTutor,deptInput.text.toString())
            intent.putExtra(YearTutor,yearInput.selectedItem.toString())
            intent.putExtra(GenderTutor,genderInput.selectedItem.toString())
            intent.putExtra(AddressTutor,addressInput.text.toString())
            intent.putExtra(EmailTutor,emailInput.text.toString())
            intent.putExtra(PhoneTutor,phoneInput.text.toString())
            intent.putExtra(ExperienceTutor,experienceInput.text.toString())
            intent.putExtra(ExpectedAreaTutor,expectedArea.text.toString())
            startActivity(intent)
        }

    }

    private fun validateInput(): Boolean {
        var allInputsValid = true
        bindWidgets();


        arrayOf(nameInput,universityInput,deptInput,addressInput,phoneInput,emailInput,experienceInput,expectedArea)
                .forEach { input ->
                    if (input.text.isEmpty()) {
                        showError(input, R.string.required)
                        allInputsValid = false
                    }
                }
        return allInputsValid
    }

    private fun showError(field: EditText, messageRes: Int) {
        field.error = getString(messageRes)
    }
    public fun chooseImageFromGallery() {
        val galleryIntent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        startActivityForResult(galleryIntent, GALLERY)
    }



    public override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (data != null)
        {
            getValues()
            val contentURI = data!!.data
            try {
                val bitmap = MediaStore.Images.Media.getBitmap(this.contentResolver, contentURI)
                //saveImage(bitmap)
                imageview!!.setImageBitmap(bitmap)
                val progress = ProgressDialog(this).apply {
                    setTitle("Uploading Picture....")
                    setCancelable(false)
                    setCanceledOnTouchOutside(false)
                    show()
                }

                val firebaseStorage = FirebaseStorage.getInstance()
                var value = 0.0
                var storage = firebaseStorage.getReference().child("Tutor/"+ep+".jpg")
                        storage.putFile(contentURI)
                        .addOnProgressListener { taskSnapshot ->
                            value = (100.0 * taskSnapshot.bytesTransferred) / taskSnapshot.totalByteCount
                            Log.v("value","value=="+value)
                            progress.setMessage("Uploaded.. " + value.toInt() + "%")
                        }
                        .addOnSuccessListener { taskSnapshot -> progress.dismiss()
                            storage.downloadUrl.addOnCompleteListener(){taskSnapshot->
                                var url = taskSnapshot.result.toString()
                                val ref= FirebaseDatabase.getInstance().getReference("Tutor").child(ep)
                                ref.child("Profile Picture").setValue(url)
                            }
                        }
                        .addOnFailureListener{
                            exception -> exception.printStackTrace()
                        }

            }
            catch (e: IOException)
            {
                e.printStackTrace()
            }
        }

    }

    fun getValues(){
        ep=intent.getStringExtra(emailPhone)
        pass=intent.getStringExtra(password)
        tp=intent.getStringExtra(type)

    }
    fun printToast(s:String){

        val toast = Toast.makeText(this, s, Toast.LENGTH_SHORT)
        toast.show()
    }

}
