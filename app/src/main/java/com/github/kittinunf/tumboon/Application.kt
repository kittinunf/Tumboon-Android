package com.github.kittinunf.tumboon

import com.github.kittinunf.fuel.core.FuelManager

class Application : android.app.Application() {

    override fun onCreate() {
        super.onCreate()

        FuelManager.instance.basePath = "http://10.0.3.2:8888"
    }

}


