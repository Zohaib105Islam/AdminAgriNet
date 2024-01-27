package com.example.adminproagro.avtivities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Toast
import com.example.adminproagro.R
import com.example.adminproagro.databinding.ActivityLoginBinding
import com.google.firebase.auth.FirebaseAuth

class LoginActivity : AppCompatActivity() {

    private val binding:ActivityLoginBinding by lazy {
        ActivityLoginBinding.inflate(layoutInflater)
    }

    val auth=FirebaseAuth.getInstance()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)



        binding.resetPass.setOnClickListener(){
            resetPassword()
        }

        binding.gotosignup.setOnClickListener(){
            startActivity(Intent(this,SignUpActivity::class.java))
            finish()
        }

        binding.loginBtn.setOnClickListener(){
            loginUser()
        }
    }

    private fun resetPassword() {


        val intent=Intent(this,PassResetActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun loginUser() {

        val email=binding.loginEmail.text.toString().trim()
        val pass=binding.loginPass.text.toString().trim()

        if (email.isEmpty()){
            binding.loginEmail.error="Enter your email"
            binding.loginPass.error="Enter your password"
        }
        else if (pass.isEmpty()){
            binding.loginPass.error="Enter your password"
        }else{

            auth.signInWithEmailAndPassword(email, pass)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        // Sign up successful, navigate to main screen
                        Toast.makeText(this,"Login success!", Toast.LENGTH_LONG).show()
                        startActivity(Intent(this, MainActivity::class.java))
                        finish()
                    } else {
                        // Handle errors
                        Toast.makeText(this, "Login failed! ${task.exception?.message}", Toast.LENGTH_LONG).show()
                    }
                }


        }
    }
}