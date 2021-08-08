package com.startup.twiscodetest.util

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.startup.twiscodetest.databinding.ItemFilterBinding
import com.startup.twiscodetest.databinding.ItemStringBinding
import kotlinx.android.synthetic.main.item_filter.view.*

class FilterAdapter(val data : MutableList<String>, val listener : (String,Boolean) -> Unit) : RecyclerView.Adapter<FilterAdapter.ViewHolder>()  {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemFilterBinding =
            ItemFilterBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FilterAdapter.ViewHolder(itemFilterBinding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
       val datax = data[position]
        holder.bind(datax)
        holder.itemView.checked.setOnCheckedChangeListener { buttonView, isChecked ->
            listener(datax,isChecked)
        }
    }

    override fun getItemCount(): Int {
        return data.size
    }

    class ViewHolder(val views : ItemFilterBinding) : RecyclerView.ViewHolder(views.root) {

        fun bind(item : String){
            views.title.text = item

        }
    }
}