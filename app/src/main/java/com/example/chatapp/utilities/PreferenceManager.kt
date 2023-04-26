package com.example.chatapp.utilities

import android.content.Context
import android.content.SharedPreferences

class PreferenceManager(context: Context) {
    private var sp: SharedPreferences? = null
    init {
        sp = context.getSharedPreferences(Constants.KEY_PREFERENCE_NAME, Context.MODE_PRIVATE)
    }

    fun putBoolean(key: String?, value: Boolean?) {
        val editor = sp!!.edit()
        editor.putBoolean(key, value!!)
        editor.apply()
    }

    fun getBoolean(key: String?): Boolean? {
        return sp!!.getBoolean(key, false)
    }

    fun setString(key: String?, value: String?) {
        val editor = sp!!.edit()
        editor.putString(key, value)
        editor.apply()
    }

    fun getString(key: String?): String? {
        return sp!!.getString(key, null)
    }

    fun clear() {
        val editor = sp!!.edit()
        editor.clear()
        editor.apply()
    }
}