package com.example.adminproagro.avtivities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.adminproagro.R
import com.example.adminproagro.adapter.OutforDeliveryAdapter
import com.example.adminproagro.databinding.ActivityOutforDeliveryBinding

class OutforDeliveryActivity : AppCompatActivity() {

    private val binding:ActivityOutforDeliveryBinding by lazy {
        ActivityOutforDeliveryBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.backBtn.setOnClickListener{
            finish()
        }

        val customerName= arrayListOf(
            "Maxwell",
            "Jos Buttler",
            "David Miller",
            "Glenn Maxwell Glenn",
            "Buttler",
            "KP",
            "Josh Hazlewood"
        )
        val moneyStatus= arrayListOf(
            "Received",
            "Not Received",
            "Pending",
            "Received",
            "Not Received",
            "Not Received",
            "Pending"
        )

        val adapter=OutforDeliveryAdapter(customerName, moneyStatus)
        binding.deliveryRecyclerView.adapter=adapter
        binding.deliveryRecyclerView.layoutManager=LinearLayoutManager(this)

    }
}