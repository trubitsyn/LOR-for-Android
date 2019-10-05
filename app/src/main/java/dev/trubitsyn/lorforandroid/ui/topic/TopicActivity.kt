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
import android.view.Menu
import android.view.MenuItem

import dev.trubitsyn.lorforandroid.R
import dev.trubitsyn.lorforandroid.ui.base.ThemeActivity
import dev.trubitsyn.lorforandroid.ui.comment.CommentActivity
import dev.trubitsyn.lorforandroid.util.StringUtils

class TopicActivity : ThemeActivity() {
    private var url: String? = null

    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_topic)
        setupActionBar(this)
        val topicFragment = supportFragmentManager.findFragmentByTag(TopicFragment.TAG) ?: {
            url = StringUtils.removeParams(intent.getStringExtra(ARG_URL)!!)
            val imageUrl = intent.getStringExtra(ARG_IMAGE_URL)
            TopicFragment.newInstance(url!!, imageUrl)
        }()
        supportFragmentManager
                .beginTransaction()
                .replace(R.id.topicFragmentContainer, topicFragment, TopicFragment.TAG)
                .commit()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_topic, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                finish()
                return true
            }
            R.id.showComments -> {
                val intent = Intent(this@TopicActivity, CommentActivity::class.java)
                intent.putExtra(CommentActivity.ARG_URL, url)
                startActivity(intent)
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    companion object {
        const val ARG_URL = "url"
        const val ARG_IMAGE_URL = "imageUrl"
    }
}
