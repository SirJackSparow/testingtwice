package com.startup.twiscodetest.view.cart

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import com.startup.twiscodetest.R
import com.startup.twiscodetest.data.model.KeranjangModel
import com.startup.twiscodetest.databinding.ItemKeranjangBinding
import com.startup.twiscodetest.util.IMAGE_URL
import com.startup.twiscodetest.util.rupiah
import kotlinx.android.synthetic.main.item_keranjang.view.*

class CartAdapter : RecyclerView.Adapter<CartAdapter.ViewHolder>() {
    private val datax: MutableList<KeranjangModel> = mutableListOf()
    private lateinit var listener: (String,KeranjangModel) -> Unit
    fun setData(datas: MutableList<KeranjangModel>) {
        datax.clear()
        datax.addAll(datas)
        notifyDataSetChanged()
    }

    fun addDeleteItem(listener: (String,KeranjangModel) -> Unit) {
        this.listener = listener
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemCart =
            ItemKeranjangBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(itemCart)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = datax[position]
        holder.itemView.title.text = data.title
        holder.itemView.stock.text = "${data.touch_count} Stock"
        holder.itemView.harga.text = "${rupiah(data.price!!.toDouble())}"
        Picasso.get().load(IMAGE_URL + data.image_url)
            .error(R.drawable.ic_baseline_image_24).into(holder.itemView.img)
        holder.itemView.plus.setOnClickListener {
            var quantity = holder.itemView.quantity.text.toString().toInt()
            if(data.touch_count.toInt() <= quantity){
                Toast.makeText(holder.itemView.context, "Stock ${data.title} Habis", Toast.LENGTH_LONG ).show()
            }else {
                quantity += 1
                listener("add",data)
                holder.itemView.quantity.text = quantity.toString()
            }
        }
        holder.itemView.min.setOnClickListener {
            var quantity = holder.itemView.quantity.text.toString().toInt()
            if(quantity == 0){
                holder.itemView.quantity.text = quantity.toString()
            }else {
                quantity -= 1
                listener("delete",data)
                holder.itemView.quantity.text = quantity.toString()
            }
        }
    }

    override fun getItemCount(): Int {
        return datax.size
    }


    class ViewHolder(val view: ItemKeranjangBinding) : RecyclerView.ViewHolder(view.root)
}