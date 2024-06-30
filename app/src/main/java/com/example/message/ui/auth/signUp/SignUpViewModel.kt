package com.example.message.ui.auth.signUp

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.message.App
import com.example.message.core.dto.UserModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SignUpViewModel : ViewModel() {


    private val _signUpEvent = MutableLiveData<SignUpEvent>()
    val signUpEvent: MutableLiveData<SignUpEvent> = _signUpEvent


    private val _saveUserEvent = MutableLiveData<SaveUserEvent>()
    val saveUserEvent: MutableLiveData<SaveUserEvent> = _saveUserEvent


    fun signUp(userName: String, password: String, mail: String) {
        _signUpEvent.value = SignUpEvent.Loading
        viewModelScope.launch(Dispatchers.IO) {
            App.auth.createUserWithEmailAndPassword(mail, password)
                .addOnCompleteListener {
                    if (it.isSuccessful) {
                        _signUpEvent.value = SignUpEvent.Success(it.result.user?.uid.toString())
                    } else {
                        _signUpEvent.value = SignUpEvent.Error(it.exception?.message.toString())
                    }
                }.addOnFailureListener {
                    _signUpEvent.value = SignUpEvent.Error(it.message.toString())
                }
        }
    }

    fun saveUsersToDatabase(name: String, token: String) {

        _saveUserEvent.value = SaveUserEvent.Loading


            App.dbReference.child("users").child(token)
                .setValue(UserModel(userToken = token, userName = name))
                .addOnCompleteListener {

                    if (it.isSuccessful) {
                        saveUserEvent.value = SaveUserEvent.Success("SignUp Successful")
                    } else {
                        saveUserEvent.value = SaveUserEvent.Error(it.exception?.message.toString())
                    }

                }.addOnFailureListener {
                    saveUserEvent.value = SaveUserEvent.Error(it.message.toString())
                }
        }

}

sealed class SaveUserEvent {
    data class Error(val message: String) : SaveUserEvent()
    data class Success(val message: String) : SaveUserEvent()
    data object Loading : SaveUserEvent()

}

sealed class SignUpEvent {
    data class Error(val error: String) : SignUpEvent()
    data class Success(val token: String?) : SignUpEvent()
    data object Loading : SignUpEvent()
}