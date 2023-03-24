package com.example.traininfo.Authentication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import androidx.appcompat.widget.AppCompatImageButton
import com.example.traininfo.R
import com.example.traininfo.Station.StationDetailHolder
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textview.MaterialTextView

class Login : AppCompatActivity() {
    private lateinit var googleAuthenticationButton : AppCompatImageButton
    private lateinit var emailForAuthentication : TextInputEditText
    private lateinit var passwordForAuthentication : TextInputEditText
    private lateinit var loginButton : MaterialButton
    private lateinit var forgotPasswordButton : MaterialTextView
    private lateinit var signupButton : MaterialTextView

    private lateinit var openActivity : Intent
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        initViews()
        openSignupActivity()
        openStationDetailHolderActivity()
    }
    private fun initViews(){
        googleAuthenticationButton = findViewById(R.id.googleauthenticationbutton)
        emailForAuthentication = findViewById(R.id.emailedittext)
        passwordForAuthentication = findViewById(R.id.passwordedittext)
        loginButton  = findViewById(R.id.loginbutton)
        forgotPasswordButton = findViewById(R.id.forgotpasswordtextview)
        signupButton = findViewById(R.id.signuptextview)
    }
    private fun openSignupActivity(){
        signupButton.setOnClickListener(View.OnClickListener {view ->
            openActivity = Intent(this@Login,Signup::class.java)
            startActivity(openActivity)
        })
    }
    private fun openStationDetailHolderActivity(){
        loginButton.setOnClickListener(View.OnClickListener { view ->
            openActivity = Intent(this@Login,StationDetailHolder::class.java)
            startActivity(openActivity)
        })
    }
}