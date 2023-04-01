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
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.auth.FirebaseAuthCredentialsProvider
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class Signup : AppCompatActivity() {
    private lateinit var googleAuthenticationButton : AppCompatImageButton
    private lateinit var emailEditText : TextInputEditText
    private lateinit var passwordEditText : TextInputEditText
    private lateinit var confirmPasswordEditText : TextInputEditText
    private lateinit var signupButton : MaterialButton
    private lateinit var loginButton : MaterialTextView

    private lateinit var openActivity : Intent

    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)

        auth = Firebase.auth

        initViews()
        openLoginActivity()
        openStationDetailHolder()
    }
    private fun initViews(){
        googleAuthenticationButton = findViewById(R.id.googleauthenticationbutton)
        emailEditText = findViewById(R.id.emailedittext)
        passwordEditText = findViewById(R.id.passwordedittext)
        confirmPasswordEditText = findViewById(R.id.confirmpasswordedittext)
        signupButton = findViewById(R.id.signupbutton)
        loginButton = findViewById(R.id.logintextview)
    }
    //function starting with open will open some activity
    private fun openLoginActivity(){
        loginButton.setOnClickListener(View.OnClickListener {view ->
            openActivity = Intent(this@Signup, Login::class.java)
            startActivity(openActivity)
        })
    }
    private fun openStationDetailHolder(){
        val email = emailEditText.editableText
        val password = passwordEditText.editableText
        val confirmPassword = confirmPasswordEditText.editableText
        signupButton.setOnClickListener(View.OnClickListener {view ->
            if(Validity(applicationContext).checkFormatForUserAuthentication("$email","$password","$confirmPassword")){
                auth.createUserWithEmailAndPassword("$email","$password")
                    .addOnSuccessListener {dc ->
                        openActivity = Intent(this@Signup, StationDetailHolder::class.java)
                        startActivity(openActivity)
                    }
                    .addOnFailureListener{e ->
                        Toast.makeText(this@Signup,"${e.message}",Toast.LENGTH_SHORT).show()
                        Log.w("firestore","Error adding document",e)
                    }
            }
        })
    }
}