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

import android.os.Bundle
import android.text.Html
import android.text.method.LinkMovementMethod
import android.view.*
import android.widget.ImageView
import android.widget.TextView
import androidx.core.widget.NestedScrollView
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import dev.trubitsyn.lorforandroid.R
import dev.trubitsyn.lorforandroid.api.ApiManager
import dev.trubitsyn.lorforandroid.api.model.Topic
import dev.trubitsyn.lorforandroid.api.model.Topics
import dev.trubitsyn.lorforandroid.ui.base.LoadableFragment
import dev.trubitsyn.lorforandroid.util.DateUtils
import dev.trubitsyn.lorforandroid.util.PreferenceUtils
import dev.trubitsyn.lorforandroid.util.StringUtils
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class TopicFragment : LoadableFragment() {
    private val scrollView by lazy { view!!.findViewById<NestedScrollView>(R.id.topicScrollView)!! }
    internal val title by lazy { view!!.findViewById<TextView>(R.id.topicTitle)!! }
    internal val tags by lazy { view!!.findViewById<TextView>(R.id.topicTags)!! }
    internal val author by lazy { view!!.findViewById<TextView>(R.id.topicAuthor)!! }
    internal val date by lazy { view!!.findViewById<TextView>(R.id.topicDate)!! }
    private val image by lazy { view!!.findViewById<ImageView>(R.id.topicImage)!! }
    private val message by lazy { view!!.findViewById<TextView>(R.id.topicMessage)!! }
    private lateinit var url: String
    private var imageUrl: String? = null
    private lateinit var topic: Topic

    private val args by navArgs<TopicFragmentArgs>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(false)
        url = args.url
        imageUrl = args.imageUrl
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu_topic, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                findNavController().navigateUp()
                return true
            }
            R.id.showComments -> {
                val action = TopicFragmentDirections.actionTopicToComment(url)
                findNavController().navigate(action)
                return true
            }
        }
        return super.onOptionsItemSelected(item)
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
        if (StringUtils.isClub(url)) {
            showErrorView(R.string.error_access_denied)
        } else {
            val topics = ApiManager.INSTANCE.apiTopic.getTopic(url)
            topics.enqueue(object : Callback<Topics> {
                override fun onResponse(call: Call<Topics>, response: Response<Topics>) {
                    response.body()?.let {
                        topic = it.topic ?: return@let null
                        stopRefreshAndShow()
                        setTopic()
                    } ?: showErrorView(R.string.error_network)
                }

                override fun onFailure(call: Call<Topics>, t: Throwable) {
                    showErrorView(R.string.error_network)
                }
            })
        }
    }

    private fun setTopic() {
        title.text = Html.fromHtml(topic.title)
        val tagsList = topic.tags
        if (tagsList!!.isNotEmpty()) {
            tags.visibility = View.VISIBLE
            tags.text = StringUtils.tagsFromStrings(tagsList)
        } else {
            tags.visibility = View.GONE
        }

        imageUrl?.let { imageUrl ->
            if (PreferenceUtils.shouldLoadImagesNow(context!!)) {
                loadImageAndSetImageActivityListener(imageUrl)
            } else {
                image.setOnClickListener { loadImageAndSetImageActivityListener(imageUrl) }
            }
        }

        author.text = topic.author!!.nick
        date.text = DateUtils.format(topic.postDate!!)
        message.text = Html.fromHtml(topic.message)
        message.movementMethod = LinkMovementMethod.getInstance()
    }

    private fun loadImageAndSetImageActivityListener(imageUrl: String) {
        image.setImageDrawable(null)
        Glide.with(context!!)
                .load(imageUrl)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(image)

        image.setOnClickListener {
            val action = TopicFragmentDirections.actionTopicToImageActivity(imageUrl)
            findNavController().navigate(action)
        }
    }

    override fun dataView() = scrollView

    companion object {
        const val TAG = "topicFragment"
    }
}
