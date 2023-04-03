package com.example.traininfo.authentication

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageButton
import android.widget.Toast
import androidx.appcompat.widget.AppCompatImageButton
import androidx.savedstate.SavedStateRegistry
import com.example.traininfo.R
import com.example.traininfo.authentication.validity.Validity
import com.example.traininfo.station.StationDetailHolder
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.common.api.GoogleApiClient
import com.google.android.gms.tasks.Task
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textview.MaterialTextView
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.google.rpc.context.AttributeContext

class Login : AppCompatActivity() {
    private lateinit var googleAuthenticationButton : ImageButton
    private lateinit var emailForAuthentication : TextInputEditText
    private lateinit var passwordForAuthentication : TextInputEditText
    private lateinit var loginButton : MaterialButton
    private lateinit var signupButton : MaterialTextView

    private lateinit var openActivity : Intent

    private lateinit var auth: FirebaseAuth

    private val google_req_code : Int = 123

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        auth = Firebase.auth

        initViews()
        openSignupActivity()
        openStationDetailHolderActivity()

        setGoogleAuthenticationButtonListener()


    }
    private fun initViews(){
        googleAuthenticationButton = findViewById(R.id.googleauthenticationbutton)
        emailForAuthentication = findViewById(R.id.emailedittext)
        passwordForAuthentication = findViewById(R.id.passwordedittext)
        loginButton  = findViewById(R.id.loginbutton)
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
    private fun getGSO() : GoogleSignInOptions{
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
            Toast.makeText(this,"Logging In",Toast.LENGTH_SHORT).show()
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
            if(getGoogleSignInAccount(completedTask) != null) UpdateUI(completedTask)
        }catch(e : Exception){
            Toast.makeText(this,e.toString(),Toast.LENGTH_SHORT).show()
        }
    }
    private fun getGoogleSignInAccount(completedTask: Task<GoogleSignInAccount>) : GoogleSignInAccount{
        return completedTask.getResult(ApiException::class.java)
    }
    private fun UpdateUI(completedTask: Task<GoogleSignInAccount>) {
        signInWithCredentials(completedTask)
    }
    private fun getCredentials(completedTask: Task<GoogleSignInAccount>): AuthCredential {
        return GoogleAuthProvider.getCredential(getGoogleSignInAccount(completedTask).idToken, null)
    }
    private fun  signInWithCredentials(completedTask: Task<GoogleSignInAccount>){
        auth.signInWithCredential(getCredentials(completedTask)).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val intent = Intent(this, StationDetailHolder::class.java)
                startActivity(intent)
                finish()
            }
        }
    }
    override fun onStart() {
        super.onStart()
        if (GoogleSignIn.getLastSignedInAccount(this) != null) {
            startActivity(
                Intent(
                    this, StationDetailHolder
                    ::class.java
                )
            )
            finish()
        }
    }
}