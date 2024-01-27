package com.example.adminproagro.avtivities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.adminproagro.adapter.PendingOrderAdapter
import com.example.adminproagro.databinding.ActivityPendingOrderBinding
import com.example.adminproagro.model.OrderDetails
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class PendingOrderActivity : AppCompatActivity(), PendingOrderAdapter.onItemClicked {

    private val binding: ActivityPendingOrderBinding by lazy {
        ActivityPendingOrderBinding.inflate(layoutInflater)
    }

    private var listOfName: MutableList<String> = mutableListOf()
    private var listOfTotalPrice: MutableList<String> = mutableListOf()
    private var listOfImageFirstProductOrder: MutableList<String> = mutableListOf()
    private var listOfOrderItem: ArrayList<OrderDetails> = arrayListOf()
    private lateinit var database: FirebaseDatabase
    private lateinit var databaseOrderDetails: DatabaseReference


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
//initialize database
        database = FirebaseDatabase.getInstance()
        databaseOrderDetails = database.reference.child("PendingOrderDetails")


        // Retrieve order details from database
        databaseOrderDetails.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for (orderSnapshot: DataSnapshot in snapshot.children) {
                    val orderDetails: OrderDetails? =
                        orderSnapshot.getValue(OrderDetails::class.java)
                    orderDetails?.let {
                        listOfOrderItem.add(it)
                    }
                }
                listOfOrderItem.reverse()
                addDataToListForRecyclerView()
            }

            override fun onCancelled(error: DatabaseError) {

            }
        })


        binding.backBtn.setOnClickListener()
        {
            finish()
        }

    }


    private fun addDataToListForRecyclerView() {
        for (orderItem: OrderDetails in listOfOrderItem) {

            // add data to respective list for populating the RecyclerView
            orderItem.userName?.let { listOfName.add(it) }
            orderItem.totalPrice?.let { listOfTotalPrice.add(it) }
            orderItem.productImages?.filterNot { it.isEmpty() }?.forEach {
                listOfImageFirstProductOrder.add(it)
            }
        }

        setAdapter()
    }

    private fun setAdapter() {
        binding.pendingOrderRCV.layoutManager = LinearLayoutManager(this)
        val adapter = PendingOrderAdapter(
            this,
            listOfName,
            listOfTotalPrice,
            listOfImageFirstProductOrder,
            this
        )
        binding.pendingOrderRCV.adapter = adapter
    }

    override fun onItemClickListner(position: Int) {
        val intent = Intent(this, PendingOrderDetails::class.java)
        val userOrderDetails: OrderDetails? = listOfOrderItem[position]
        intent.putExtra("UserOrderDetails", userOrderDetails)
        startActivity(intent)

    }

    override fun onItemAcceptClickListner(position: Int) {
// handle product acceptance and update database
        val childItemPushKey = listOfOrderItem[position].itemPushKey
        val clickItemOrderRefrence = childItemPushKey?.let {
            database.reference.child("PendingOrderDetails").child(it)
        }
        clickItemOrderRefrence?.child("orderAccepted")?.setValue(true)
        updateOrderAcceptStatus(position)
    }

    override fun onItemDispatchClickListner(position: Int) {
// handle product Dispatch and update database
        val dispatchItemPushKey = listOfOrderItem[position].itemPushKey
        val dispatchItemOrderRefrence =
            database.reference.child("CompletedOrder").child(dispatchItemPushKey!!)
        dispatchItemOrderRefrence.setValue(listOfOrderItem[position])
            .addOnSuccessListener {
                deleteThisItemFromOrderDetails(dispatchItemPushKey)
            }
         // update orderAccepted status in CompletedOrder
                val buyCompletedOrderRefrence = database.reference.child("CompletedOrder")
                    .child(dispatchItemPushKey!!)
                buyCompletedOrderRefrence.child("orderAccepted").setValue(true)

    }

    private fun deleteThisItemFromOrderDetails(dispatchItemPushKey: String) {
        val orderDetailsItemRefrence =
            database.reference.child("PendingOrderDetails").child(dispatchItemPushKey)
        orderDetailsItemRefrence.removeValue()
            .addOnSuccessListener {
                Toast.makeText(this, "Order is Dispatched", Toast.LENGTH_LONG).show()
            }
            .addOnFailureListener() {
               // Toast.makeText(this, "Order is not Dispatched", Toast.LENGTH_LONG).show()
            }

    }

    private fun updateOrderAcceptStatus(position: Int) {
// update order acceptance in user's BuyHistory => node    and OrderDetails=> node
        val userEmailOfClickedItem = listOfOrderItem[position].userEmail
        val pushKeyOfClickedItem = listOfOrderItem[position].itemPushKey
        // update in History
        val buyHistoryRefrence = database.reference.child("user").child(userEmailOfClickedItem!!)
            .child("BuyHistory").child(pushKeyOfClickedItem!!)
        buyHistoryRefrence.child("orderAccepted").setValue(true)
        // update into PendingOrderDetails
        databaseOrderDetails.child(pushKeyOfClickedItem).child("orderAccepted").setValue(true)

    }

}