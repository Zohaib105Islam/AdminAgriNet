package com.example.adminproagro.avtivities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.adminproagro.R
import com.example.adminproagro.adapter.AllProductAdapter
import com.example.adminproagro.databinding.ActivityAllProductBinding

class AllProductActivity : AppCompatActivity() {

    private val binding: ActivityAllProductBinding by  lazy {
        ActivityAllProductBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.backBtn.setOnClickListener{
            finish()
        }

        val productName= listOf("Vertako","Glapho","Cartep","Lemda","Vertako","Glapho","Cartep","Lemda","Vertako","Glapho","Cartep","Lemda","Vertako","Glapho","Cartep","Lemda")
        val productPrice= listOf("$15","$7","$10","$20","$15","$7","$10","$20","$15","$7","$10","$20","$15","$7","$10","$20")
        val productImage= listOf(
            R.drawable.spray1,
            R.drawable.spary2,
            R.drawable.sprey3,
            R.drawable.spray4,
            R.drawable.spray1,
            R.drawable.spary2,
            R.drawable.sprey3,
            R.drawable.spray4,
            R.drawable.spray1,
            R.drawable.spary2,
            R.drawable.sprey3,
            R.drawable.spray4,
            R.drawable.spray1,
            R.drawable.spary2,
            R.drawable.sprey3,
            R.drawable.spray4)

        val adapter= AllProductAdapter(ArrayList(productName),ArrayList(productPrice),ArrayList(productImage))
        binding.allProductRecyclerView.layoutManager= LinearLayoutManager(applicationContext)
        binding.allProductRecyclerView.adapter=adapter

    }
}