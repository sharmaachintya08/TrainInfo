package com.example.traininfo.authentication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.appcompat.widget.AppCompatImageButton
import com.example.traininfo.R
import com.example.traininfo.authentication.validity.Validity
import com.example.traininfo.station.StationDetailHolder
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textview.MaterialTextView

class Signup : AppCompatActivity() {
    private lateinit var googleAuthenticationButton : AppCompatImageButton
    private lateinit var emailEditText : TextInputEditText
    private lateinit var passwordEditText : TextInputEditText
    private lateinit var confirmPasswordEditText : TextInputEditText
    private lateinit var signupButton : MaterialButton
    private lateinit var loginButton : MaterialTextView

    private lateinit var openActivity : Intent
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)

        initViews()
        openLoginActivity()
    }
    private fun initViews(){
        googleAuthenticationButton = findViewById(R.id.googleauthenticationbutton)
        emailEditText = findViewById(R.id.emailedittext)
        passwordEditText = findViewById(R.id.passwordedittext)
        confirmPasswordEditText = findViewById(R.id.confirmpasswordedittext)
        signupButton = findViewById(R.id.signupbutton)
        loginButton = findViewById(R.id.logintextview)
    }
    private fun openLoginActivity(){
        loginButton.setOnClickListener(View.OnClickListener {view ->
            openActivity = Intent(this@Signup, Login::class.java)
            startActivity(openActivity)
        })
    }
}