package com.github.kittinunf.tumboon.activity

import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.widget.Toast
import com.devmarvel.creditcardentry.library.CreditCard
import com.github.kittinunf.fuel.Fuel
import com.github.kittinunf.fuel.android.extension.responseJson
import com.github.kittinunf.fuel.core.FuelError
import com.github.kittinunf.fuel.rx.rx_string
import com.github.kittinunf.tumboon.fragment.DonationDialogFragment
import com.github.kittinunf.tumboon.model.Tumboon
import com.github.kittinunf.tumboon.util.omiseGetToken
import com.github.kittinunf.tumboon.view.TumboonDetailView
import rx.android.schedulers.AndroidSchedulers
import trikita.anvil.Anvil

class TumboonDetailActivity : AppCompatActivity(), DonationDialogFragment.DonationResultListener {

    companion object {
        val TUMBOON_ITEM_ID_EXTRA = "tumboon_item_id_extra"
    }

    var tumboonItemId: Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        intent?.let {
            tumboonItemId = intent.getIntExtra(TUMBOON_ITEM_ID_EXTRA, -1)

            val view = TumboonDetailView(this)
            view.charityId = tumboonItemId
            view.onFabClick = {
                showDonationDialog()
            }
            setContentView(view)

            Fuel.get("api/charities/$tumboonItemId").responseJson { request, response, result ->
                result.fold({
                    val charity = Tumboon.Charity.init(it.obj())
                    Tumboon.update(tumboonItemId, charity)
                    //update view
                    Anvil.render()
                }, {
                    Log.e(javaClass.simpleName, it.toString())
                })
            }
        }
    }

    override fun onDonationConfirmed(donatorName: String, creditCardInfo: CreditCard, amount: String) {
        val tokenAndCharges = omiseGetToken(donatorName,
                creditCardInfo.cardNumber,
                creditCardInfo.expMonth,
                creditCardInfo.expYear,
                creditCardInfo.zipCode,
                creditCardInfo.securityCode).flatMap {
            Fuel.post("api/charities/$tumboonItemId/donate", listOf("card" to it, "currency" to "thb", "amount" to amount)).rx_string()
        }

        tokenAndCharges.observeOn(AndroidSchedulers.mainThread()).subscribe({
            Toast.makeText(this, "Your donation is successful!", Toast.LENGTH_SHORT).show()
        }, {
            val error = it as FuelError
            AlertDialog.Builder(this@TumboonDetailActivity)
                    .setTitle("Error")
                    .setMessage("There is an error processing your donation")
                    .setPositiveButton("Try Again") { dialog, which ->
                        showDonationDialog(donatorName, amount, creditCardInfo)
                    }
                    .setNegativeButton(android.R.string.cancel, null)
                    .show()
        })
    }

    fun showDonationDialog() {
        DonationDialogFragment.newInstance(tumboonItemId).apply {
            listener = this@TumboonDetailActivity
        }.show(supportFragmentManager, "fragment_donation")
    }

    fun showDonationDialog(donatorName: String, donationAmount: String, creditCardInfo: CreditCard) {
        DonationDialogFragment.newInstance(tumboonItemId, donatorName, donationAmount, creditCardInfo).apply {
            listener = this@TumboonDetailActivity
        }.show(supportFragmentManager, "fragment_donation")
    }

}
