package com.iteach.taxi
import  android.app.Application
import io.paperdb.Paper
open class Application:Application() {
    override fun onCreate() {
        super.onCreate()
    Paper.init(applicationContext)
    }
}