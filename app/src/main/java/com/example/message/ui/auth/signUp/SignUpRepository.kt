package com.example.message.ui.auth.signUp

import com.example.message.App
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SignUpRepository {

    fun signUp(mail: String, password: String) {
        CoroutineScope(Dispatchers.IO).launch {
            App.auth.createUserWithEmailAndPassword(mail, password)
                .addOnCanceledListener {

                }
        }

    }
}