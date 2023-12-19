package com.example.adminproagro.adapter

import android.content.res.ColorStateList
import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.adminproagro.databinding.OutForDeliveryBinding

class OutforDeliveryAdapter(
    private val customerNames:ArrayList<String>,
    private val moneyStatus:ArrayList<String>
) : RecyclerView.Adapter<OutforDeliveryAdapter.OutforDeliveryViewHolder>()
{

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OutforDeliveryViewHolder {
         var binding=OutForDeliveryBinding.inflate(LayoutInflater.from(parent.context),parent,false)
             return OutforDeliveryViewHolder(binding)
    }



    override fun onBindViewHolder(holder: OutforDeliveryViewHolder, position: Int) {
        holder.bind(position)
    }

    override fun getItemCount(): Int=customerNames.size

    inner class OutforDeliveryViewHolder(private val binding: OutForDeliveryBinding):RecyclerView.ViewHolder(binding.root) {
        fun bind(position: Int) {

            binding.apply {
                custmrName.text=customerNames[position]
                statusMoney.text=moneyStatus[position]

                val colorMap= mapOf(
                    "Received" to Color.GREEN,"Not Received" to Color.RED,"Pending" to Color.GRAY
                )
                statusMoney.setTextColor(colorMap[moneyStatus[position]] ?: Color.BLACK)
                statusColor.backgroundTintList= ColorStateList.valueOf(colorMap[moneyStatus[position]] ?: Color.BLACK)
            }

        }

    }


}