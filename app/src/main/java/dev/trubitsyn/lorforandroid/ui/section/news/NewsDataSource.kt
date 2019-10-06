package dev.trubitsyn.lorforandroid.ui.section.news

import android.content.Context

class NewsDataSource(
        private val context: Context
) {
    val path = "news"

    //val requestParams: RequestParams
    //    get() = RequestParams("offset", offset)

    val itemFactory
        get() = NewsItemFactory(context)
}