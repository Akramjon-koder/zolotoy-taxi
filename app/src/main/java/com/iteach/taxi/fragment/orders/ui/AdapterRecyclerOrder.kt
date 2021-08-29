package com.iteach.taxi.fragment.orders.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.iteach.taxi.databinding.ItemOrderBinding
import com.iteach.taxi.fragment.orders.base.OrderModel

class AdapterRecyclerOrder(val items: List<OrderModel>,var orderclick: OnItemClickListner) :RecyclerView.Adapter<AdapterRecyclerOrder.MyViewHolder>(){
   inner class MyViewHolder(var binding: ItemOrderBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
          var binding =ItemOrderBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
      with(holder){
          binding.nomi.text = items[position].userName
          binding.itemOrder.setOnClickListener {
              orderclick.orderClicked(items[position])

          }
      }
    }

    override fun getItemCount(): Int {
        return items.size
    }

    interface OnItemClickListner{
        fun orderClicked(order: OrderModel)
    }
}