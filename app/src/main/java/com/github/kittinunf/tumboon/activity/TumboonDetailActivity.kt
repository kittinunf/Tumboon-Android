package com.github.kittinunf.tumboon.activity

import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.widget.Toast
import com.devmarvel.creditcardentry.library.CreditCard
import com.github.kittinunf.tumboon.fragment.DonationDialogFragment
import com.github.kittinunf.tumboon.util.omiseGetToken
import com.github.kittinunf.tumboon.util.tumboonGetCharity
import com.github.kittinunf.tumboon.util.tumboonPostDonation
import com.github.kittinunf.tumboon.view.TumboonDetailView
import rx.android.schedulers.AndroidSchedulers

class TumboonDetailActivity : AppCompatActivity(), DonationDialogFragment.DonationResultListener {

    companion object {
        val TUMBOON_ITEM_ID_EXTRA = "tumboon_item_id_extra"
    }

    var tumboonItemId: Int = -1

    private lateinit var view: TumboonDetailView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        intent?.let {
            tumboonItemId = intent.getIntExtra(TUMBOON_ITEM_ID_EXTRA, -1)

            view = TumboonDetailView(this)
            view.charityId = tumboonItemId
            view.onFabClick = {
                showDonationDialog()
            }
            setContentView(view)

            callApiAndUpdateView()
        }
    }

    fun callApiAndUpdateView() {
        tumboonGetCharity(tumboonItemId).subscribe({
            view.item = it
        }, {
            Log.e(javaClass.simpleName, it.toString())
            AlertDialog.Builder(this@TumboonDetailActivity)
                    .setTitle("Error")
                    .setMessage("There is something wrong, please try again later")
                    .setNegativeButton(android.R.string.cancel, null)
                    .show()
        })
    }

    override fun onDonationConfirmed(donatorName: String, creditCardInfo: CreditCard, amount: String) {
        val tokenAndCharges = omiseGetToken(donatorName,
                creditCardInfo.cardNumber,
                creditCardInfo.expMonth,
                creditCardInfo.expYear,
                creditCardInfo.zipCode,
                creditCardInfo.securityCode).flatMap {
            tumboonPostDonation(tumboonItemId, it, amount)
        }

        tokenAndCharges.observeOn(AndroidSchedulers.mainThread()).subscribe({
            Toast.makeText(this, "Your donation is successful!", Toast.LENGTH_SHORT).show()

            // update donation amount
            callApiAndUpdateView()
        }, {
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
