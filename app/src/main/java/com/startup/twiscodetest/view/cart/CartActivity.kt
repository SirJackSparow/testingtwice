package com.startup.twiscodetest.view.cart

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.startup.twiscodetest.databinding.ActivityCartBinding
import com.startup.twiscodetest.util.BaseViewBinding
import com.startup.twiscodetest.util.rupiah
import com.startup.twiscodetest.view.CelebrateOrder
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject

class CartActivity : BaseViewBinding<ActivityCartBinding>() {
    private val vm: CartViewModel by inject()
    private var jumlahOrder = 0
    private var quantity = 0
    private var first = false
    private lateinit var adapterKeranjang: CartAdapter
    private fun onAction() {
        bind.order.setOnClickListener {
            if (first && quantity != 0){
                CoroutineScope(Dispatchers.IO).launch {
                    vm.deleteAll()
                }
                startActivity(Intent(this,CelebrateOrder::class.java))
            }else {
                Toast.makeText(this, "Mau Beli Apa Quantity Anda Kosong?", Toast.LENGTH_SHORT).show()
            }

        }
        adapterKeranjang.addDeleteItem{ type , data ->
            first = true
            if (type == "add"){
                jumlahOrder += data.price.toInt()
                bind.jumlahOrder.text = rupiah(jumlahOrder.toDouble())
                quantity +=1
            }else{
                quantity -= 1
                if (jumlahOrder > 0){
                    Log.e("kurang rene", jumlahOrder.toString() +" - "+ data.price )
                    bind.jumlahOrder.text = rupiah ((jumlahOrder - data.price.toInt()).toDouble())
                    jumlahOrder -= data.price.toInt()
                }
            }
        }
    }

    override fun onGetData() {
        CoroutineScope(Dispatchers.Main).launch {
            adapterKeranjang = CartAdapter()
            adapterKeranjang.setData(vm.getKeranjang())
            bind.listKeranjang.apply {
                layoutManager = LinearLayoutManager(this@CartActivity)
                adapter = adapterKeranjang
            }
            onAction()
        }
    }

    override fun getActivityBinding(layoutinflater: LayoutInflater): ActivityCartBinding {
        return ActivityCartBinding.inflate(layoutinflater)
    }
}