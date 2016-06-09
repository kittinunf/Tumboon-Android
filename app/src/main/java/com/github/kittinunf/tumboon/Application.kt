package com.github.kittinunf.tumboon

import com.github.kittinunf.tumboon.model.Tumboon
import com.github.kittinunf.tumboon.util.asSequence
import org.json.JSONArray
import org.json.JSONObject
import java.nio.charset.Charset

class Application : android.app.Application() {

    override fun onCreate() {
        super.onCreate()

        val content = assets.open("charities.json").readBytes().toString(Charset.defaultCharset())
        val json = JSONArray(content)
        json.asSequence().forEach {
            Tumboon + Tumboon.TumboonItem.init(it as JSONObject)
        }
    }

}


