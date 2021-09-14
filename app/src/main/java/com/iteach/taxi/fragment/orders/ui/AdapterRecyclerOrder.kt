package com.iteach.taxi.fragment.orders.ui
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.iteach.taxi.databinding.ItemOrderBinding
import com.iteach.taxi.fragment.orders.base.OrderModel
class AdapterRecyclerOrder(val items: ArrayList<OrderModel>,var orderclick: OnItemClickListner) :RecyclerView.Adapter<AdapterRecyclerOrder.MyViewHolder>(){
   inner class MyViewHolder(var binding: ItemOrderBinding): RecyclerView.ViewHolder(binding.root)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
          var binding =ItemOrderBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return MyViewHolder(binding) }
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
      with(holder){
          binding.nomi.text = items[position].clientphone
          binding.title.text=items[position].where.toString()
          binding.itemOrder.setOnClickListener {
              orderclick.orderClicked(items[position]) }}
    }
    fun setData(list: List<OrderModel>){
        items.clear()
        items.addAll(list)
        notifyDataSetChanged()
    }
    override fun getItemCount(): Int {
        return items.size
    }

    interface OnItemClickListner{
        fun orderClicked(order: OrderModel)
    }
}