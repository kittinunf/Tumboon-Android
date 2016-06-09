package com.github.kittinunf.tumboon.fragment

import android.app.Dialog
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.support.v7.app.AlertDialog
import android.view.LayoutInflater
import android.view.View
import com.devmarvel.creditcardentry.library.CreditCard
import com.github.kittinunf.tumboon.R
import kotlinx.android.synthetic.main.fragment_donation.view.*

class DonationDialogFragment() : DialogFragment() {

    var listener: DonationResultListener? = null

    companion object {
        val TUMBOON_ITEM_ID = "tumboon_item_id"

        fun newInstance(itemId: Int): DonationDialogFragment {
            val f = DonationDialogFragment()
            f.arguments = Bundle().apply {
                putShort(TUMBOON_ITEM_ID, itemId.toShort())
            }
            return f
        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val layout = LayoutInflater.from(activity).inflate(R.layout.fragment_donation, null)
        setUp(layout)
        return AlertDialog.Builder(activity)
                .setTitle("Donation")
                .setView(layout)
                .setPositiveButton("OK") { dialog, which ->
                    val creditCard = layout.donationCreditCardForm.creditCard
                    val donatorName = layout.donationNameEditText.text.toString()
                    val amount = layout.donationAmountEditText.text.toString()
                    listener?.onDonationConfirmed(donatorName, creditCard, amount)
                }
                .setNegativeButton("Cancel") { dialog, which -> }
                .create();
    }

    private fun setUp(layout: View) {
    }

    interface DonationResultListener {
        fun onDonationConfirmed(donatorName: String, creditCardInfo: CreditCard, amount: String)
    }

}