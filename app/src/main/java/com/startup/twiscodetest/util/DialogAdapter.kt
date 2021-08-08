package com.startup.twiscodetest.util

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.startup.twiscodetest.databinding.ItemStringBinding

class DialogAdapter(val data : MutableList<String>, val listener : (String) -> Unit) : RecyclerView.Adapter<DialogAdapter.ViewHolder>()  {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemStringBinding =
            ItemStringBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return DialogAdapter.ViewHolder(itemStringBinding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
       val datax = data[position]
        holder.bind(datax)
        holder.itemView.setOnClickListener {
            listener(datax)
        }
    }

    override fun getItemCount(): Int {
        return data.size
    }

    class ViewHolder(val views : ItemStringBinding) : RecyclerView.ViewHolder(views.root) {

        fun bind(item : String){
            views.title.text = item
        }
    }
}