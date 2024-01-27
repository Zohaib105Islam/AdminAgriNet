package com.example.adminproagro.avtivities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.adminproagro.adapter.OrderDetailsAdapter
import com.example.adminproagro.databinding.ActivityPendingOrderDetailsBinding
import com.example.adminproagro.model.OrderDetails

class PendingOrderDetails : AppCompatActivity() {

    private val binding: ActivityPendingOrderDetailsBinding by lazy {
        ActivityPendingOrderDetailsBinding.inflate(layoutInflater)
    }

    private var userName: String? =null
    private var address: String? =null
    private var phoneNumber: String? =null
    private var totalPrice: String? =null

    private var productNames: ArrayList<String> = arrayListOf()
    private var productImages: ArrayList<String> = arrayListOf()
    private var productQuantity: ArrayList<Int> = arrayListOf()
    private var productPrices: ArrayList<String> = arrayListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.backBtn.setOnClickListener(){ finish() }

      //  getDataFromIntent()
        //=======================
        val receivedOrderDetails: OrderDetails? = intent.getSerializableExtra("UserOrderDetails") as OrderDetails
       receivedOrderDetails?. let { orderDetails ->
            userName = receivedOrderDetails.userName
            productNames = receivedOrderDetails.productNames as ArrayList<String>

            productImages = receivedOrderDetails.productImages  as ArrayList<String>
            productQuantity = receivedOrderDetails.productQuantities  as ArrayList<Int>
            address = receivedOrderDetails.address
            phoneNumber = receivedOrderDetails.phoneNumber
            productPrices = receivedOrderDetails.productPrices  as ArrayList<String>
            totalPrice = receivedOrderDetails.totalPrice

            setUserDetail()
            setAdapter()
        }


    }

//    private fun getDataFromIntent() {
//        val receivedOrderDetails: OrderDetails? = intent.getParcelableExtra<OrderDetails>("UserOrderDetails")
//        if (receivedOrderDetails != null) {
//            userName = receivedOrderDetails.userName
//            productNames = receivedOrderDetails.productNames ?: mutableListOf()
//            productImages = receivedOrderDetails.productImages ?: mutableListOf()
//            productQuantity = receivedOrderDetails.productQuantities ?: mutableListOf()
//            address = receivedOrderDetails.address
//            phoneNumber = receivedOrderDetails.phoneNumber
//            productPrices = receivedOrderDetails.productPrices ?: mutableListOf()
//            totalPrice = receivedOrderDetails.totalPrice
//
//         //   setUserDetail()
//         //   setAdapter()
//        } else {
//            Toast.makeText(this, "Data is null", Toast.LENGTH_LONG).show()
//        }
//    }

    private fun setAdapter() {
        binding.orderUserRecyclerView.layoutManager=LinearLayoutManager(this)
        val adapter=OrderDetailsAdapter(this,productNames,productImages,productQuantity,productPrices)
        binding.orderUserRecyclerView.adapter = adapter
    }

    private fun setUserDetail() {
        binding.orderUserName.text=userName
        binding.orderUserAddress.text=address
        binding.orderUserPhone.text=phoneNumber
        binding.orderUserAmount.text=totalPrice
    }


//======================================================================  original
//    private fun getDataFromIntent() {
//        val receivedOrderDetails: OrderDetails? = intent.getParcelableExtra<OrderDetails>("UserOrderDetails")
//   if(receivedOrderDetails != null){
//       userName=receivedOrderDetails.userName
//       productNames=receivedOrderDetails.productNames!!
//       productImages=receivedOrderDetails.productImages!!
//       productQuantity=receivedOrderDetails.productQuantities!!
//       address=receivedOrderDetails.address
//       phoneNumber=receivedOrderDetails.phoneNumber
//       productPrices=receivedOrderDetails.productPrices!!
//       totalPrice=receivedOrderDetails.totalPrice
//
//       setUserDetail()
//   }else{
//       Toast.makeText(this,"Data is null",Toast.LENGTH_LONG).show()
//   }
//
//
//
//    }


}