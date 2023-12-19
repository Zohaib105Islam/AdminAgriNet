package com.example.adminproagro.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.adminproagro.databinding.AllProductBinding

class AllProductAdapter(
    private val allProductName: MutableList<String>,
    private val allProductPrices: MutableList<String>,
    private val allProductImages: MutableList<Int>
): RecyclerView.Adapter<AllProductAdapter.AllProductViewHolder>() {

    private var productQuantities: MutableList<Int> = Array(allProductName.size) { 1 }.toMutableList()


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AllProductViewHolder {
        return AllProductViewHolder(AllProductBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }



    override fun onBindViewHolder(holder: AllProductViewHolder, position: Int) {
        holder.bind(allProductName[position],allProductPrices[position],allProductImages[position])
    }

    override fun getItemCount(): Int=allProductName.size

    inner class AllProductViewHolder(private val binding: AllProductBinding):RecyclerView.ViewHolder(binding.root) {
        fun bind(name: String, price: String, img: Int) {
           binding.apply{
               allProductName.text=name
               allProductPrice.text=price
               allProductImg.setImageResource(img)
           }

            binding.allProductImgbtnAdd.setOnClickListener{
                increaseQuantity(position)
            }
            binding.allProductImgbtnMinus.setOnClickListener{
                decreaseQuantity(position)
            }
            binding.allProductImgbtnDelete.setOnClickListener{
                delete(position)
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
            allProductName.removeAt(position)
            allProductPrices.removeAt(position)
            allProductImages.removeAt(position)
            productQuantities.removeAt(position)
            notifyDataSetChanged()
        }


    }

}