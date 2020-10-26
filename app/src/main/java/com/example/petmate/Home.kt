package com.example.petmate

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.FrameLayout
import android.widget.Toolbar
import androidx.appcompat.app.ActionBar
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.fragment.app.Fragment
import com.example.petmate.fragments.*
import com.google.android.material.bottomnavigation.BottomNavigationMenu
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.activity_home.*

class Home : AppCompatActivity() {

    lateinit var toolBar: ActionBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        supportActionBar?.let { toolBar = it }

        toolBar.title = getString(R.string.feed)
        switchContent(FeedFragment())

        bottom_navigation_homepage.setOnNavigationItemSelectedListener(object :
            BottomNavigationView.OnNavigationItemSelectedListener {
            override fun onNavigationItemSelected(item: MenuItem): Boolean {
                when (item.itemId) {
                    R.id.menuFeed -> {
                        toolBar.title = getString(R.string.feed)
                        switchContent(FeedFragment())
                        return true
                    }

                    R.id.menuTinder -> {
                        toolBar.title = getString(R.string.tinder)
                        switchContent(TinderFragment())
                        return true
                    }

                    R.id.menuAdd -> {
                        toolBar.title = getString(R.string.add)
                        switchContent(AddFragment())
                        return true
                    }

                    R.id.menuAdopt -> {
                        toolBar.title = getString(R.string.adopt)
                        switchContent(AdoptFragment())
                        return true
                    }

                    R.id.menuFood -> {
                        toolBar.title = getString(R.string.food)
                        switchContent(FoodFragment())
                        return true
                    }
                }
                return false
            }

        })
    } //end of onCreate Method

    private fun switchContent(fragment: Fragment) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.fl_wrapper_homepage, fragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }
} //end of AppCompatActivity