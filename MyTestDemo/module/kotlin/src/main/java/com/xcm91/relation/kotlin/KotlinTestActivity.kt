package com.xcm91.relation.kotlin

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_kotlin_test.*

/** var是一个可变变量，这是一个可以通过重新分配来更改为另一个值的变量。这种声明变量的方式和Java中声明变量的方式一样。
 * val是一个只读变量，这种声明变量的方式相当于java中的final变量。一个val创建的时候必须初始化，因为以后不能被改变。
 * */
class KotlinTestActivity : Activity() {

     val tag = this.javaClass.simpleName

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_kotlin_test)
        tv_test1.text = "kotlin"

        tv_test1.setOnClickListener {
            val it = Intent(this@KotlinTestActivity, MainActivity::class.java)
            startActivity(it)
        }


    }


}
