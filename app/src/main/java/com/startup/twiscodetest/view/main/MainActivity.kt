package com.startup.twiscodetest.view.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.Toast
import com.startup.twiscodetest.data.model.ModelDataItem
import com.startup.twiscodetest.databinding.ActivityMainBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject
import android.app.AlertDialog
import android.content.Intent
import android.widget.Button
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.startup.twiscodetest.R
import com.startup.twiscodetest.util.*
import com.startup.twiscodetest.view.cart.CartActivity
import kotlinx.android.synthetic.main.dialog_layout.*

class MainActivity : BaseViewBinding<ActivityMainBinding>() {
    private val vm: MainViewModel by inject()
    private lateinit var adapters: MainAdapter
    private var categoryData = mutableSetOf("all")
    private val temp: MutableList<ModelDataItem> = mutableListOf()
    private val datareal: MutableList<ModelDataItem> = mutableListOf()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (vm.checkConnection(this)) {
            CoroutineScope(Dispatchers.Main).launch {
                vm.getData()
            }
        } else {
            bind.loading.visibility = View.GONE
            bind.tryAgain.visibility = View.VISIBLE
            Toast.makeText(this, CHECK_CONNECTION, Toast.LENGTH_SHORT).show()
        }
        onAction()
        getKeranjang()
    }

    override fun onGetData() {
        adapters = MainAdapter()
        vm.datax.observe(this, { data ->
            when {
                data == null -> {
                    bind.loading.visibility = View.GONE
                    bind.tryAgain.visibility = View.VISIBLE
                    Toast.makeText(this, TIMEOUT, Toast.LENGTH_SHORT).show()
                }
                data.isNotEmpty() -> {
                    bind.loading.visibility = View.GONE
                    bind.tryAgain.visibility = View.GONE
                    adapters.setData(data as MutableList<ModelDataItem>)
                    datareal.addAll(data)
                    data.forEach {
                        categoryData.add(it.category!!.cat_name)
                    }
                }
                else -> {
                    bind.loading.visibility = View.GONE
                    Toast.makeText(this, EMPTY_DATA, Toast.LENGTH_SHORT).show()
                }
            }
        })
        bind.listproduct.adapter = adapters
    }

    private fun getKeranjang(){
        CoroutineScope(Dispatchers.Main).launch{
            if (vm.getSize() > 0){
                bind.cartBadge.visibility = View.VISIBLE
                if (vm.getSize() > 100){
                    bind.cartBadge.text = "99+"
                }else {
                    bind.cartBadge.text = vm.getSize().toString()
                }
            }else {
                bind.cartBadge.visibility = View.GONE
            }
        }
    }

    private fun onAction() {
        bind.keranjang.setOnClickListener {
            startActivity(Intent(this,CartActivity::class.java))
        }
        bind.tryAgain.setOnClickListener {
            if (vm.checkConnection(this)) {
                bind.loading.visibility = View.VISIBLE
                bind.tryAgain.visibility = View.GONE
                CoroutineScope(Dispatchers.Main).launch {
                    vm.getData()
                }
            } else {
                bind.loading.visibility = View.GONE
                bind.tryAgain.visibility = View.VISIBLE
                Toast.makeText(this, CHECK_CONNECTION, Toast.LENGTH_SHORT).show()
            }
        }
        bind.filter.setOnClickListener {
            val dialog = AlertDialog.Builder(this).create()
            val view = layoutInflater.inflate(R.layout.dialog_layout_filter, null)
            val list = view.findViewById<RecyclerView>(R.id.list_category)
            val btn = view.findViewById<Button>(R.id.pilih)
            val data = mutableListOf("Bayar","Gratis")
            val filter = mutableSetOf<String>()
            if (temp.isEmpty()){
                temp.addAll(datareal)
            }
            list.apply {
                layoutManager = LinearLayoutManager(this@MainActivity)
                adapter = FilterAdapter(data) { data, isChecked->
                    if (isChecked){
                        filter.add(data)
                    }else if (filter.isNotEmpty() && !isChecked){
                        filter.remove(data)
                    }
                }
            }
            btn.setOnClickListener {
                val temp2 = mutableListOf<ModelDataItem>()
                if (filter.isNotEmpty()) {
                    filter.forEach {
                       if (it == "Gratis"){
                           temp2.addAll(temp.filter { it.is_free == "1" })
                       }
                       if (it == "Bayar"){
                           temp2.addAll(temp.filter { it.is_free!!.isEmpty() })
                       }
                    }
                    adapters.setData(temp2)
                }
                dialog.dismiss()
            }
            dialog.setView(view)
            dialog.show()
        }
        bind.category.setOnClickListener {
            val dialog = AlertDialog.Builder(this).create()
            val view = layoutInflater.inflate(R.layout.dialog_layout, null)
            val list = view.findViewById<RecyclerView>(R.id.list_category)
            val data = mutableListOf<String>()
            data.addAll(categoryData)
            list.apply {
                layoutManager = LinearLayoutManager(this@MainActivity)
                adapter = DialogAdapter(data) {
                    temp.clear()
                    datareal.forEach { datax ->
                        if (it == datax.category!!.cat_name) {
                            temp.add(datax)
                        } else if (it == "all") {
                            temp.addAll(datareal)
                            return@forEach
                        }
                    }
                    adapters.setData(temp)
                    dialog.dismiss()
                }
            }
            dialog.setView(view)
            dialog.show()
        }

        adapters.onClicked {
            CoroutineScope(Dispatchers.Main).launch {
                when {
                    it.touch_count!!.toInt() == 0 -> {
                        Toast.makeText(this@MainActivity, "Barang Habis", Toast.LENGTH_SHORT).show()
                    }
                    vm.getDataKeranjang().any { keranjang -> it.id == keranjang.ids } -> {
                        Toast.makeText(this@MainActivity, "Barang Sudah Ditambah", Toast.LENGTH_SHORT).show()
                    }
                    else -> {
                        vm.addKeranjang(it)
                        getKeranjang()
                    }
                }
            }
        }
    }

    override fun getActivityBinding(layoutinflater: LayoutInflater): ActivityMainBinding {
        return ActivityMainBinding.inflate(layoutInflater)
    }
}