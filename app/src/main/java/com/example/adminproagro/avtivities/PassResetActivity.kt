package com.example.adminproagro.avtivities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Toast
import com.example.adminproagro.R
import com.example.adminproagro.databinding.ActivityPassResetBinding
import com.google.firebase.auth.FirebaseAuth

class PassResetActivity : AppCompatActivity() {

    val binding: ActivityPassResetBinding by lazy {
        ActivityPassResetBinding.inflate(layoutInflater)
    }

    val auth= FirebaseAuth.getInstance()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.backBtn.setOnClickListener(){
            val intent = Intent(this,LoginActivity::class.java)
            startActivity(intent)
            finish()
        }


        binding.resetPassBtn.setOnClickListener(){
            resetPassword()
        }


    }


    private fun resetPassword() {
        val email=binding.resetPassEmail.text.toString().trim()

        // Validate input
        if (TextUtils.isEmpty(email)) {
            Toast.makeText(this, "Please enter your email address!", Toast.LENGTH_LONG).show()
            return
        }

        // Send password reset email
        auth.sendPasswordResetEmail(email)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    Toast.makeText(this, "Password reset email sent! Varify and then Login", Toast.LENGTH_LONG).show()
                    startActivity(Intent(this, LoginActivity::class.java))
                    finish()
                } else {
                    Toast.makeText(this, "Error sending reset email: ${task.exception?.message}", Toast.LENGTH_LONG).show()
                }
            }

    }
}