package com.example.chatapp.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.chatapp.R
import com.example.chatapp.databinding.ActivityMainChatBinding

class MainChat : AppCompatActivity() {
    lateinit var binding: ActivityMainChatBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainChatBinding.inflate(layoutInflater)
        setContentView(binding.root)
        Toast.makeText(this.applicationContext,"Зашел",Toast.LENGTH_LONG).show()
    }
}