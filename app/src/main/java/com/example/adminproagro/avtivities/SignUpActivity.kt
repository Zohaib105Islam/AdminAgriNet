package com.example.adminproagro.avtivities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.adminproagro.R
import com.example.adminproagro.databinding.ActivitySignUpBinding
import com.example.adminproagro.model.UserModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

class SignUpActivity : AppCompatActivity() {
    private val binding: ActivitySignUpBinding by lazy {
        ActivitySignUpBinding.inflate(layoutInflater)
    }

    val auth = FirebaseAuth.getInstance()
    val database= FirebaseDatabase.getInstance().reference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.gotoLogin.setOnClickListener() {
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }

        binding.signupBtn.setOnClickListener() {
            signUp()
        }
    }

    private fun signUp(){
        val name = binding.signupName.text.toString().trim()
        val email=binding.signupEmail.text.toString().trim()
        val pass=binding.signupPass.text.toString().trim()
        val address=binding.signupAddress.text.toString().trim()
        val phone=binding.signupPhone.text.toString().trim()

        if (email.isEmpty()){
            binding.signupEmail.error="Enter your email"
            // binding.signupPass.error="Enter your password"
        }
        else if (pass.isEmpty()){
            binding.signupPass.error="Enter your password"
        }else{

            auth.createUserWithEmailAndPassword(email, pass)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        // Sign up successful, navigate to main screen
                        Toast.makeText(this,"Sign up success!", Toast.LENGTH_SHORT).show()
                        saveUserData()
                        startActivity(Intent(this, MainActivity::class.java))
                        finish()
                    } else {
                        // Handle errors
                        Toast.makeText(this, "Sign up failed! ${task.exception?.message}", Toast.LENGTH_SHORT).show()
                    }
                }


        }

    }

    private fun saveUserData() {
        val name = binding.signupName.text.toString().trim()
        val email=binding.signupEmail.text.toString().trim()
        val pass=binding.signupPass.text.toString().trim()
        val address=binding.signupAddress.text.toString().trim()
        val phone=binding.signupPhone.text.toString().trim()

        val user = UserModel(name,email,pass,address,phone)
        val userEmail = auth.currentUser?.email.toString() ?: ""
        val hashedEmail = userEmail.replace('.', ',')

        database.child("user").child(hashedEmail).setValue(user)
    }



}