package com.example.adminproagro.avtivities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.adminproagro.adapter.AllProductAdapter
import com.example.adminproagro.databinding.ActivityAllProductBinding
import com.example.adminproagro.model.AllMenu
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class AllProductActivity : AppCompatActivity() {

    private lateinit var databaseReference: DatabaseReference
    private lateinit var database : FirebaseDatabase
    private var productItems : ArrayList<AllMenu> = ArrayList()

    private val binding: ActivityAllProductBinding by  lazy {
        ActivityAllProductBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        databaseReference=FirebaseDatabase.getInstance().reference
        retrieveProductItem()

        binding.backBtn.setOnClickListener{
            finish()
        }


    }

    private fun retrieveProductItem() {
       database=FirebaseDatabase.getInstance()
        val productRef: DatabaseReference = database.reference.child("product")

        // fetch data from RealTime database
        productRef.addListenerForSingleValueEvent(object : ValueEventListener
        {
            override fun onDataChange(snapshot: DataSnapshot) {
                //Clear existing data before show
                productItems.clear()

                // loop for through each item
                for (productSnapshot in snapshot.children){
                    val menuItem : AllMenu? =productSnapshot.getValue(AllMenu::class.java)
                    menuItem?.let {
                        productItems.add(it)
                    }
                }
                productItems.reverse()
                setAdapter()
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
    }

    private fun setAdapter() {
        val adapter= AllProductAdapter(this,productItems,databaseReference){position ->
            deleteProductItems(position)
        }
        binding.allProductRecyclerView.layoutManager= LinearLayoutManager(applicationContext)
        binding.allProductRecyclerView.adapter=adapter
    }

    private fun deleteProductItems(position: Int) {
        val productItemToDelete = productItems[position]
        val productItemKey = productItemToDelete.key
        val productMenuRefrence = database.reference.child("product").child(productItemKey!!)
        productMenuRefrence.removeValue().addOnCompleteListener(){task ->
            if(task.isSuccessful){
                productItems.removeAt(position)
                binding.allProductRecyclerView.adapter?.notifyItemRemoved(position)
            }else{
                Toast.makeText(this,"Product not Deleted",Toast.LENGTH_LONG).show()
            }
        }
    }
}