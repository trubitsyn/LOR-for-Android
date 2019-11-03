package dev.trubitsyn.lorforandroid.site

import org.jsoup.Jsoup

class JsoupParser : HtmlParser {
    override fun parse(document: String) {
        Jsoup.parse(document)
    }
}