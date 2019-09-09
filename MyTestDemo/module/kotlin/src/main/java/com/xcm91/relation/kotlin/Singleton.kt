package com.xcm91.relation.kotlin

/**
 * Created by lhy on 2019/8/29.
 */

class Singleton private constructor() {
    companion object {
        private var instance: Singleton? = null
            get() {
                if (field == null) {
                    field = Singleton()
                }
                return field
            }
        @Synchronized
        fun get(): Singleton{
            return instance!!
        }
    }

}