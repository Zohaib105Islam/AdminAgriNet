package com.example.adminproagro.avtivities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.adminproagro.R
import com.example.adminproagro.databinding.ActivityAdminProfileBinding

class AdminProfileActivity : AppCompatActivity() {

    private val binding: ActivityAdminProfileBinding by lazy {
        ActivityAdminProfileBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.backBtn.setOnClickListener{
            finish()
        }

        binding.name.isEnabled=false
        binding.address.isEnabled=false
        binding.email.isEnabled=false
        binding.phone.isEnabled=false
        binding.password.isEnabled=false

       // var isEnable=false
        binding.editImg.setOnClickListener{
            binding.name.isEnabled=true
            binding.address.isEnabled=true
            binding.email.isEnabled=true
            binding.phone.isEnabled=true
            binding.password.isEnabled=true
            binding.name.requestFocus()


//            isEnable=! isEnable
//
//            binding.name.isEnabled=isEnable
//            binding.address.isEnabled=isEnable
//            binding.email.isEnabled=isEnable
//            binding.phone.isEnabled=isEnable
//            binding.password.isEnabled=isEnable
//
//            if (isEnable)
//                binding.name.requestFocus()

        }


    }
}