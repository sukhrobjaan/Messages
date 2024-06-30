package com.example.message.ui.users

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.message.App
import com.example.message.core.dto.UserModel
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class UsersViewModel : ViewModel() {

    private val myToken by lazy {
        App.sharedPreferences.getString(App.TOKEN, "")
    }


    private val _users = MutableLiveData<UsersModelEvent>()
    val users: LiveData<UsersModelEvent> = _users

    private val userDataList = mutableListOf<UserModel>()


    private var valueListener: ValueEventListener? = null

    init {
        getUsers()
    }

    fun getUsers() = viewModelScope.launch(Dispatchers.IO) {

        val vl = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {

                userDataList.clear()
                snapshot.children.forEach { ds ->
                    ds.getValue(UserModel::class.java)?.let { user ->
                        if (myToken.equals(user.userToken)) {
                            user.userName = "Saqlangan xabarlar"
                            userDataList.add(user)
                        } else {
                            userDataList.add(user)
                        }
                    }
                    _users.value = UsersModelEvent.Success(userDataList.toList())
                }
            }

            override fun onCancelled(error: DatabaseError) {
                _users.value = UsersModelEvent.Error(error.message)
            }

        }


        this@UsersViewModel.valueListener = vl
        this@UsersViewModel.valueListener?.let {
            App.dbReference.addValueEventListener(it)
        }


//        viewModelScope.launch(Dispatchers.IO) {
//
//            App.dbReference.child("users").get().addOnCompleteListener {
//                if (it.isSuccessful) {
//                    userDataList.clear()
//                    it.result?.children?.forEach { ds ->
//                        ds.getValue(UserModel::class.java)?.let { user ->
//                            if (myToken.equals(user.userToken)) {
//                                user.userName = "Saqlangan xabarlar"
//                                userDataList.add(user)
//                            } else {
//                                userDataList.add(user)
//                            }
//                        }
//                    }
//                    _users.value = UsersModelEvent.Success(userDataList.toList())
//
//                } else {
//                    _users.value = UsersModelEvent.Error(it.exception?.message.toString())
//                }
//            }.addOnFailureListener {
//                _users.value = UsersModelEvent.Error(it.message.toString())
//            }
//        }
    }

    fun removeListener() {
        this.valueListener?.let {
            App.dbReference.removeEventListener(it)
        }
    }
}

sealed class UsersModelEvent {
    data class Success(val userList: List<UserModel>) : UsersModelEvent()
    data class Error(val errorMessage: String) : UsersModelEvent()
    data object Loading : UsersModelEvent()
}