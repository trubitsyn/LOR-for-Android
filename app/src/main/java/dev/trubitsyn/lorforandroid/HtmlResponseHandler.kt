package dev.trubitsyn.lorforandroid

import com.loopj.android.http.AsyncHttpResponseHandler
import cz.msebera.android.httpclient.Header
import org.jsoup.Jsoup
import org.jsoup.nodes.Element
import java.nio.charset.Charset

abstract class HtmlResponseHandler : AsyncHttpResponseHandler() {
    override fun onSuccess(statusCode: Int, headers: Array<Header>, responseBody: ByteArray) {
        val body = try {
            val resp = String(responseBody, Charset.forName("UTF-8"))
            Jsoup.parse(resp).body()
        } catch (e: Exception) {
            null
        }
        onSuccess(statusCode, headers, body)
    }

    abstract fun onSuccess(statusCode: Int, headers: Array<Header>, responseBody: Element?)
}