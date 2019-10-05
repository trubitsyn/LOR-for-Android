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
import android.content.res.Configuration
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import com.google.android.material.navigation.NavigationView
import dev.trubitsyn.lorforandroid.R
import dev.trubitsyn.lorforandroid.ui.base.ThemeActivity
import dev.trubitsyn.lorforandroid.ui.section.forum.ForumOverviewFragment
import dev.trubitsyn.lorforandroid.ui.section.forum.section.ForumSectionActivity
import dev.trubitsyn.lorforandroid.ui.section.gallery.GalleryFilterEnum
import dev.trubitsyn.lorforandroid.ui.section.gallery.GalleryFragment
import dev.trubitsyn.lorforandroid.ui.section.gallery.GalleryItem
import dev.trubitsyn.lorforandroid.ui.section.news.NewsFragment
import dev.trubitsyn.lorforandroid.ui.section.tracker.TrackerFilterEnum
import dev.trubitsyn.lorforandroid.ui.section.tracker.TrackerFragment
import dev.trubitsyn.lorforandroid.ui.topic.TopicActivity
import dev.trubitsyn.lorforandroid.ui.util.ItemClickCallback

class MainActivity : ThemeActivity(), NavigationView.OnNavigationItemSelectedListener, ItemClickCallback {
    private val drawerLayout by lazy { findViewById<DrawerLayout>(R.id.drawer_layout) }
    private val navigationView by lazy { findViewById<NavigationView>(R.id.navigationView) }
    private var currentNavigationItemId: Int = 0
    private var requestedNavigationItemId: Int = 0
    private var drawerToggle: ActionBarDrawerToggle? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupActionBar(this)

        currentNavigationItemId = savedInstanceState?.getInt(ARG_NAV_ITEM_ID)
                ?: if (intent.getBooleanExtra(SettingsFragment.ARG_RESTART_ACTIVITY, false)) {
                    R.id.drawer_settings
                } else {
                    R.id.drawer_news
                }

        navigationView!!.setNavigationItemSelectedListener(this)
        drawerToggle = ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.open, R.string.close)
        drawerLayout!!.addDrawerListener(drawerToggle!!)
        drawerLayout!!.addDrawerListener(object : DrawerLayout.SimpleDrawerListener() {
            override fun onDrawerClosed(drawerView: View) {
                super.onDrawerClosed(drawerView)
                requestedNavigationItemId.let {
                    currentNavigationItemId = it
                    navigate(it)
                }
            }
        })
        drawerToggle!!.syncState()
        onNavigationItemSelected(navigationView!!.menu.findItem(currentNavigationItemId))
    }

    override fun onNavigationItemSelected(menuItem: MenuItem): Boolean {
        menuItem.isChecked = true
        requestedNavigationItemId = menuItem.itemId
        if (drawerLayout!!.isDrawerOpen(GravityCompat.START)) {
            drawerLayout!!.closeDrawer(GravityCompat.START)
        } else {
            navigate(requestedNavigationItemId)
        }
        return true
    }

    private class NavigationTarget(val title: Int, val fragmentFunc: () -> Fragment, val tag: String)

    private fun navigate(selection: Int) {
        actionBar!!.setDisplayShowCustomEnabled(false)
        val target = when (selection) {
            R.id.drawer_news -> NavigationTarget(
                    title = R.string.drawer_news,
                    fragmentFunc = { NewsFragment() },
                    tag = NewsFragment.TAG
            )
            R.id.drawer_gallery -> NavigationTarget(
                    title = R.string.drawer_gallery,
                    fragmentFunc = { GalleryFragment.newInstance(GalleryFilterEnum.all) },
                    tag = GalleryFragment.TAG
            )
            R.id.drawer_tracker -> NavigationTarget(
                    title = R.string.drawer_tracker,
                    fragmentFunc = { TrackerFragment.newInstance(TrackerFilterEnum.all) },
                    tag = TrackerFragment.TAG
            )
            R.id.drawer_forum -> NavigationTarget(
                    title = R.string.drawer_forum,
                    fragmentFunc = { ForumOverviewFragment() },
                    tag = ForumOverviewFragment.TAG
            )
            R.id.drawer_settings -> NavigationTarget(
                    title = R.string.drawer_settings,
                    fragmentFunc = { SettingsFragment() },
                    tag = SettingsFragment.TAG
            )
            else -> null
        }
        target?.let {
            actionBar!!.setTitle(it.title)
            val fragment = supportFragmentManager.findFragmentByTag(it.tag) ?: it.fragmentFunc()
            supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.fragmentContainer, fragment, it.tag)
                    .commit()
            supportFragmentManager.executePendingTransactions()
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                drawerLayout!!.openDrawer(GravityCompat.START)
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onBackPressed() {
        if (drawerLayout!!.isDrawerOpen(GravityCompat.START)) {
            drawerLayout!!.closeDrawer(GravityCompat.START)
        } else super.onBackPressed()
    }

    override fun onConfigurationChanged(newConfiguration: Configuration) {
        super.onConfigurationChanged(newConfiguration)
        drawerToggle!!.onConfigurationChanged(newConfiguration)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt(ARG_NAV_ITEM_ID, currentNavigationItemId)
    }

    override fun onTopicRequested(url: String) {
        val intent = Intent(this, TopicActivity::class.java)
        intent.putExtra(TopicActivity.ARG_URL, url)
        startActivity(intent)
    }

    override fun onGalleryTopicRequested(item: GalleryItem) {
        val intent = Intent(this, TopicActivity::class.java)
        intent.putExtra(TopicActivity.ARG_URL, item.url)
        intent.putExtra(TopicActivity.ARG_IMAGE_URL, item.imageUrl)
        startActivity(intent)
    }

    override fun onForumSectionRequested(group: String, name: String) {
        val intent = Intent(this@MainActivity, ForumSectionActivity::class.java)
        intent.putExtra(ForumSectionActivity.ARG_GROUP, group)
        intent.putExtra(ForumSectionActivity.ARG_NAME, name)
        startActivity(intent)
    }

    companion object {
        private const val ARG_NAV_ITEM_ID = "navItemId"
    }
}
