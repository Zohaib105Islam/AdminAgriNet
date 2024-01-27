package com.example.adminproagro.avtivities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.adminproagro.adapter.OutforDeliveryAdapter
import com.example.adminproagro.databinding.ActivityOutforDeliveryBinding
import com.example.adminproagro.model.OrderDetails
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class OutforDeliveryActivity : AppCompatActivity() {

    private val binding:ActivityOutforDeliveryBinding by lazy {
        ActivityOutforDeliveryBinding.inflate(layoutInflater)
    }

    private lateinit var database: FirebaseDatabase
    private var listOfCompleteOrderList: ArrayList<OrderDetails> = arrayListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.backBtn.setOnClickListener{
            finish()
        }

        // retrive and display completed order
        retriveCompleteOrderDetail()



    }

    private fun retriveCompleteOrderDetail() {
        // initialize firebase
        database = FirebaseDatabase.getInstance()

         val completeOrderRefrence = database.reference.child("CompletedOrder")
             .orderByChild("currentTime")
        completeOrderRefrence.addListenerForSingleValueEvent(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                // clear the list
                listOfCompleteOrderList.clear()

                for (orderSnapShot in snapshot.children){
                    val completeOrder = orderSnapShot.getValue(OrderDetails::class.java)
                    completeOrder?.let {
                        listOfCompleteOrderList.add(it)
                    }
                }
                // reverse the list
                listOfCompleteOrderList.reverse()

                // set data into recycler view
                setDataIntoRecyclerView()
            }

            override fun onCancelled(error: DatabaseError) {

            }
        })
    }

    private fun setDataIntoRecyclerView() {
        // intialize list for customer name and money status

        val customerName = mutableListOf<String>()
        val moneyStatus = mutableListOf<Boolean>()

        for (order in listOfCompleteOrderList){
            order.userName?.let {
                customerName.add(it)
            }
            moneyStatus.add(order.orderReceived)
        }

        val adapter=OutforDeliveryAdapter(customerName,moneyStatus)
        binding.deliveryRecyclerView.adapter=adapter
        binding.deliveryRecyclerView.layoutManager=LinearLayoutManager(this)


    }
}