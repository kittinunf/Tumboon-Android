package com.github.kittinunf.tumboon.model

import org.json.JSONObject

data class Charity(val id: Int, val name: String, val logoUrl: String, val coverUrl: String, val desc: String, val donationCount: Int, val donationAmount: Double) {
    companion object {
        fun init(j: JSONObject): Charity {
            val id = j.getInt("id")
            val name = j.getString("name")
            val logoUrl = j.getString("logo_url")
            val coverUrl = j.getString("cover_url")
            val desc = j.getString("desc")
            val count = j.getInt("donator_count")
            val amount = j.getDouble("donation_amount")
            return Charity(id, name, logoUrl, coverUrl, desc, count, amount)
        }
    }
}
