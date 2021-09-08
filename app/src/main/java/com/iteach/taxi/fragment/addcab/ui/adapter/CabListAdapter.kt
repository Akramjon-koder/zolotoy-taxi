package com.iteach.taxi.fragment.addcab.ui.adapter
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.iteach.taxi.R
import com.iteach.taxi.api.Api_urls
import com.iteach.taxi.databinding.AdapterCablistBinding
import com.iteach.taxi.fragment.addcab.base.CabListModel
data class CabListAdapter(var context: Context, var list: ArrayList<CabListModel>,var listener:OnItemClickListener) :
    RecyclerView.Adapter<CabListAdapter.MyHolderView>() {
    var layoutInflater = LayoutInflater.from(context)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CabListAdapter.MyHolderView {
        var binding = AdapterCablistBinding.inflate(layoutInflater, parent, false)
        return MyHolderView(binding)
    }
    override fun onBindViewHolder(holder: CabListAdapter.MyHolderView, position: Int) {
        var model = list[position]
        with(holder) {
            binding.apply {
                cabName.text = model.model?.name
                cabColor.text = model.color
                Glide.with(context)
                    .load(Api_urls.BASE_URL + model.imageUrl)
                    .placeholder(R.drawable.ic_taxi)
                    .into(cabImage)
                if (model.status == 1) {
                    cabActive.visibility =View.VISIBLE
                } else {
                    cabActive.visibility = View.GONE
                }
                click.setOnClickListener{
                    listener.ClickListener(model)
                }
            }
        }
    }
    fun setData(list1: ArrayList<CabListModel>) {
        this.list.apply {
            list.clear()
            addAll(list1)
            notifyDataSetChanged()
        }
    }
    override fun getItemCount() = list.size
    inner class MyHolderView(var binding: AdapterCablistBinding) :
        RecyclerView.ViewHolder(binding.root)
    interface OnItemClickListener{
        fun ClickListener(model: CabListModel)}

    }