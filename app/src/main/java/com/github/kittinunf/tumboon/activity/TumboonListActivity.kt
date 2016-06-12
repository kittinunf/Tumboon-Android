package com.github.kittinunf.tumboon.activity

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import com.github.kittinunf.fuel.Fuel
import com.github.kittinunf.fuel.android.extension.responseJson
import com.github.kittinunf.tumboon.model.Tumboon
import com.github.kittinunf.tumboon.util.asSequence
import com.github.kittinunf.tumboon.view.TumboonListView
import org.json.JSONObject
import trikita.anvil.Anvil

class TumboonListActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val mainView = TumboonListView(this)

        mainView.onItemClick = {
            val intent = Intent(this, TumboonDetailActivity::class.java)
            intent.putExtra(TumboonDetailActivity.TUMBOON_ITEM_ID_EXTRA, Tumboon[it].id)
            startActivity(intent)
        }

        setContentView(mainView)

        Fuel.get("api/charities").responseJson { request, response, result ->
            result.fold({
                val charities = it.array().asSequence().map { Tumboon.Charity.init(it as JSONObject) }.toList()
                Tumboon += charities

                //update view
                Anvil.render()
            }, {
                Log.e(javaClass.simpleName, it.toString())
            })
        }
    }

}
