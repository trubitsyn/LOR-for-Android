package dev.trubitsyn.lorforandroid.site

import okhttp3.MediaType
import okhttp3.ResponseBody
import okio.BufferedSource
import org.jsoup.Jsoup
import org.jsoup.nodes.Element
import retrofit2.Converter
import retrofit2.Retrofit
import java.lang.reflect.Type

class JsoupConverter : Converter<ResponseBody, Element> {
    override fun convert(value: ResponseBody?): Element {
        return Jsoup.parse(value?.string()).body()
    }

    companion object {
        val FACTORY = object : Converter.Factory() {
            override fun responseBodyConverter(type: Type?, annotations: Array<out Annotation>?, retrofit: Retrofit?): Converter<ResponseBody, Element> {
                return JsoupConverter()
            }
        }
    }

    class HtmlResponseBody : ResponseBody() {
        override fun contentLength(): Long {
            TODO("not implemented")
        }

        override fun contentType(): MediaType? {
            TODO("not implemented")
        }

        override fun source(): BufferedSource {
            TODO("not implemented")
        }
    }
}