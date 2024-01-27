package com.example.adminproagro.adapter

import android.content.Context
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.adminproagro.databinding.PendingOrderProductBinding

class OrderDetailsAdapter(
    private val context: Context,
    private var productName: MutableList<String>,
    private var productImage: MutableList<String>,
    private var productQuantity: MutableList<Int>,
    private var productPrice: MutableList<String>,
) :RecyclerView.Adapter<OrderDetailsAdapter.OrderDetailsViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderDetailsViewHolder {
        val binding = PendingOrderProductBinding.inflate(LayoutInflater.from(parent.context),parent,false)
       return OrderDetailsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: OrderDetailsViewHolder, position: Int) {
        holder.bind(position)
    }

    override fun getItemCount(): Int =productName.size

   inner class OrderDetailsViewHolder(private var binding: PendingOrderProductBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(position: Int) {
            binding.apply {
                pendingCustomerName.text=productName[position]
                pendingPrice.text=productPrice[position]
                pendingQuantity.text=productQuantity[position].toString()
                 val uriString: String = productImage[position]
                val uri: Uri = Uri.parse(uriString)
                Glide.with(context).load(uri).into(recentBuyImage)
            }
        }

    }

}