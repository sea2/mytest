package com.xcm91.relation.kotlin

import android.app.Activity
import android.os.Bundle
import android.util.Log
import kotlinx.android.synthetic.main.activity_kotlin_test.*

class KotlinTestActivity : Activity() {

    internal var tag = this.javaClass.simpleName

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_kotlin_test)
        tv_test1.text="kotlin"

        tv_test1.setOnClickListener {
            Log.e(tag,"测试点击事件")
        }
    }


}
