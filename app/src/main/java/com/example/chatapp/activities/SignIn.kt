package com.example.chatapp.activities

import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.chatapp.databinding.ActivitySigininBinding
import com.example.chatapp.utilities.Constants
import com.example.chatapp.utilities.PreferenceManager
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot


class SignIn : AppCompatActivity() {
    lateinit var binding: ActivitySigininBinding
    var preferenceManager: PreferenceManager? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySigininBinding.inflate(layoutInflater)
        setContentView(binding.root)
        preferenceManager = PreferenceManager(applicationContext)
        if (preferenceManager!!.getBoolean(Constants.KEY_IS_SIGNED_IN)!!) {
            val intent = Intent(applicationContext, MainChat::class.java)
            startActivity(intent)
            finish()
        }
        setListeners()
    }

    private fun setListeners()
    {
        binding.btnSignIn.setOnClickListener()
        {
            if (isValidSignIn() == true)  SignIn()
        }
        binding!!.txtCreateNewAccount.setOnClickListener()
        {
            startActivities(arrayOf(Intent(this,Sing_Up::class.java)))
        }
    }
    private fun showToast(msg: String) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
    }

    private fun isValidSignIn(): Boolean? {
        if (binding.inputEmail.text.toString().trim().isEmpty()) {
            showToast("Enter the Email")
            return false
        } else if (!Patterns.EMAIL_ADDRESS.matcher(binding.inputEmail.text.toString().trim())
                .matches()
        ) {
            showToast("Enter valid Email")
            return false
        } else if (binding.inputPassword.text.toString().trim().isEmpty()) {
            showToast("Enter the Password")
            return false
        }
        return true
    }

    private fun SignIn() {
        loading(true)
        val db = FirebaseFirestore.getInstance()
        db.collection(Constants.KEY_COLLECTION_USERS)
            .whereEqualTo(Constants.KEY_EMAIL, binding.inputEmail.text.toString().trim())
            .whereEqualTo(Constants.KEY_PASSWORD, binding.inputPassword.text.toString().trim())
            .get()
            .addOnCompleteListener(OnCompleteListener { task: Task<QuerySnapshot?> ->
                if (task.isSuccessful && task.result != null && task.result!!
                        .documents.size > 0
                ) {
                    val snapshot = task.result!!.documents[0]
                    preferenceManager!!.putBoolean(Constants.KEY_IS_SIGNED_IN, true)
                    preferenceManager!!.setString(Constants.KEY_USER_ID, snapshot.id)
                    preferenceManager!!.setString(
                        Constants.KEY_NAME,
                        snapshot.getString(Constants.KEY_NAME)
                    )
                    preferenceManager!!.setString(
                        Constants.KEY_IMAGE,
                        snapshot.getString(Constants.KEY_IMAGE)
                    )
                    val intent = Intent(applicationContext, MainChat::class.java)
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
                    startActivity(intent)
                } else {
                    loading(false)
                    showToast("Unable to Sign In")
                }
            })
    }

    private fun loading(isLoading: Boolean) {
        if (isLoading) {
            binding.btnSignIn.visibility = View.INVISIBLE
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.INVISIBLE
            binding.btnSignIn.visibility = View.VISIBLE
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