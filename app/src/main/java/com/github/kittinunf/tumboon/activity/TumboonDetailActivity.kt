package com.github.kittinunf.tumboon.activity

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.github.kittinunf.tumboon.view.TumboonDetailView

class TumboonDetailActivity : AppCompatActivity() {

    companion object {
        val TUMBOON_ITEM_ID_EXTRA = "tumboon_item_id_extra"
        val TUMBOON_ITEM_NAME_EXTRA = "tumboon_item_name_extra"
        val TUMBOON_ITEM_LOGO_URL_EXTRA = "tumboon_item_logo_url_extra"
    }

    var tumboonItemId: Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        intent?.let {
            tumboonItemId = intent.getIntExtra(TUMBOON_ITEM_ID_EXTRA, -1)

            val name = it.getStringExtra(TUMBOON_ITEM_NAME_EXTRA)
            val logoUrl = it.getStringExtra(TUMBOON_ITEM_LOGO_URL_EXTRA)
            val view = TumboonDetailView(this)
            view.name = name
            view.logoUrl = logoUrl
            setContentView(view)
        }
    }

}
