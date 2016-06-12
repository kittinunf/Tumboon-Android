package com.github.kittinunf.tumboon.model

import com.github.kittinunf.fuel.android.core.Json
import com.github.kittinunf.fuel.core.Deserializable
import com.github.kittinunf.fuel.core.Response
import com.github.kittinunf.tumboon.util.asSequence
import org.json.JSONObject
import java.nio.charset.Charset

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

    class Deserializer : Deserializable<Charity> {
        override fun deserialize(response: Response): Charity {
            val json = Json(response.data.toString(Charset.defaultCharset()))
            return Charity.init(json.obj())
        }
    }

    class ListDeserializer : Deserializable<List<Charity>> {
        override fun deserialize(response: Response): List<Charity> {
            val json = Json(response.data.toString(Charset.defaultCharset()))
            return json.array().asSequence().map { Charity.init(it as JSONObject) }.toList()
        }
    }

}
