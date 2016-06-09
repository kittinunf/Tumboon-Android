package com.github.kittinunf.tumboon.activity

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import com.devmarvel.creditcardentry.library.CreditCard
import com.github.kittinunf.tumboon.fragment.DonationDialogFragment
import com.github.kittinunf.tumboon.util.omiseCharge
import com.github.kittinunf.tumboon.util.omiseGetToken
import com.github.kittinunf.tumboon.view.TumboonDetailView
import rx.android.schedulers.AndroidSchedulers

class TumboonDetailActivity : AppCompatActivity(), DonationDialogFragment.DonationResultListener {

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
            view.onFabClick = {
                val f = DonationDialogFragment.newInstance(tumboonItemId)
                f.listener = this@TumboonDetailActivity
                f.show(supportFragmentManager, "fragment_donation")
            }
            setContentView(view)
        }
    }

    override fun onDonationConfirmed(donatorName: String, creditCardInfo: CreditCard, amount: String) {
        val tokenAndCharges = omiseGetToken(donatorName,
                creditCardInfo.cardNumber,
                creditCardInfo.expMonth,
                creditCardInfo.expYear,
                creditCardInfo.zipCode,
                creditCardInfo.securityCode).flatMap {
            omiseCharge(amount, it)
        }

        tokenAndCharges.observeOn(AndroidSchedulers.mainThread()).subscribe({
            Toast.makeText(this, "Your donation is successful!", Toast.LENGTH_SHORT).show()
        }, {
            Toast.makeText(this, "There is an error processing your donation", Toast.LENGTH_SHORT).show()
        })
    }

}
