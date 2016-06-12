package com.github.kittinunf.tumboon.activity

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.util.Log
import com.github.kittinunf.tumboon.util.tumboonGetCharities
import com.github.kittinunf.tumboon.view.TumboonListView

class TumboonListActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val mainView = TumboonListView(this)

        mainView.onItemClick = {
            val intent = Intent(this, TumboonDetailActivity::class.java)
            intent.putExtra(TumboonDetailActivity.TUMBOON_ITEM_ID_EXTRA, it)
            startActivity(intent)
        }

        setContentView(mainView)

        tumboonGetCharities().subscribe({
            mainView.items = it
        }, {
            Log.e(javaClass.simpleName, it.toString())
            AlertDialog.Builder(this@TumboonListActivity)
                    .setTitle("Error")
                    .setMessage("There is something wrong, please try again later")
                    .setNegativeButton(android.R.string.cancel, null)
                    .show()
        })
    }

}
