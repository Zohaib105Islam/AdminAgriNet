package com.example.adminproagro.adapter

import android.content.Context
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.adminproagro.databinding.PendingOrderBinding
import java.net.URI

class PendingOrderAdapter(
    private val context: Context,
    private val customerName: MutableList<String>,
    private val price: MutableList<String>,
    private val image: MutableList<String>,
    private val itemClicked: onItemClicked
) : RecyclerView.Adapter<PendingOrderAdapter.PendingOrderVeiwHolder>() {

    interface onItemClicked {
        fun onItemClickListner(position: Int)
        fun onItemAcceptClickListner(position: Int)
        fun onItemDispatchClickListner(position: Int)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PendingOrderVeiwHolder {
        var binding =
            PendingOrderBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PendingOrderVeiwHolder(binding)
    }


    override fun onBindViewHolder(holder: PendingOrderVeiwHolder, position: Int) {
        holder.bind(position)
    }

    override fun getItemCount(): Int = customerName.size


    inner class PendingOrderVeiwHolder(private var binding: PendingOrderBinding) :
        RecyclerView.ViewHolder(binding.root) {
        private var isAccepted = false
        fun bind(position: Int) {
            binding.apply {
                pendingName.text = customerName[position]
                pendingPrice.text = price[position]
                //  pendingImage.setImageResource(image[position])
                val uriString: String = image[position]
                val uri: Uri = Uri.parse(uriString)
                Glide.with(context).load(uri).into(pendingImage)

                orderAcceptedBtn.apply {
                    if (!isAccepted) {
                        text = "Accept"
                    } else {
                        text = "Dispatch"
                    }
                    setOnClickListener {
                        if (!isAccepted) {
                            text = "Dispatch"
                            isAccepted = true
                            toastShow("Order is accepted")
                            itemClicked.onItemAcceptClickListner(position)

                        } else {
                            customerName.removeAt(adapterPosition)
                            notifyItemRemoved(adapterPosition)
                            toastShow("Order is dispatch")
                            itemClicked.onItemDispatchClickListner(position)
                        }
                    }
                }

                itemView.setOnClickListener() {
                    itemClicked.onItemClickListner(position)
                }
            }
        }

        private fun toastShow(message: String) {
            Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
        }

    }


}