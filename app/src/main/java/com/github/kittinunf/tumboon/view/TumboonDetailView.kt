package com.github.kittinunf.tumboon.view

import android.content.Context
import android.support.design.widget.AppBarLayout
import android.support.design.widget.CoordinatorLayout
import android.support.design.widget.FloatingActionButton
import android.support.v4.content.ContextCompat
import android.util.TypedValue
import android.view.Gravity
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import com.github.kittinunf.tumboon.R
import trikita.anvil.Anvil
import trikita.anvil.DSL.*
import trikita.anvil.RenderableView
import trikita.anvil.design.DesignDSL.*
import trikita.anvil.support.v4.Supportv4DSL.nestedScrollView

class TumboonDetailView(context: Context) : RenderableView(context) {

    var onFabClick: ((View) -> Unit)? = null

    var name = ""
    var logoUrl = ""

    override fun view() {
        coordinatorLayout {
            size(MATCH, MATCH)
            fitsSystemWindows(true)

            appBarLayout {
                id(R.id.detail_header_app_bar)
                size(MATCH, dip(180))
                fitsSystemWindows(true)

                collapsingToolbarLayout {
                    size(MATCH, MATCH)
                    contentScrimColor(valueForAttrStyle(R.attr.colorPrimary))

                    imageView {
                        size(MATCH, MATCH)
                        adjustViewBounds(true)
                        fitsSystemWindows(true)
                        scaleType(ImageView.ScaleType.CENTER_CROP)
                        loadImageUrl(context, "http://worldvision.or.th/News&Events/images/tesco1.jpg")
                    }
                }
            }

            nestedScrollView {
                id(R.id.detail_content_scroll)
                size(MATCH, MATCH)

                val scroll = Anvil.currentView<View>()
                val p = scroll.layoutParams as CoordinatorLayout.LayoutParams
                p.behavior = AppBarLayout.ScrollingViewBehavior()
                scroll.layoutParams = p

                linearLayout {
                    orientation(LinearLayout.VERTICAL)
                    gravity(CENTER_HORIZONTAL)
                    padding(dip(8))

                    imageView {
                        mediumSizeLogo()
                        if (logoUrl.isNotBlank()) {
                            loadImageUrl(context, logoUrl)
                        }
                        margin(dip(8))
                    }

                    textView {
                        headLineTextView()
                        text(name)
                        textColor(ContextCompat.getColor(context, R.color.colorAccent))
                        gravity(CENTER_HORIZONTAL)
                    }

                    linearLayout {
                        orientation(LinearLayout.HORIZONTAL)
                        twoTextVertical("Donators", "46")
                        space {
                            size(dip(16), MATCH)
                        }
                        twoTextVertical("Total Donation", "3,485")

                        gravity(CENTER_HORIZONTAL)
                    }

                    textView {
                        headLineTextView()
                        text("Description")
                        textColor(ContextCompat.getColor(context, R.color.colorAccent))
                        gravity(CENTER_HORIZONTAL)
                    }

                    textView {
                        titleTextView()
                        margin(dip(8))
                        text(R.string.large_text)
                        gravity(CENTER_HORIZONTAL)
                    }
                }
            }

            floatingActionButton {
                size(WRAP, WRAP)
                margin(dip(16))
                imageResource(R.mipmap.ic_card_giftcard_white_24dp)

                val fab = Anvil.currentView<FloatingActionButton>()
                val p = fab.layoutParams as CoordinatorLayout.LayoutParams
                p.anchorId = R.id.detail_header_app_bar
                p.anchorGravity = Gravity.BOTTOM or Gravity.END
                p.behavior = ScrollAwareBehavior()
                fab.layoutParams = p

                onClick {
                    onFabClick?.invoke(it)
                }
            }
        }
    }

    fun valueForAttrStyle(attr: Int): Int {
        val typedValue = TypedValue()
        val typedArray = context.obtainStyledAttributes(typedValue.data, intArrayOf(attr))
        val color = typedArray.getColor(0, 0)
        typedArray.recycle()
        return color
    }

}