/*
 * Copyright (C) 2015-2016 Nikola Trubitsyn
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package dev.trubitsyn.lorforandroid.ui.topic

import android.content.Intent
import android.os.Bundle
import android.text.Html
import android.text.method.LinkMovementMethod
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.widget.NestedScrollView

import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy

import dev.trubitsyn.lorforandroid.R
import dev.trubitsyn.lorforandroid.api.ApiManager
import dev.trubitsyn.lorforandroid.api.model.Topic
import dev.trubitsyn.lorforandroid.api.model.Topics
import dev.trubitsyn.lorforandroid.ui.ImageActivity
import dev.trubitsyn.lorforandroid.ui.base.LoadableFragment
import dev.trubitsyn.lorforandroid.util.DateUtils
import dev.trubitsyn.lorforandroid.util.PreferenceUtils
import dev.trubitsyn.lorforandroid.util.StringUtils
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class TopicFragment : LoadableFragment() {
    internal val scrollView by lazy { view!!.findViewById<NestedScrollView>(R.id.topicScrollView) }
    internal val title by lazy { view!!.findViewById<TextView>(R.id.topicTitle) }
    internal val tags by lazy { view!!.findViewById<TextView>(R.id.topicTags) }
    internal val author by lazy { view!!.findViewById<TextView>(R.id.topicAuthor) }
    internal val date by lazy { view!!.findViewById<TextView>(R.id.topicDate) }
    internal val image by lazy { view!!.findViewById<ImageView>(R.id.topicImage) }
    internal val message by lazy { view!!.findViewById<TextView>(R.id.topicMessage) }
    private var url: String? = null
    private var imageUrl: String? = null
    private var topic: Topic? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(false)
        url = arguments!!.getString(ARG_URL)
        imageUrl = arguments!!.getString(ARG_IMAGE_URL)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        return inflater.inflate(R.layout.fragment_topic, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        if (savedInstanceState == null) {
            fetchData()
        } else {
            stopRefreshAndShow()
            setTopic()
        }
    }

    override fun fetchData() {
        if (StringUtils.isClub(url!!)) {
            showErrorView(R.string.error_access_denied)
        } else {
            val topics = ApiManager.INSTANCE.apiTopic.getTopic(url!!)
            topics.enqueue(object : Callback<Topics> {
                override fun onResponse(call: Call<Topics>, response: Response<Topics>) {
                    if (response.body() != null) {
                        topic = response.body().topic
                        stopRefreshAndShow()
                        setTopic()
                    } else {
                        showErrorView(R.string.error_network)
                    }
                }

                override fun onFailure(call: Call<Topics>, t: Throwable) {
                    showErrorView(R.string.error_network)
                }
            })
        }
    }

    private fun setTopic() {
        title!!.text = Html.fromHtml(topic!!.title)
        val tagsList = topic!!.tags
        if (tagsList!!.isNotEmpty()) {
            tags!!.visibility = View.VISIBLE
            tags!!.text = StringUtils.tagsFromStrings(tagsList)
        } else {
            tags!!.visibility = View.GONE
        }

        image?.let {
            if (PreferenceUtils.shouldLoadImagesNow(context!!)) {
                loadImageAndSetImageActivityListener()
            } else {
                it.setOnClickListener { loadImageAndSetImageActivityListener() }
            }
        }

        author!!.text = topic!!.author!!.nick
        date!!.text = DateUtils.getDate(topic!!.postDate!!)
        message!!.text = Html.fromHtml(topic!!.message)
        message!!.movementMethod = LinkMovementMethod.getInstance()
    }

    private fun loadImageAndSetImageActivityListener() {
        assert(image != null)
        image!!.setImageDrawable(null)
        Glide.with(context!!)
                .load(imageUrl)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(image!!)

        image!!.setOnClickListener {
            val intent = Intent(activity, ImageActivity::class.java)
            intent.putExtra(ImageActivity.ARG_IMAGE_URL, imageUrl)
            startActivity(intent)
        }
    }

    override fun dataView() = scrollView

    companion object {
        const val ARG_URL = "url"
        const val ARG_IMAGE_URL = "imageUrl"
        const val TAG = "topicFragment"

        fun newInstance(url: String, imageUrl: String): TopicFragment {
            val fragment = TopicFragment()
            val args = Bundle()
            args.putString(ARG_URL, url)
            args.putString(ARG_IMAGE_URL, imageUrl)
            fragment.arguments = args
            return fragment
        }
    }
}
