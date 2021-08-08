package com.startup.twiscodetest.util

import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import androidx.viewbinding.ViewBinding

abstract class BaseViewBinding<vb: ViewBinding> : AppCompatActivity() {

    lateinit var bind : vb
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bind = getActivityBinding(layoutInflater)
        val view = bind.root
        setContentView(view)
        onGetData()
    }

    abstract fun onGetData()
    abstract fun getActivityBinding(layoutinflater: LayoutInflater ) : vb
}