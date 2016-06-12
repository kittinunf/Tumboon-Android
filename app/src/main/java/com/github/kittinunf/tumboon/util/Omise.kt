package com.github.kittinunf.tumboon.util

import com.github.kittinunf.fuel.core.Deserializable
import com.github.kittinunf.fuel.core.Response
import com.github.kittinunf.fuel.httpPost
import com.github.kittinunf.fuel.rx.rx_object
import com.github.kittinunf.fuel.rx.rx_string
import org.json.JSONObject
import rx.Observable
import java.nio.charset.Charset

private val omisePKey = "pkey_test_54ab0dh16q70kne0f4g"
private val omiseSKey = "<S Key is stored safely in server>"

fun omiseGetToken(name: String, cardNumber: String, expMonth: Int, expYear: Int, zip: String, securityCode: String): Observable<String> {
    return "https://vault.omise.co/tokens".httpPost(
            listOf("card[name]" to name,
                    "card[number]" to cardNumber,
                    "card[expiration_month]" to expMonth,
                    "card[expiration_year]" to expYear,
                    "card[city]" to "Bangkok",
                    "card[postal_code]" to zip,
                    "card[security_code]" to securityCode))
            .authenticate(omisePKey, "").rx_object(object : Deserializable<JSONObject> {
        override fun deserialize(response: Response): JSONObject {
            return JSONObject(response.data.toString(Charset.defaultCharset()))
        }
    }).map {
        it.getString("id")
    }
}

// this function called should not be done at application level, as API Key can be exposed by decomplilation of .apk
fun omiseCharge(amount: String, cardId: String): Observable<String> {
    return "https://api.omise.co/charges".httpPost(
            listOf("amount" to amount,
                    "currency" to "thb",
                    "card" to cardId))
            .authenticate(omiseSKey, "").rx_string()
}

fun omiseProcessError(rawData: String): String {
    val json = JSONObject(rawData)
    return json.getString("message")?.replace("2000", "20.00") ?: "Something wrong, please try again later."
}

