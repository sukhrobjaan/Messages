package com.example.message.ui.users


import android.content.Intent
import androidx.activity.viewModels
import com.example.message.App
import com.example.message.core.base.BaseActivity
import com.example.message.core.ext.toast
import com.example.message.databinding.ActivityUsersBinding
import com.example.message.ui.MainActivity

class UsersActivity : BaseActivity<ActivityUsersBinding>() {


    private val vm: UsersViewModel by viewModels()

    private val userAdapter by lazy { UsersAdapter(this) }


    override fun getVB(): ActivityUsersBinding {
        return ActivityUsersBinding.inflate(layoutInflater)
    }

    override fun setup() = with(binding) {

        rvUsers.adapter = userAdapter

        btnLogout.setOnClickListener {

            App.sharedPreferences.edit().clear().apply()

            val intent = Intent(this@UsersActivity, MainActivity::class.java)

            intent.addFlags(
                Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            )
            startActivity(intent)

        }
        vm.users.observe(this@UsersActivity) {
            when (it) {
                is UsersModelEvent.Error -> {
                    toast(it.errorMessage)

                }

                UsersModelEvent.Loading -> {


                }

                is UsersModelEvent.Success -> {
                    println("::::::::::List-> ${it.userList}")
                    userAdapter.submitList(it.userList.toList())

                }
            }
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        vm.removeListener()
    }
}
