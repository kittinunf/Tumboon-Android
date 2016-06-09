package com.github.kittinunf.tumboon.view

import android.content.Context
import android.support.annotation.ColorRes
import android.support.v4.content.ContextCompat
import android.widget.ImageView
import com.squareup.picasso.Picasso
import trikita.anvil.Anvil
import trikita.anvil.DSL.*

//region TextView

fun headLineTextView() = {
    size(WRAP, WRAP)
    margin(dip(16))
    textSize(sip(20).toFloat())
}()

//endregion


//region ImageView

fun loadImageUrl(context: Context, url: String) = {
    Anvil.currentView<ImageView>()?.let {
        Picasso.with(context).load(url).into(it)
    }
}()

fun mediumSizeLogo() = {
    size(dip(56), dip(56))
    margin(dip(8))
}()

//endregion

//region View

fun lineView(context: Context, @ColorRes color: Int) = {
    size(MATCH, dip(1))
    backgroundColor(ContextCompat.getColor(context, color))
}()

//endregion
 
