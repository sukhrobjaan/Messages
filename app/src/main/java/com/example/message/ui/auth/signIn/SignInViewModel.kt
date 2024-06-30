package com.example.message.ui.auth.signIn

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.message.App
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SignInViewModel : ViewModel() {
    private val _signInEvent = MutableLiveData<SignInEvent>()
    val signInEvent: LiveData<SignInEvent> = _signInEvent


    fun signIn(email: String, password: String) {


        viewModelScope.launch(Dispatchers.IO) {
            App.auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener {
                    if (it.isSuccessful) {
                        _signInEvent.value = SignInEvent.Success(
                                it.result?.user?.uid.toString(),
                                it.result?.user?.uid.toString()
                            )

                    } else {
                        _signInEvent.value=SignInEvent.Failure(it.exception?.message.toString())
                    }
                }.addOnFailureListener {
                    _signInEvent.value=SignInEvent.Failure(it.message.toString())
                }

        }
    }
}


sealed class SignInEvent {
    data class Success(val message: String, val token: String) : SignInEvent()
    data class Failure(val message: String) : SignInEvent()
    data object Loading : SignInEvent()
}