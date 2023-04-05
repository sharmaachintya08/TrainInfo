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
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textview.MaterialTextView
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
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

    private val google_req_code : Int = 123
    private lateinit var  completedTask : Task<GoogleSignInAccount>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)

        auth = Firebase.auth

        initViews()
        openLoginActivity()
        openStationDetailHolder()

        setGoogleAuthenticationButtonListener()
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
    private fun getGSO() : GoogleSignInOptions {
        return GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()
    }
    private fun getGoogleSignInClient(): GoogleSignInClient {
        return GoogleSignIn.getClient(this,getGSO())
    }
    private fun setGoogleAuthenticationButtonListener(){
        googleAuthenticationButton.setOnClickListener{view ->
            Toast.makeText(this,"Signing up",Toast.LENGTH_SHORT).show()
            googleSignIn()
        }
    }
    private fun getGoogleSignInIntent() : Intent{
        return getGoogleSignInClient().signInIntent
    }
    private fun googleSignIn(){
        startActivityForResult(getGoogleSignInIntent(),google_req_code)
    }
    override fun onActivityResult(requestCode : Int,resultCode : Int,data : Intent?){
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == google_req_code) {
            val task: Task<GoogleSignInAccount> = GoogleSignIn.getSignedInAccountFromIntent(data)
            handleResult(task)
        }
    }
    private fun handleResult(completedTask : Task<GoogleSignInAccount>){
        try{
            this.completedTask = completedTask
            if(getGoogleSignInAccount() != null) UpdateUI()
        }catch(e : Exception){
            Toast.makeText(this,e.toString(),Toast.LENGTH_SHORT).show()
        }
    }
    private fun getGoogleSignInAccount() : GoogleSignInAccount {
        return completedTask.getResult(ApiException::class.java)
    }
    private fun UpdateUI() {
        signInWithCredentials()
    }
    private fun getCredentials(): AuthCredential {
        return GoogleAuthProvider.getCredential(getGoogleSignInAccount().idToken, null)
    }
    private fun  signInWithCredentials(){
        auth.signInWithCredential(getCredentials()).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val intent = Intent(this, StationDetailHolder::class.java)
                startActivity(intent)
                finish()
            }
        }
    }
    override fun onStart() {
        super.onStart()
        if (ifLastSignedInAccountIsNotNull()) {
            startActivity(
                Intent(
                    this, StationDetailHolder
                    ::class.java
                )
            )
            finish()
        }
    }
    private fun ifLastSignedInAccountIsNotNull() : Boolean{
        return GoogleSignIn.getLastSignedInAccount(this) != null
    }
}