package com.github.kittinunf.tumboon.fragment

import android.app.Dialog
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.support.v7.app.AlertDialog
import android.view.LayoutInflater
import android.view.View
import android.widget.EditText
import android.widget.TextView
import com.devmarvel.creditcardentry.library.CreditCard
import com.github.kittinunf.tumboon.R
import kotlinx.android.synthetic.main.fragment_donation.view.*

class DonationDialogFragment() : DialogFragment() {

    var listener: DonationResultListener? = null

    companion object {
        val TUMBOON_ITEM_ID = "tumboon_item_id"
        val TUMBOON_ITEM_CREDIT_CARD_INFO = "tumboon_item_credit_card_info"
        val TUMBOON_ITEM_DONATOR_NAME = "tumboon_item_donator_name"

        fun newInstance(itemId: Int): DonationDialogFragment {
            return DonationDialogFragment().apply {
                arguments = Bundle().apply {
                    putShort(TUMBOON_ITEM_ID, itemId.toShort())
                }
            }
        }

        fun newInstance(itemId: Int, donatorName: String, creditCardInfo: CreditCard): DonationDialogFragment {
            return DonationDialogFragment().apply {
                arguments = Bundle().apply {
                    putShort(TUMBOON_ITEM_ID, itemId.toShort())
                    putString(TUMBOON_ITEM_DONATOR_NAME, donatorName)
                    putSerializable(TUMBOON_ITEM_CREDIT_CARD_INFO, creditCardInfo)
                }
            }
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

                    listener?.onDonationConfirmed(donatorName, creditCard, amount.replace(".", ""))
                }
                .setNegativeButton("Cancel") { dialog, which -> }
                .create();
    }

    private fun setUp(layout: View) {
        arguments.let {
            // name
            val donatorName = it.getString(TUMBOON_ITEM_DONATOR_NAME)
            layout.donationNameEditText.setText(donatorName, TextView.BufferType.NORMAL)

            // re-enter credit card info
            val creditCardInfo = it.getSerializable(TUMBOON_ITEM_CREDIT_CARD_INFO) as? CreditCard

            creditCardInfo?.let {
                layout.donationCreditCardForm.apply {
                    setExpDate(creditCardInfo.expDate, false)
                    setZipCode(creditCardInfo.zipCode, false)
                    setCardNumber(creditCardInfo.cardNumber, true)
                }
            }
        }

        //set up to always show 2 decimal places on UI
        layout.donationAmountEditText.setOnFocusChangeListener { view, hasFocus ->
//            if (!hasFocus) {
                val editText = view as EditText

                var modifiedInput = ""
                val input = editText.text.toString()
                if (input.isEmpty()) {
                    modifiedInput = "0.00"
                } else {
                    val float = input.toFloat()
                    modifiedInput = java.lang.String.format("%.2f", float)
                }
                editText.setText(modifiedInput, TextView.BufferType.NORMAL)
//            }
        }
    }

    interface DonationResultListener {
        fun onDonationConfirmed(donatorName: String, creditCardInfo: CreditCard, amount: String)
    }

}