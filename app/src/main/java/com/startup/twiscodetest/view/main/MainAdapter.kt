package com.startup.twiscodetest.view.main

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import com.startup.twiscodetest.R
import com.startup.twiscodetest.data.model.ModelDataItem
import com.startup.twiscodetest.databinding.ItemShopBinding
import com.startup.twiscodetest.util.IMAGE_URL
import com.startup.twiscodetest.util.rupiah

class MainAdapter : RecyclerView.Adapter<MainAdapter.ViewHolder>() {
    private val datax: MutableList<ModelDataItem> = mutableListOf()
    private lateinit var listener: (ModelDataItem) -> Unit
    fun setData(datas: MutableList<ModelDataItem>) {
        datax.clear()
        datax.addAll(datas)
        notifyDataSetChanged()
    }

    fun onClicked(listener: (ModelDataItem) -> Unit) {
        this.listener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemShopBinding =
            ItemShopBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(itemShopBinding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = datax[position]
        holder.bind(data)
        holder.itemView.setOnClickListener {
            listener(data)
        }
    }

    override fun getItemCount(): Int {
        return datax.size
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }

    class ViewHolder(val view: ItemShopBinding) : RecyclerView.ViewHolder(view.root) {
        fun bind(data: ModelDataItem) {
            view.title.text = data.title
            view.price.text = "${rupiah(data.price!!.toDouble())}"
            view.user.text = data.user!!.user_name
            view.location.text = data.location_name
            Picasso.get().load(IMAGE_URL + data.default_photo!!.img_path)
                .error(R.drawable.ic_baseline_image_24).into(view.images)
            if (data.is_halal == "0" || data.is_halal!!.isEmpty()  || data.category!!.is_food == "0") {
                view.halal.visibility = View.GONE
            }else {
                view.halal.visibility = View.VISIBLE
            }
            if (data.touch_count == "0") {
                view.btn.text = "Habis"
            } else if (data.touch_count!!.isNotEmpty() && data.touch_count.toInt() > 99 && data.is_free!!.isEmpty()) {
                view.btn.text = "99+ Stock"
            } else if (data.is_free == "1") {
                view.btn.text = "Gratis"
            } else {
                view.btn.text = "${data.touch_count} Stock"
            }
        }
    }

}