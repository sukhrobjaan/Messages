package com.example.message.core.db

import android.content.Context
import android.content.SharedPreferences
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

class Preference<T>(
    private val context: Context, private val name: String, private val default: T
) : ReadWriteProperty<Any?, T> {

    private val prefs: SharedPreferences by lazy {
        context.getSharedPreferences("chat_app_shadprefs", Context.MODE_PRIVATE)
    }

    override fun getValue(thisRef: Any?, property: KProperty<*>): T {
        return findPreference(name, default)
    }

    override fun setValue(thisRef: Any?, property: KProperty<*>, value: T) {
        putPreference(name, value)
    }

    private fun putPreference(name: String, value: T) = with(prefs.edit()) {
        when (value) {
            is Long -> putLong(name, value)
            is String -> putString(name, value)
            is Boolean -> putBoolean(name, value)
            is Int -> putInt(name, value)
            is Float -> putFloat(name, value)
            else -> throw IllegalArgumentException("This type can't be saved into Preferences")
        }.apply()

    }

    private fun <T> findPreference(name: String, default: T): T = with(prefs) {
        val res: Comparable<*>? = when (default) {
            is Long -> getLong(name, default)
            is String -> getString(name, default)
            is Boolean -> getBoolean(name, default)
            is Int -> getInt(name, default)
            is Float -> getFloat(name, default)
            else -> throw IllegalArgumentException("This type can't be saved into Preferences")
        }
        @Suppress("UNCHECKE D_CAST") res as T
    }


}