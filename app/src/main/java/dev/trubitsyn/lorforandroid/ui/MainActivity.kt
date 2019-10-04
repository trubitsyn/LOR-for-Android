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

        if (savedInstanceState == null) {
            if (intent.getBooleanExtra(SettingsFragment.ARG_RESTART_ACTIVITY, false)) {
                currentNavigationItemId = R.id.drawer_settings
            } else {
                currentNavigationItemId = R.id.drawer_news
            }
        } else {
            currentNavigationItemId = savedInstanceState.getInt(NAV_ITEM_ID)
        }

        navigationView!!.setNavigationItemSelectedListener(this)
        drawerToggle = ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.open, R.string.close)
        drawerLayout!!.addDrawerListener(drawerToggle!!)
        drawerLayout!!.addDrawerListener(object : DrawerLayout.SimpleDrawerListener() {
            override fun onDrawerClosed(drawerView: View) {
                super.onDrawerClosed(drawerView)
                if (requestedNavigationItemId != currentNavigationItemId) {
                    currentNavigationItemId = requestedNavigationItemId
                    navigate(requestedNavigationItemId)
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

    private fun navigate(selection: Int) {
        actionBar!!.setDisplayShowCustomEnabled(false)
        val fm = supportFragmentManager
        var fragment: Fragment?
        when (selection) {
            R.id.drawer_news -> {
                actionBar!!.setTitle(R.string.drawer_news)
                fragment = fm.findFragmentByTag(NewsFragment.TAG)
                if (fragment == null) fragment = NewsFragment()
                fm.beginTransaction().replace(R.id.fragmentContainer, fragment, NewsFragment.TAG).commit()
            }
            R.id.drawer_gallery -> {
                actionBar!!.setTitle(R.string.drawer_gallery)
                fragment = fm.findFragmentByTag(GalleryFragment.TAG)
                if (fragment == null) fragment = GalleryFragment.newInstance(GalleryFilterEnum.all)
                fm.beginTransaction().replace(R.id.fragmentContainer, fragment, GalleryFragment.TAG).commit()
            }
            R.id.drawer_tracker -> {
                actionBar!!.setTitle(R.string.drawer_tracker)
                fragment = fm.findFragmentByTag(TrackerFragment.TAG)
                if (fragment == null) fragment = TrackerFragment.newInstance(TrackerFilterEnum.all)
                fm.beginTransaction().replace(R.id.fragmentContainer, fragment, TrackerFragment.TAG).commit()
            }
            R.id.drawer_forum -> {
                actionBar!!.setTitle(R.string.drawer_forum)
                fragment = fm.findFragmentByTag(ForumOverviewFragment.TAG)
                if (fragment == null) fragment = ForumOverviewFragment()
                fm.beginTransaction().replace(R.id.fragmentContainer, fragment, ForumOverviewFragment.TAG).commit()
            }
            R.id.drawer_settings -> {
                actionBar!!.setTitle(R.string.drawer_settings)
                fragment = fm.findFragmentByTag(SettingsFragment.TAG)
                if (fragment == null) fragment = SettingsFragment()
                fm.beginTransaction().replace(R.id.fragmentContainer, fragment, SettingsFragment.TAG).commit()
            }
        }
        fm.executePendingTransactions()
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
        } else {
            super.onBackPressed()
        }
    }

    override fun onConfigurationChanged(newConfiguration: Configuration) {
        super.onConfigurationChanged(newConfiguration)
        drawerToggle!!.onConfigurationChanged(newConfiguration)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt(NAV_ITEM_ID, currentNavigationItemId)
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
        private const val NAV_ITEM_ID = "NAV_ITEM_ID"
    }
}
