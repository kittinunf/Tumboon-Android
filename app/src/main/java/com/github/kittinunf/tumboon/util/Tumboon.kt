package com.github.kittinunf.tumboon.util

import com.github.kittinunf.fuel.Fuel
import com.github.kittinunf.fuel.rx.rx_object
import com.github.kittinunf.fuel.rx.rx_string
import com.github.kittinunf.tumboon.model.Charity
import rx.Observable

fun tumboonGetCharities(): Observable<List<Charity>> {
    return Fuel.get("api/charities").rx_object(Charity.ListDeserializer())
}

fun tumboonGetCharity(id: Int): Observable<Charity> {
    return Fuel.get("api/charities/$id").rx_object(Charity.Deserializer())
}

fun tumboonPostDonation(id: Int, cardToken: String, amount: String): Observable<String> {
    return Fuel.post("api/charities/$id/donate", listOf("card" to cardToken, "currency" to "thb", "amount" to amount)).rx_string()
}
