package com.github.kittinunf.tumboon.activity

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import com.github.kittinunf.tumboon.view.MainView

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val mainView = MainView(this)

        mainView.onItemClick = {
            Log.i(javaClass.simpleName, it.toString())
        }

        setContentView(mainView)
    }

}
