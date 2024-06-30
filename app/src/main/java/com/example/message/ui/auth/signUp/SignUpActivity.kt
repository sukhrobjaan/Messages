package com.example.message.ui.auth.signUp

import android.content.Intent
import androidx.activity.viewModels
import androidx.core.view.isVisible
import com.example.message.App
import com.example.message.core.base.BaseActivity
import com.example.message.core.ext.toast
import com.example.message.databinding.ActivitySignUpBinding
import com.example.message.ui.users.UsersActivity

class SignUpActivity : BaseActivity<ActivitySignUpBinding>() {

    private val vm: SignUpViewModel by viewModels()


    override fun getVB(): ActivitySignUpBinding {
        return ActivitySignUpBinding.inflate(layoutInflater)
    }

    override fun setup() = with(binding) {



        btnSignup.setOnClickListener {
            val userName = etUsername.text?.toString()
            val mail = etMail.text?.toString()
            val password = etPassword.text?.toString()

            if (mail.isNullOrEmpty() || password.isNullOrEmpty()|| userName.isNullOrEmpty()) {
                toast("Fill all fields  To'ldirilmagan ")
                return@setOnClickListener
            }
            vm.signUp(userName, password,mail)
        }



        vm.signUpEvent.observe(this@SignUpActivity) {
            when (it) {
                is SignUpEvent.Error -> {
                    toast(it.error)
                    progressBar.isVisible = false
                }

                SignUpEvent.Loading -> {
                    progressBar.isVisible = true
                }

                is SignUpEvent.Success -> {

                    progressBar.isVisible = false
                    it.token?.let { iToken ->
                        App.sharedPreferences.edit().putString(App.TOKEN, iToken)
                            .apply()

                        vm.saveUsersToDatabase(etUsername.text.toString(), iToken)
                        //navigateToUsersActivity()
                    } ?: toast("Token is null")

                }
            }
        }
        vm.saveUserEvent.observe(
            this@SignUpActivity
        ){
            when(it){
                is SaveUserEvent.Error -> {

                    progressBar.isVisible=false
                    toast(it.message)
                }
                SaveUserEvent.Loading -> {

                    progressBar.isVisible=true

                }
                is SaveUserEvent.Success -> {

                    progressBar.isVisible=false
                    toast(it.message)
                    navigateToUsersActivity()

                }
            }
        }
    }

    private fun navigateToUsersActivity() {
        startActivity(Intent(this, UsersActivity::class.java))
    }
}