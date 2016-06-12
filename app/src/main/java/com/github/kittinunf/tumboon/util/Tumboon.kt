package com.github.kittinunf.tumboon.util

import com.github.kittinunf.fuel.Fuel
import com.github.kittinunf.fuel.android.core.Json
import com.github.kittinunf.fuel.core.Deserializable
import com.github.kittinunf.fuel.core.Response
import com.github.kittinunf.fuel.rx.rx_object
import com.github.kittinunf.fuel.rx.rx_string
import com.github.kittinunf.tumboon.model.Charity
import org.json.JSONObject
import rx.Observable
import java.nio.charset.Charset

fun tumboonGetCharities(): Observable<List<Charity>> {
    return Fuel.get("api/charities").rx_object(object : Deserializable<List<Charity>> {
        override fun deserialize(response: Response): List<Charity> {
            val json = Json(response.data.toString(Charset.defaultCharset()))
            return json.array().asSequence().map { Charity.init(it as JSONObject) }.toList()
        }
    })
}

fun tumboonGetCharity(id: Int): Observable<Charity> {
    return Fuel.get("api/charities/$id").rx_object(object : Deserializable<Charity> {
        override fun deserialize(response: Response): Charity {
            val json = Json(response.data.toString(Charset.defaultCharset()))
            return Charity.init(json.obj())
        }
    })
}

fun tumboonPostDonation(id: Int, cardToken: String, amount: String): Observable<String> {
    return Fuel.post("api/charities/$id/donate", listOf("card" to cardToken, "currency" to "thb", "amount" to amount)).rx_string()
}
