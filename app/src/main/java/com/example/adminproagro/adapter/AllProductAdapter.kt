package com.example.adminproagro.adapter

import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.adminproagro.databinding.AllProductBinding
import com.example.adminproagro.model.AllMenu
//import com.google.firebase.database.core.Context
import android.content.Context
import com.google.firebase.database.DatabaseReference


class AllProductAdapter(
    private val context: Context,
    private val productList: ArrayList<AllMenu>,
    databaseReference: DatabaseReference,
    private val onDeleteClickListner : (position : Int) -> Unit
): RecyclerView.Adapter<AllProductAdapter.AllProductViewHolder>() {

    private var productQuantities =IntArray(productList.size) { 1 }
   // private var productQuantities: MutableList<Int> = Array(productList.size) { 1 }.toMutableList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AllProductViewHolder {
        return AllProductViewHolder(AllProductBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder: AllProductViewHolder, position: Int) {
        holder.bind(position)
    }
    override fun getItemCount(): Int=productList.size

    inner class AllProductViewHolder(private val binding: AllProductBinding):RecyclerView.ViewHolder(binding.root) {
        fun bind(position: Int) {
           binding.apply{

               val productItem: AllMenu=productList[position]
               val uriString : String? = productItem.productImage
               val uri: Uri = Uri.parse(uriString)
               allProductName.text=productItem.productName
               allProductPrice.text=productItem.productPrice
               Glide.with(context).load(uri).into(allProductImg)

               allProductQuantityTv.text= productItem.productQuantity.toString()
           }

            binding.allProductImgbtnAdd.setOnClickListener{
                increaseQuantity(position)
            }
            binding.allProductImgbtnMinus.setOnClickListener{
                decreaseQuantity(position)
            }
            binding.allProductImgbtnDelete.setOnClickListener{
                onDeleteClickListner(position)
            }
        }

        //Method for Increase quantity
        private fun increaseQuantity(position: Int) {
            if (productQuantities[position] < 10) {
                productQuantities[position]++
                binding.allProductQuantityTv.text = productQuantities[position].toString()
            }
        }

        //Method for decrease quantity
        private fun decreaseQuantity(position: Int) {
            if (productQuantities[position] > 1) {
                productQuantities[position]--
                binding.allProductQuantityTv.text = productQuantities[position].toString()
            }
        }

        //Method for delete quantity
        fun delete(position: Int) {
            productList.removeAt(position)
            productList.removeAt(position)
            productList.removeAt(position)
            notifyItemRemoved(position)
            notifyItemRangeChanged(position,productList.size)
        }


    }

}