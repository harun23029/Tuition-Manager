package com.example.coder87.tuitionmanager

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.FrameLayout
import android.widget.ScrollView
import kotlinx.android.synthetic.main.activity_home_page2.*

class HomePage : AppCompatActivity() {
    private lateinit var homePage: ScrollView
    private lateinit var profilePage: ScrollView
    private lateinit var notificationsPage: ScrollView

    private fun bindWidgets()
    {
        homePage=findViewById(R.id.home_page)
        profilePage=findViewById(R.id.profile_page)
        notificationsPage=findViewById(R.id.notifications_page)
    }

    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.navigation_home -> {
                bindWidgets()
                profilePage.visibility= View.INVISIBLE
                notificationsPage.visibility= View.INVISIBLE
                homePage.visibility= View.VISIBLE
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_profile -> {
                notificationsPage.visibility=View.INVISIBLE
                homePage.visibility=View.INVISIBLE
                profilePage.visibility=View.VISIBLE
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_notifications -> {
                bindWidgets()
                homePage.visibility=View.INVISIBLE
                profilePage.visibility=View.INVISIBLE
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

}
