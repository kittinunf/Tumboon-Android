package com.github.kittinunf.tumboon.model

import org.json.JSONObject

object Tumboon {

    private val items = mutableListOf<TumboonItem>()

    fun items() = items.toList()

    operator fun plus(data: TumboonItem) {
        items += data
    }

    operator fun plusAssign(data: List<TumboonItem>) {
        items += data
    }

    operator fun get(index: Int): TumboonItem = items[index]

    data class TumboonItem(val id: Int, val name: String, val logoUrl: String) {
        companion object {
            fun init(j: JSONObject): Tumboon.TumboonItem {
                val id = j.getInt("id")
                val name = j.getString("name")
                val logoUrl = j.getString("logo_url")
                return Tumboon.TumboonItem(id, name, logoUrl)
            }
        }
    }
}

