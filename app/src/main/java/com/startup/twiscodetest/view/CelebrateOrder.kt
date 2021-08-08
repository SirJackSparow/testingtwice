package com.startup.twiscodetest.view

import android.animation.Animator
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import com.startup.twiscodetest.databinding.ActivityCelebrateOrderBinding
import com.startup.twiscodetest.util.BaseViewBinding
import com.startup.twiscodetest.view.main.MainActivity

class CelebrateOrder : BaseViewBinding<ActivityCelebrateOrderBinding>() {
    override fun onGetData() {
        bind.hurray.addAnimatorListener(object : Animator.AnimatorListener{
            override fun onAnimationStart(animation: Animator?) {}
            override fun onAnimationEnd(animation: Animator?) {
                startActivity(Intent(this@CelebrateOrder,MainActivity::class.java))
            }
            override fun onAnimationCancel(animation: Animator?) {}
            override fun onAnimationRepeat(animation: Animator?) {}
        })
    }

    override fun getActivityBinding(layoutinflater: LayoutInflater): ActivityCelebrateOrderBinding {
        return ActivityCelebrateOrderBinding.inflate(layoutInflater)
    }
}