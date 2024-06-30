package com.example.message

import android.app.Application
import android.content.SharedPreferences
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class App:Application() {


    override fun onCreate() {
        super.onCreate()
        dbReference =
         Firebase.database.getReference()
        sharedPreferences = getSharedPreferences("ChatApp", MODE_PRIVATE)
        auth = Firebase.auth


    }

    companion object {
        lateinit var sharedPreferences: SharedPreferences
        lateinit var dbReference: DatabaseReference
        lateinit var auth: FirebaseAuth


        const val TOKEN = "token"

    }
}