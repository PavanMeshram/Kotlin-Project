package com.example.petmate

import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.facebook.login.LoginManager
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import kotlinx.android.synthetic.main.activity_home_page.*

class HomePage : AppCompatActivity() {

    //Google Sign In
    private var mGoogleSignInClient: GoogleSignInClient? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home_page)

        //Google Sign In
        val textNameGoogle = findViewById<TextView>(R.id.textNameGoogle)
        val textEmailGoogle = findViewById<TextView>(R.id.textEmailGoogle)
        val textIdGoogle = findViewById<TextView>(R.id.textIdGoogle)
        //val imageViewGoogle = findViewById<ImageView>(R.id.imageViewGoogle)
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