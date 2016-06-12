package com.github.kittinunf.tumboon.model

import org.json.JSONObject

object Tumboon {

    private val _items = mutableListOf<Charity>()

    val items: List<Charity> = _items

    operator fun plus(data: Charity) {
        _items += data
    }

    operator fun plusAssign(data: List<Charity>) {
        _items += data
    }

    operator fun get(index: Int): Charity = _items[index]

    operator fun set(index: Int, value: Charity) {
        _items[index] = value
    }

    fun fetch(id: Int) = _items.findLast { it.id == id }

    fun update(id: Int, value: Charity): Boolean {
        val index = _items.indexOfFirst { it.id == id }
        if (index == -1) {
            return false
        } else {
            _items[index] = value
            return true
        }
    }

    data class Charity(val id: Int, val name: String, val logoUrl: String, val coverUrl: String, val desc: String, val donationCount: Int, val donationAmount: Double) {
        companion object {
            fun init(j: JSONObject): Tumboon.Charity {
                val id = j.getInt("id")
                val name = j.getString("name")
                val logoUrl = j.getString("logo_url")
                val coverUrl = j.getString("cover_url")
                val desc = j.getString("desc")
                val count = j.getInt("donator_count")
                val amount = j.getDouble("donation_amount")
                return Tumboon.Charity(id, name, logoUrl, coverUrl, desc, count, amount)
            }
        }
    }
}

