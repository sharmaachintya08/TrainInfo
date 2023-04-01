package com.example.traininfo.authentication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.widget.AppCompatImageButton
import com.example.traininfo.R
import com.example.traininfo.authentication.validity.Validity
import com.example.traininfo.station.StationDetailHolder
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textview.MaterialTextView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class Login : AppCompatActivity() {
    private lateinit var googleAuthenticationButton : AppCompatImageButton
    private lateinit var emailForAuthentication : TextInputEditText
    private lateinit var passwordForAuthentication : TextInputEditText
    private lateinit var loginButton : MaterialButton
    private lateinit var forgotPasswordButton : MaterialTextView
    private lateinit var signupButton : MaterialTextView

    private lateinit var openActivity : Intent

    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        auth = Firebase.auth

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
        val email  = emailForAuthentication.editableText
        val password = passwordForAuthentication.editableText
        loginButton.setOnClickListener(View.OnClickListener { view ->
            if(Validity(applicationContext).checkFormatForUserAuthentication("$email","$password")){
                auth.signInWithEmailAndPassword("$email","$password")
                    .addOnSuccessListener {dc ->
                        openActivity = Intent(this@Login, StationDetailHolder::class.java)
                        startActivity(openActivity)
                    }
                    .addOnFailureListener{e ->
                        Toast.makeText(this@Login,"${e.message}", Toast.LENGTH_SHORT).show()
                        Log.w("firestore","Error Logging in",e)
                    }
            }
        })
    }
}