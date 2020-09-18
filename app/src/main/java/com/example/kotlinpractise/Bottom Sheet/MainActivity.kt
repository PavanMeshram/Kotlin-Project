package com.example.kotlinpractise

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.facebook.*
import com.facebook.login.LoginManager
import com.facebook.login.LoginResult
import com.facebook.login.widget.LoginButton
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.SignInButton
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.google.android.material.bottomsheet.BottomSheetBehavior
import kotlinx.android.synthetic.main.activity_bottom_sheet.*
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    //Bottom Sheet Behavior
    private lateinit var bottomSheetBehavior: BottomSheetBehavior<LinearLayout>

    //Google Sign In
    lateinit var gso: GoogleSignInOptions
    lateinit var mGoogleSignInClient: GoogleSignInClient
    val RC_SIGN_IN: Int = 123
    lateinit var signIn: SignInButton

    //Facebook Sign In
    lateinit var callbackManager: CallbackManager
    var id = ""

    //var firstName = ""
    //var middleName = ""
    //var lastName = ""
    var name = ""
    var picture = ""
    var email = ""
    var accessToken = ""

    @SuppressLint("WrongViewCast")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //Bottom Sheet Behavior
        bottomSheetBehavior = BottomSheetBehavior.from(bottom_sheet)

        button_persistent_sheet.setOnClickListener {
            bottomSheetBehavior.state =
                if (bottomSheetBehavior.state == BottomSheetBehavior.STATE_EXPANDED)
                    BottomSheetBehavior.STATE_COLLAPSED else BottomSheetBehavior.STATE_EXPANDED
        }

        bottomSheetBehavior.addBottomSheetCallback(object :
            BottomSheetBehavior.BottomSheetCallback() {
            override fun onSlide(bottomSheet: View, slideOffset: Float) {

            }

            override fun onStateChanged(bottomSheet: View, newState: Int) {
                button_persistent_sheet.text = when (newState) {
                    BottomSheetBehavior.STATE_EXPANDED -> "Close Persistent Bottom Sheet"
                    BottomSheetBehavior.STATE_COLLAPSED -> "Open Persistent Bottom Sheet"
                    else -> "Bottom Sheet State Changing"
                }
            }

        })

        button_dialog_sheet.setOnClickListener {
            MyDialogBottomSheet().show(
                supportFragmentManager,
                ""
            )
        }

        //Google Sign In
        signIn = findViewById(R.id.sign_in_button)

        //Configure sign-in to request the user's ID, email address, and basic
        //profile. ID and basic profile are included in DEFAULT_SIGN_IN.
        gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestEmail()
            .build()

        //Build a GoogleSignInCLient with the options specified by gso
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso)

        sign_in_button.setSize(SignInButton.SIZE_WIDE)
        signIn.setOnClickListener { view: View? ->
            signIn()
        }

        //Facebook Sign In
        callbackManager = CallbackManager.Factory.create()

        //LoginManager.getInstance()
        //  .logInWithReadPermissions(this, Arrays.asList("public_profile", "email"))

        if (isLoggedIn()) {
            //fbLogin()
            Log.d("LoggedIn?: ", "YES")
            //show the activity with the logged in user
        } else {
            //Toast.makeText(this@MainActivity, "Login with Facebook", Toast.LENGTH_LONG)
            //  .show()
            Log.d("LoggedIn?: ", "NO")
            //show the Home Activity
        }

        //val accessToken = AccessToken.getCurrentAccessToken()
        //val isLoggedIn = accessToken != null && !accessToken.isExpired
        //val loginButtonFB = findViewById<Button>(R.id.facebook_login_btn)

        facebook_login_btn.setOnClickListener {
            //facebookLogin()
            LoginManager.getInstance()
                .logInWithReadPermissions(this, listOf("public_profile", "email"))
        }

        //Register Callback
        LoginManager.getInstance()
            .registerCallback(callbackManager, object : FacebookCallback<LoginResult?> {
                override fun onSuccess(loginResult: LoginResult?) {
                    Log.d("TAG", "Success Login")
                    getUserProfile(loginResult?.accessToken, loginResult?.accessToken?.userId)
                    //fbLogin()
                }

                override fun onCancel() {
                    Toast.makeText(this@MainActivity, "Login Cancelled", Toast.LENGTH_LONG).show()
                }

                override fun onError(exception: FacebookException) {
                    Toast.makeText(this@MainActivity, exception.message, Toast.LENGTH_LONG).show()
                }
            })
    } //close onCreate Method

    //Tinder
    fun btn_tinder(view: View) {
        val intent = Intent(this, Tinder::class.java)
        startActivity(intent)
    }

    //Google Sign In
    private fun signIn() {
        val signInIntent: Intent = mGoogleSignInClient.signInIntent
        startActivityForResult(signInIntent, RC_SIGN_IN)
    }

    public override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        //Result returned from launching the Intent from GoogleSignInClient.getSignInIntent(...)
        if (requestCode == RC_SIGN_IN) {
            //The task returened from this call is always completed, no need to attach a listener
            val task: Task<GoogleSignInAccount> = GoogleSignIn.getSignedInAccountFromIntent(data)
            handleSignInResult(task)
        } else {
            callbackManager.onActivityResult(requestCode, resultCode, data)
            //callbackManager?.onActivityResult(requestCode, resultCode, data)
            Log.d("Lets See", "malsehnnnn" + data)
        }
    }

    private fun handleSignInResult(completedTask: Task<GoogleSignInAccount>) {
        try {
            val account: GoogleSignInAccount? = completedTask.getResult(ApiException::class.java)
            val intent = Intent(this, HomePage::class.java)
            startActivity(intent)
        } catch (e: ApiException) {
            Log.v("Error", "signInResult:failed code=" + e.statusCode)
        }
    }

    //Facebook Login
    @SuppressLint("LongLogTag")
    fun getUserProfile(token: AccessToken?, userId: String?) {
        val parameters = Bundle()
        parameters.putString(
            "fields",
            "id, name, picture, email"
        )
        GraphRequest(token,
            "/$userId",
            parameters,
            HttpMethod.GET,
            GraphRequest.Callback { response ->
                val jsonObject = response.jsonObject

                //Facebook Access Token
                //You can see Access token only in Debug mode.
                //You can't see it in Logcat using Log.d, Facebook did that to avoid leaking user's access token.
                if (BuildConfig.DEBUG) {
                    FacebookSdk.setIsDebugEnabled(true)
                    FacebookSdk.addLoggingBehavior(LoggingBehavior.INCLUDE_ACCESS_TOKENS)
                }
                accessToken = token.toString()

                //Facebook Id
                if (jsonObject.has("id")) {
                    val facebookId = jsonObject.getString("id")
                    Log.i("Facebook Id: ", facebookId.toString())
                    id = facebookId.toString()
                } else {
                    Log.i("Facebook Id: ", "Not exists")
                    id = "Not exists"
                }

                //Facebook First Name
                /*if (jsonObject.has("first_name")) {
                    val facebookFirstName = jsonObject.getString("first_name")
                    Log.i("Facebook First Name: ", facebookFirstName)
                    firstName = facebookFirstName
                } else {
                    Log.i("Facebook First Name: ", "Not exists")
                    firstName = "Not exists"
                }*/

                //Facebook Middle Name
                /*if (jsonObject.has("middle_name")) {
                    val facebookMiddleName = jsonObject.getString("middle_name")
                    Log.i("Facebook Middle Name: ", facebookMiddleName)
                    middleName = facebookMiddleName
                } else {
                    Log.i("Facebook Middle Name: ", "Not exists")
                    middleName = "Not exists"
                }*/

                //Facebook Last Name
                /*if (jsonObject.has("last_name")) {
                    val facebookLastName = jsonObject.getString("last_name")
                    Log.i("Facebook Last Name: ", facebookLastName)
                    lastName = facebookLastName
                } else {
                    Log.i("Facebook Last Name: ", "Not exists")
                    lastName = "Not exists"
                }*/

                //Facebook Name
                if (jsonObject.has("name")) {
                    val facebookName = jsonObject.getString("name")
                    Log.i("Facebook Name: ", facebookName)
                    name = facebookName
                } else {
                    Log.i("Facebook Name: ", "Not exists")
                    name = "Not exists"
                }

                //Facebook Profile Pic URL
                if (jsonObject.has("picture")) {
                    val facebookPictureObject = jsonObject.getJSONObject("picture")
                    if (facebookPictureObject.has("data")) {
                        val facebookDataObject = facebookPictureObject.getJSONObject("data")
                        if (facebookDataObject.has("url")) {
                            val facebookProfilePicURL = facebookDataObject.getString("url")
                            Log.i("FacebookProfilePicURL:", facebookProfilePicURL)
                            picture = facebookProfilePicURL
                        }
                    }
                } else {
                    Log.i("FacebookProfilePicURL:", "Not exists")
                    picture = "Not exists"
                }

                //Facebook Email
                if (jsonObject.has("email")) {
                    val facebookEmail = jsonObject.getString("email")
                    Log.i("Facebook Email: ", facebookEmail)
                    email = facebookEmail
                } else {
                    Log.i("Facebook Email: ", "Not exists")
                    email = "Not exists"
                }

                openDetailsActivity()
            }).executeAsync()
    }

    fun isLoggedIn(): Boolean {
        val accessToken = AccessToken.getCurrentAccessToken()
        val isLoggedIn = accessToken != null && !accessToken.isExpired
        return isLoggedIn
    }

    fun logOutUser() {
        LoginManager.getInstance().logOut()
    }

    fun openDetailsActivity() {
        val myIntent = Intent(this, HomePage::class.java)
        myIntent.putExtra("facebook_id", id)
        //myIntent.putExtra("facebook_first_name",firstName)
        //myIntent.putExtra("facebook_middle_name", middleName)
        //myIntent.putExtra("facebook_last_name", lastName)
        myIntent.putExtra("facebook_name", name)
        myIntent.putExtra("facebook_picture", picture)
        myIntent.putExtra("facebook_email", email)
        myIntent.putExtra("facebook_access_token", accessToken)
        startActivity(myIntent)
    }


    /*private fun facebookLogin() {
        login_button.registerCallback(callbackManager, object : FacebookCallback<LoginResult> {
            override fun onSuccess(result: LoginResult?) {
                fbLogin()
                //Log.d("Lets See", "Facebook Token: " + loginResult.accessToken.token)

            }

            override fun onCancel() {
                Log.e("FBLOGIN_FAILD", "Cancel")
            }

            override fun onError(error: FacebookException?) {
                Log.e("FBLOGIN_FAILD", "ERROR", error)
            }

        })
    }*/

    /*private fun fbLogin() {
        val intent = Intent(this, HomePage::class.java)
        startActivity(intent)
    }*/

}//Close Main Activity Method