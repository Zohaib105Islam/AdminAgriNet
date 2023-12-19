package com.example.adminproagro.adapter

import android.content.Context
import android.os.Message
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.adminproagro.databinding.PendingOrderBinding

class PendingOrderAdapter(
    private val customerName:MutableList<String>,
    private val quantity:MutableList<String>,
    private val image:MutableList<Int>,
    private val context: Context
): RecyclerView.Adapter<PendingOrderAdapter.PendingOrderVeiwHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PendingOrderVeiwHolder {
        var binding=PendingOrderBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return PendingOrderVeiwHolder(binding)
    }



    override fun onBindViewHolder(holder: PendingOrderVeiwHolder, position: Int) {
        holder.bind(position)
    }

    override fun getItemCount(): Int=customerName.size


    inner class PendingOrderVeiwHolder(private var binding:PendingOrderBinding) : RecyclerView.ViewHolder(binding.root) {
       private var isAccepted=false
        fun bind(position: Int) {
            binding.apply {
                pendingName.text=customerName[position]
                pendingQuantity.text=quantity[position]
                pendingImage.setImageResource(image[position])

                orderAcceptedBtn.apply {
                    if(!isAccepted){
                        text="Accept"
                    }else{
                        text="Dispatch"
                    }
                    setOnClickListener{
                        if (!isAccepted){
                            text="Dispatch"
                            isAccepted=true
                            toastShow("Order is accepted")

                        }else{
                            customerName.removeAt(adapterPosition)
                            notifyItemRemoved(adapterPosition)
                            toastShow("Order is dispatch")
                        }
                    }
                }
            }
        }
        private fun toastShow(message: String){
            Toast.makeText(context,message,Toast.LENGTH_SHORT).show()
        }

    }


}