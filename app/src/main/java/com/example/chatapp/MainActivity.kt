package com.example.chatapp

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.chatapp.databinding.ActivityMainBinding
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setListeners()
    }

    private fun setListeners()
    {
        binding.btnSignIn.setOnClickListener()
        {
            addDataToFirestore()
        }
    }
    private fun addDataToFirestore() {
        val dataBase = FirebaseFirestore.getInstance()
        val data = HashMap<String, Any>()
        data["first_name"] = "Julia"
        data["last_name"] = "Mamsheva"
        dataBase.collection("users").add(data)
            .addOnSuccessListener {
                Toast.makeText(
                    applicationContext,
                    "Data Inserted",
                    Toast.LENGTH_SHORT
                ).show()
            }
            .addOnFailureListener { exception: Exception ->
                Toast.makeText(
                    applicationContext,
                    exception.message,
                    Toast.LENGTH_SHORT
                ).show()
            }
    }
}