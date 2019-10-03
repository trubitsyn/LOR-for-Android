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

package dev.trubitsyn.lorforandroid.ui.section.forum.section

import android.content.Intent
import android.os.Bundle

import dev.trubitsyn.lorforandroid.R
import dev.trubitsyn.lorforandroid.ui.base.ThemeActivity
import dev.trubitsyn.lorforandroid.ui.topic.TopicActivity

class ForumSectionActivity : ThemeActivity(), ForumSectionFragment.Callback {
    private var group: String? = null

    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forum_section)
        setupActionBar(this)

        group = intent.getStringExtra("group")
        val name = intent.getStringExtra("name")
        actionBar?.title = name
        replace()
    }

    private fun replace() {
        var fragment = supportFragmentManager.findFragmentByTag("forumSectionFragment") as ForumSectionFragment?
        if (fragment == null) fragment = ForumSectionFragment.newInstance(group!!)
        supportFragmentManager
                .beginTransaction()
                .replace(R.id.forumSectionFragment, fragment, "forumSectionFragment")
                .commit()
    }

    override fun returnToActivity(url: String) {
        val intent = Intent(this, TopicActivity::class.java)
        intent.putExtra("url", url)
        startActivity(intent)
    }
}
