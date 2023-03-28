package com.example.traininfo.authentication.validity

import android.content.Context
import android.util.Log
import android.util.Patterns
import android.widget.Toast

class Validity(private val context : Context) {
    //validity for signin
    fun checkFormat(email : String,password : String) : Boolean{
        if(email.trim().isEmpty() || password.trim().isEmpty()){
            Toast.makeText(context,"credentials can't be blank",Toast.LENGTH_SHORT).show()
            return false
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            Toast.makeText(context,"email format not correct",Toast.LENGTH_SHORT).show()
            return false
        }
        return true
    }
    //validity for signup
    fun checkFormat(email : String,password : String,confirmPassword : String) : Boolean{
        if(email.trim().isEmpty() || password.trim().isEmpty() || confirmPassword.trim().isEmpty()){
            Toast.makeText(context,"credentials can't be blank",Toast.LENGTH_SHORT).show()
            return false
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            Toast.makeText(context,"email format not correct",Toast.LENGTH_SHORT).show()
            return false
        }
        return true
    }
}