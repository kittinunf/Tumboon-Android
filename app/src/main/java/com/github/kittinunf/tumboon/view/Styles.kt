package com.github.kittinunf.tumboon.view

import android.content.Context
import android.support.annotation.ColorRes
import android.support.design.widget.CoordinatorLayout
import android.support.design.widget.FloatingActionButton
import android.support.v4.content.ContextCompat
import android.support.v4.view.ViewCompat
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import com.github.kittinunf.tumboon.R
import com.squareup.picasso.Picasso
import trikita.anvil.Anvil
import trikita.anvil.DSL.*
import trikita.anvil.BaseDSL.R as resource

//region TextView

fun headLineTextView() = {
    size(WRAP, WRAP)
    margin(dip(16))
    textSize(sip(20).toFloat())
}()

fun titleTextView() = {
    size(WRAP, WRAP)
    margin(dip(16))
    textSize(sip(18).toFloat())
}()

//endregion


//region ImageView

fun loadImageUrl(context: Context, url: String) = {
    Anvil.currentView<ImageView>()?.let {
        Picasso.with(context).load(url).placeholder(R.mipmap.ic_launcher).into(it)
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

//region AppBarLayout

//endregion

//

//region Group of Views
fun twoTextVertical(text1: CharSequence, text2: CharSequence) = {
    linearLayout {
        size(WRAP, WRAP)

        orientation(LinearLayout.VERTICAL)

        textView {
            size(WRAP, WRAP)
            text(text1)
            margin(dip(8))
            textSize(sip(18).toFloat())
        }

        textView {
            size(WRAP, WRAP)
            text(text2)
            margin(dip(8))
            textSize(sip(18).toFloat())
        }

        gravity(CENTER)
    }
}()

//endregion

//region Behavior
class ScrollAwareBehavior : FloatingActionButton.Behavior() {

    override fun onStartNestedScroll(coordinatorLayout: CoordinatorLayout?, child: FloatingActionButton?, directTargetChild: View?, target: View?, nestedScrollAxes: Int): Boolean {
        return nestedScrollAxes == ViewCompat.SCROLL_AXIS_VERTICAL
    }

    override fun onNestedScroll(coordinatorLayout: CoordinatorLayout?, child: FloatingActionButton, target: View?, dxConsumed: Int, dyConsumed: Int, dxUnconsumed: Int, dyUnconsumed: Int) {
        super.onNestedScroll(coordinatorLayout, child, target, dxConsumed, dyConsumed, dxUnconsumed, dyUnconsumed)
        if (dyConsumed > 0 && child.visibility == View.VISIBLE) {
            child.hide();
        } else if (dyConsumed < 0 && child.visibility != View.VISIBLE) {
            child.show();
        }
    }
}
//endregion
 
