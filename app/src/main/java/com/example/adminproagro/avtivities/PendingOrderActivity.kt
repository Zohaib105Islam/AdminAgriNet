package com.example.adminproagro.avtivities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.adminproagro.R
import com.example.adminproagro.adapter.PendingOrderAdapter
import com.example.adminproagro.databinding.ActivityPendingOrderBinding

class PendingOrderActivity : AppCompatActivity() {

    private val binding:ActivityPendingOrderBinding by lazy {
        ActivityPendingOrderBinding.inflate(layoutInflater)
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
            "Glenn Maxwell",
            "Buttler",
            "KP",
            "Josh Hazlewood")
        val quantity= arrayListOf("8","2","6","8","2","6","8")
        val image= listOf(
            R.drawable.spray1,
            R.drawable.spary2,
            R.drawable.sprey3,
            R.drawable.spray4,
            R.drawable.spray1,
            R.drawable.spary2,
            R.drawable.sprey3,
        )
        val adapter=PendingOrderAdapter(ArrayList(customerName),ArrayList(quantity),ArrayList(image),this)
        binding.pendingOrderRCV.adapter=adapter
        binding.pendingOrderRCV.layoutManager=LinearLayoutManager(this)

    }
}