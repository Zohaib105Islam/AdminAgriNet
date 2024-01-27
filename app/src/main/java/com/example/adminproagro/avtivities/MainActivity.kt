package com.example.adminproagro.avtivities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.adminproagro.databinding.ActivityMainBinding
import com.example.adminproagro.model.OrderDetails
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class MainActivity : AppCompatActivity() {

    private val binding: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    val auth=FirebaseAuth.getInstance()
    private lateinit var database: FirebaseDatabase
    private lateinit var completedOrderRefrence : DatabaseReference

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

        binding.logOut.setOnClickListener(){
            auth.signOut()
            startActivity(Intent(this,LoginActivity::class.java))
            finish()
        }

        pendingOrders()

        completedOrders()

        wholeTimeEarning()

    }

    private fun wholeTimeEarning() {
        val listOfTotalPay = mutableListOf<Int>()
completedOrderRefrence = FirebaseDatabase.getInstance().reference.child("CompletedOrder")
        completedOrderRefrence.addListenerForSingleValueEvent(object  : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {

                for(orderSnapShot in snapshot.children){
                    var completeOrder = orderSnapShot.getValue(OrderDetails::class.java)

                    completeOrder?.totalPrice?.replace("$","")?.toIntOrNull()
                    ?.let {i ->
                        listOfTotalPay.add(i)
                    }
                }

                binding.wholeTimeEarning.text = listOfTotalPay.sum().toString() + "$"
            }

            override fun onCancelled(error: DatabaseError) {

            }
        })
    }

    private fun completedOrders() {
       // database = FirebaseDatabase.getInstance()
        var completedOrderRefrence = database.reference.child("CompletedOrder")
        var completedOrderItemCount = 0
        completedOrderRefrence.addListenerForSingleValueEvent(object  : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {

                completedOrderItemCount = snapshot.childrenCount.toInt()
                binding.completedOrders.text = completedOrderItemCount.toString()
            }

            override fun onCancelled(error: DatabaseError) {

            }
        })
    }

    private fun pendingOrders() {
        database = FirebaseDatabase.getInstance()
        var pendingOrderRefrence = database.reference.child("PendingOrderDetails")
        var pendingOrderItemCount = 0
        pendingOrderRefrence.addListenerForSingleValueEvent(object  : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {

                pendingOrderItemCount = snapshot.childrenCount.toInt()
                binding.pendingOrders.text = pendingOrderItemCount.toString()
            }

            override fun onCancelled(error: DatabaseError) {

            }
        })
    }
}