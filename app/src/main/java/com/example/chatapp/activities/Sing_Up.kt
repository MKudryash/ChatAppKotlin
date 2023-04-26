package com.example.chatapp.activities

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.util.Patterns
import android.view.View
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.example.chatapp.databinding.ActivitySingUpBinding
import com.example.chatapp.utilities.Constants
import com.example.chatapp.utilities.PreferenceManager
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import org.checkerframework.common.returnsreceiver.qual.This
import java.io.ByteArrayOutputStream
import java.util.*

@Suppress("DEPRECATION")
class Sing_Up : AppCompatActivity() {
    private lateinit var binding: ActivitySingUpBinding
    private var encodedImage: String? = null
    var preferenceManager: PreferenceManager? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySingUpBinding.inflate(layoutInflater)
        setContentView(binding.root)
        preferenceManager = PreferenceManager(applicationContext)
        setListener()
    }

    private fun setListener() {
        binding.signIn.setOnClickListener()
        {
            this.onBackPressed()
        }
        binding.btnSignUp.setOnClickListener {
            if (isValidSignUpDetails()) {
                singUp()
            }
        }
        binding.layoutImg.setOnClickListener {
            val intent =
                Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
            pickImg.launch(intent)
        }
    }

    private fun singUp() {
        loading(true)
        val db = FirebaseFirestore.getInstance()
        val user = HashMap<String, Any>()
        user[Constants.KEY_NAME] = binding.inputName.text.toString().trim()
        user[Constants.KEY_EMAIL] = binding.inputEmail.text.toString().trim()
        user[Constants.KEY_PASSWORD] =
            binding.inputPassword.text.toString().trim()
        user[Constants.KEY_IMAGE] = encodedImage!!
        db.collection(Constants.KEY_COLLECTION_USERS)
            .add(user)
            .addOnSuccessListener { documentReference: DocumentReference ->
                loading(false)
                preferenceManager!!.setString(
                    Constants.KEY_USER_ID,
                    documentReference.id
                )
                preferenceManager!!.putBoolean(Constants.KEY_IS_MAIN_CHAT, true)
                preferenceManager!!.setString(
                    Constants.KEY_NAME,
                    binding.inputName.text.toString().trim()
                )
                preferenceManager!!.setString(
                    Constants.KEY_EMAIL,
                    binding.inputEmail.text.toString().trim()
                )
                preferenceManager!!.setString(Constants.KEY_IMAGE, encodedImage)
                val intent = Intent(this.applicationContext, MainChat::class.java)
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
                startActivity(intent)
            }
            .addOnFailureListener {
                loading(false)
                Toast.makeText(this, "Failed to get Data", Toast.LENGTH_SHORT).show()
            }
    }

    private fun encodeImage(bitmap: Bitmap): String? {
        val prevW = 150
        val prevH = bitmap.height * prevW / bitmap.width
        val b = Bitmap.createScaledBitmap(bitmap, prevW, prevH, false)
        val byteArrayOutputStream = ByteArrayOutputStream()
        b.compress(Bitmap.CompressFormat.JPEG, 50, byteArrayOutputStream)
        val bytes = byteArrayOutputStream.toByteArray()
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            Base64.getEncoder().encodeToString(bytes)
        } else ""
    }

    private val pickImg = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result: ActivityResult ->
        if (result.resultCode == RESULT_OK) {
            if (result.data != null) {
                val uri = result.data!!.data
                try {
                    val `is` =
                        contentResolver.openInputStream(uri!!)
                    val bitmap = BitmapFactory.decodeStream(`is`)
                    binding.profileImg.setImageBitmap(bitmap)
                    binding.addImgTv.visibility = View.GONE
                    encodedImage = encodeImage(bitmap)
                } catch (_: Exception) {
                }
            }
        }
    }

    private fun showToast(message: String) {
        Toast.makeText(applicationContext, message, Toast.LENGTH_SHORT).show()
    }

    private fun isValidSignUpDetails(): Boolean {
        return if (encodedImage == null) {
            showToast("Select profile image")
            false
        } else {
            if (binding.inputName.text.toString().trim().isEmpty()) {
                showToast("Enter name")
                false
            } else {
                if (binding.inputEmail.text.toString().trim().isEmpty()) {
                    showToast("Enter email")
                    false
                } else {
                    if (!Patterns.EMAIL_ADDRESS.matcher(binding.inputEmail.text.toString())
                            .matches()
                    ) {
                        showToast("Enter valid email")
                        false
                    } else {
                        if (binding.inputPassword.text.toString().trim().isEmpty()) {
                            showToast("Enter password")
                            false
                        } else {
                            if (binding.inputConfirmPassword.text.toString().trim().isEmpty()) {
                                showToast("Confirm your password")
                                false
                            } else {
                                if (!binding.inputPassword.text.toString()
                                        .equals(binding.inputConfirmPassword.text.toString())
                                ) {
                                    showToast("Password & Confirm password must be same")
                                    false
                                } else true
                            }
                        }
                    }
                }
            }
        }
    }

    private fun loading(isLoading: Boolean) {
        if (isLoading) {
            binding.btnSignUp.visibility = View.INVISIBLE
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.INVISIBLE
            binding.btnSignUp.visibility = View.VISIBLE
        }
    }
}