package com.github.kittinunf.tumboon.activity

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.github.kittinunf.tumboon.model.Tumboon
import com.github.kittinunf.tumboon.view.TumboonListView
import android.support.v4.util.Pair as AndroidPair

class TumboonListActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val mainView = TumboonListView(this)

        mainView.onItemClick = {
            val intent = Intent(this, TumboonDetailActivity::class.java)
            intent.putExtra(TumboonDetailActivity.TUMBOON_ITEM_ID_EXTRA, Tumboon[it].id)
            intent.putExtra(TumboonDetailActivity.TUMBOON_ITEM_NAME_EXTRA, Tumboon[it].name)
            intent.putExtra(TumboonDetailActivity.TUMBOON_ITEM_LOGO_URL_EXTRA, Tumboon[it].logoUrl)
            startActivity(intent)
        }

        setContentView(mainView)
    }

}
