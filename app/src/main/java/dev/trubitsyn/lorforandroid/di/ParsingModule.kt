package dev.trubitsyn.lorforandroid.di

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dev.trubitsyn.lorforandroid.site.HtmlParser
import dev.trubitsyn.lorforandroid.site.JsoupParser

@Module
class ParsingModule {

    fun provideHtmlParser(): HtmlParser {
        return JsoupParser()
    }

    fun provideJsonParser(): Gson {
        return GsonBuilder()
                .setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ")
                .create()
    }
}