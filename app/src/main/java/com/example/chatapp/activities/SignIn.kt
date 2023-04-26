package com.example.chatapp.activities

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.chatapp.databinding.ActivitySigininBinding

class SignIn : AppCompatActivity() {
    lateinit var binding: ActivitySigininBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySigininBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setListeners()
    }

    private fun setListeners()
    {
        binding.btnSignIn.setOnClickListener()
        {

        }
        binding!!.txtCreateNewAccount.setOnClickListener()
        {
            startActivities(arrayOf(Intent(this,Sing_Up::class.java)))
        }
    }

    //Test Connection Database
    /*private fun addDataToFirestore() {
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
    }*/
}