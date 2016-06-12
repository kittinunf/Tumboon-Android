package com.github.kittinunf.tumboon.view

import android.content.Context
import com.github.kittinunf.tumboon.R
import com.github.kittinunf.tumboon.model.Charity
import trikita.anvil.Anvil
import trikita.anvil.DSL.*
import trikita.anvil.RenderableRecyclerViewAdapter
import trikita.anvil.RenderableView
import trikita.anvil.recyclerview.v7.RecyclerViewv7DSL.*
import kotlin.properties.Delegates
import trikita.anvil.BaseDSL.R as resource

class TumboonListView(context: Context) : RenderableView(context) {

    var onItemClick: ((Int) -> Unit)? = null

    var items by Delegates.observable(emptyList<Charity>()) { meta, oldValue, newValue ->
        // re-render view
        Anvil.render()
    }

    override fun view() {

        val charities = items
        val listAdapter = RenderableRecyclerViewAdapter.withItems(charities, itemLayout {
            onItemClick?.invoke(it)
        })

        recyclerView {
            size(MATCH, MATCH)
            linearLayoutManager()
            adapter(listAdapter)
        }
    }

    fun itemLayout(onClick: ((Int) -> Unit)? = null): (Int, Charity) -> Unit {
        return { index, item ->
            relativeLayout {
                size(MATCH, WRAP)
                minHeight(dip(72))

                imageView {
                    id(R.id.main_recycler_item_image)
                    mediumSizeLogo()
                    loadImageUrl(context, item.logoUrl)
                    alignParentLeft()
                    centerVertical()
                }

                textView {
                    id(R.id.main_recycler_item_title)
                    headLineTextView()
                    text(item.name)
                    toRightOf(R.id.main_recycler_item_image)
                    centerVertical()
                }

                view {
                    lineView(context, R.color.material_grey_100)
                    alignParentBottom()
                }

                onClick {
                    onClick?.invoke(item.id)
                }
            }
        }
    }

}