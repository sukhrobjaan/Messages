package com.example.message.ui

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.message.App
import com.example.message.R
import com.example.message.ui.auth.signIn.SignInActivity
import com.example.message.ui.auth.signUp.SignUpActivity
import com.example.message.ui.users.UsersActivity
import com.google.firebase.Firebase
import com.google.firebase.auth.auth

class MainActivity : AppCompatActivity() {

    private val token by lazy {
        App.sharedPreferences.getString(App.TOKEN, "")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val currentUsers = Firebase.auth.currentUser

        if (currentUsers == null) {
            val intent = Intent(this, SignUpActivity::class.java)

            intent.addFlags(
                Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            )
            startActivity(intent)
        } else {
            if (token.isNullOrEmpty()) {
                val intent = Intent(this, SignInActivity::class.java)

                intent.addFlags(
                    Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                )
                startActivity(intent)

            } else {
                val intent = Intent(this, UsersActivity::class.java)

                intent.addFlags(
                    Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                )
                startActivity(intent)
            }
        }


    }
}