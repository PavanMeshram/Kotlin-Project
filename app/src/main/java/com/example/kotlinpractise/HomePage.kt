package com.example.kotlinpractise

import android.annotation.SuppressLint
import android.app.PendingIntent.getActivity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import com.facebook.*
import com.facebook.login.LoginManager
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import kotlinx.android.synthetic.main.activity_bottom_sheet.*
import kotlinx.android.synthetic.main.activity_home_page.*
import org.w3c.dom.Text
import java.util.jar.Attributes

class HomePage : AppCompatActivity() {

    //Google Sign In
    lateinit var signOut: Button
    var mGoogleSignInClient: GoogleSignInClient? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home_page)

        //Google Sign In
        val textNameGoogle = findViewById<TextView>(R.id.textNameGoogle)
        val textEmailGoogle = findViewById<TextView>(R.id.textEmailGoogle)
        val textIdGoogle = findViewById<TextView>(R.id.textIdGoogle)
        val imageViewGoogle = findViewById<ImageView>(R.id.imageViewGoogle)
        val textTokenGoogle = findViewById<TextView>(R.id.textTokenGoogle)
        //signOut = findViewById<View>(R.id.btn_signOut) as Button

        val acct = GoogleSignIn.getLastSignedInAccount(this)
        if (acct != null) {
            textNameGoogle.text = acct.displayName
            textEmailGoogle.text = acct.email
            textIdGoogle.text = acct.id
            textTokenGoogle.text = acct.idToken
            Log.d("Debug", "Google Token" + textTokenGoogle.text)
        }

        //Facebook Sign In

        val facebookId = intent.getStringExtra("facebook_id")
        //val facebookFirstName = intent.getStringExtra("facebook_first_name")
        //val facebookMiddleName = intent.getStringExtra("facebook_middle_name")
        //val facebookLastName = intent.getStringExtra("facebook_last_name")
        val facebookName = intent.getStringExtra("facebook_name")
        val facebookPicture = intent.getStringExtra("facebook_picture")
        val facebookEmail = intent.getStringExtra("facebook_email")
        val facebookAccessToken = intent.getStringExtra("facebook_access_token")

        facebook_id_textview.text = facebookId
        //facebook_first_name_textview.text = facebookFirstName
        //facebook_middle_name_textview.text = facebookMiddleName
        //facebook_last_name_textview.text = facebookLastName
        facebook_name_textview.text = facebookName
        facebook_profile_picture_url_textview.text = facebookPicture
        facebook_email_textview.text = facebookEmail
        facebook_access_token_textview.text = facebookAccessToken

        btn_signOut.setOnClickListener {
            logout()
        }
    }

    private fun logout() {

        //Google Session out
        mGoogleSignInClient?.signOut()

        //Facebook Session out
        LoginManager.getInstance().logOut()

        finish()
    }
}