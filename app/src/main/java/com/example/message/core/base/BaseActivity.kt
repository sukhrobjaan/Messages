package com.example.message.core.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.viewbinding.ViewBinding

abstract class BaseActivity<VB: ViewBinding> : AppCompatActivity() {
    private var _binding: VB? = null

    val binding get() = _binding!!

    abstract fun getVB(): VB

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = getVB()
        setContentView(binding.root)
        setup()
    }

    abstract fun setup()

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}