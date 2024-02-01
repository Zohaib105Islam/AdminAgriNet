package com.example.adminproagro.avtivities

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import com.example.adminproagro.R
import com.example.adminproagro.databinding.ActivitySplashBinding
import com.google.firebase.auth.FirebaseAuth

class SplashActivity : AppCompatActivity() {

    val binding : ActivitySplashBinding by lazy {
        ActivitySplashBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)


        // Load the animation from the XML file
        val fadeInAnimation = AnimationUtils.loadAnimation(this, R.anim.alphain)

        // Set an optional AnimationListener
        fadeInAnimation.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationStart(animation: Animation) {
                // Animation started
            }

            override fun onAnimationEnd(animation: Animation) {
                // Animation ended
            }

            override fun onAnimationRepeat(animation: Animation) {
                // Animation repeated (if set to repeat)
            }
        })

        // Start the animation on your ImageView
        binding.splashImage.startAnimation(fadeInAnimation)




        Handler().postDelayed({
            chechUserExist()
        },3000)


    }

    fun chechUserExist(){

        val auth=FirebaseAuth.getInstance()
        val currentUser=auth.currentUser

        if (currentUser!=null) {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }else{
            startActivity(Intent(this,LoginActivity::class.java))
            finish()
        }
    }
}