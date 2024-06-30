package com.example.message.ui.auth.signIn

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.message.App
import com.example.message.R
import com.example.message.core.base.BaseActivity
import com.example.message.core.ext.toast
import com.example.message.databinding.ActivitySignInBinding
import com.example.message.ui.users.UsersActivity

class SignInActivity : BaseActivity<ActivitySignInBinding>() {


    private val vm: SignInViewModel by viewModels()


    override fun getVB(): ActivitySignInBinding {
        return ActivitySignInBinding.inflate(layoutInflater)
    }

    override fun setup() = with(binding) {
        btnSignin.setOnClickListener {
            vm.signIn(etMail.text.toString(), etPassword.text.toString())
        }
        vm.signInEvent.observe(this@SignInActivity) {
            when (it) {
                is SignInEvent.Failure -> {
                    toast(it.message)

                }

                SignInEvent.Loading -> {


                }

                is SignInEvent.Success -> {
                    App.sharedPreferences.edit().putString(App.TOKEN, it.token).apply()
                    startActivity(Intent(this@SignInActivity, UsersActivity::class.java))
                    finish()
                    toast(it.message)
                }
            }


        }
    }
}