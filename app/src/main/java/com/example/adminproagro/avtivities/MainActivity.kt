package com.example.adminproagro.avtivities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.adminproagro.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private val binding: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.addProduct.setOnClickListener{
            val intent=Intent(this, AddProductActivity::class.java)
            startActivity(intent)
        }

        binding.allProduct.setOnClickListener{
            val intent=Intent(this, AllProductActivity::class.java)
            startActivity(intent)
        }

        binding.orderDispatch.setOnClickListener{
            val intent=Intent(this, OutforDeliveryActivity::class.java)
            startActivity(intent)
        }

        binding.adminProfile.setOnClickListener{
            val intent=Intent(this, AdminProfileActivity::class.java)
            startActivity(intent)
        }

        binding.createUser.setOnClickListener{
            val intent=Intent(this, CreateUserActivity::class.java)
            startActivity(intent)
        }

        binding.pendingOrderTv.setOnClickListener{
            val intent=Intent(this,PendingOrderActivity::class.java)
            startActivity(intent)
        }
    }
}