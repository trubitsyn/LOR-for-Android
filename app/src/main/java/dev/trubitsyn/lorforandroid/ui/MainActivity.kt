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

package dev.trubitsyn.lorforandroid.ui

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.navigation.NavigationView
import dev.trubitsyn.lorforandroid.R
import dev.trubitsyn.lorforandroid.ui.base.ThemeActivity
import dev.trubitsyn.lorforandroid.ui.section.forum.ForumOverviewFragmentDirections
import dev.trubitsyn.lorforandroid.ui.section.gallery.GalleryFragmentDirections
import dev.trubitsyn.lorforandroid.ui.section.gallery.GalleryItem
import dev.trubitsyn.lorforandroid.ui.topic.TopicActivity
import dev.trubitsyn.lorforandroid.ui.util.ItemClickCallback

class MainActivity : ThemeActivity(), ItemClickCallback {
    private val drawerLayout by lazy { findViewById<DrawerLayout>(R.id.drawer_layout)!! }
    private val navigationView by lazy { findViewById<NavigationView>(R.id.navigationView)!! }
    private val navController by lazy { findNavController(R.id.main_content) }
    private val appBarConfiguration by lazy {
        AppBarConfiguration(
                setOf(R.id.news, R.id.gallery, R.id.tracker, R.id.forum, R.id.settings),
                drawerLayout
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupActionBar()
        navigationView.setupWithNavController(navController)
        setupActionBarWithNavController(navController, appBarConfiguration)
    }

    override fun onSupportNavigateUp() = navController.navigateUp(drawerLayout)

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                drawerLayout.openDrawer(GravityCompat.START)
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START)
        } else super.onBackPressed()
    }

    override fun onTopicRequested(url: String) {
        Intent(this, TopicActivity::class.java).apply {
            putExtra(TopicActivity.ARG_URL, url)
            startActivity(this)
        }
    }

    override fun onGalleryTopicRequested(item: GalleryItem) {
        val action = GalleryFragmentDirections.actionGalleryToTopic(
                url = item.url,
                imageUrl = item.imageUrl
        )
        navController.navigate(action)
    }

    override fun onForumSectionRequested(group: String, name: String) {
        val action = ForumOverviewFragmentDirections.actionForumOverviewToForumSection(
                group = group,
                name = name
        )
        navController.navigate(action)
    }
}
