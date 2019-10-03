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
import androidx.fragment.app.FragmentManager

import com.google.android.material.navigation.NavigationView

import butterknife.BindView
import butterknife.ButterKnife
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
    @BindView(R.id.drawer_layout)
    internal var drawerLayout: DrawerLayout? = null
    @BindView(R.id.navigationView)
    internal var navigationView: NavigationView? = null
    private var currentNavigationItemId: Int = 0
    private var requestedNavigationItemId: Int = 0
    private var drawerToggle: ActionBarDrawerToggle? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        unbinder = ButterKnife.bind(this)

        setupActionBar(this)

        if (savedInstanceState == null) {
            if (intent.getBooleanExtra(getString(R.string.intent_settings), false)) {
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
            override fun onDrawerClosed(drawerView: View?) {
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
        val tag: String
        when (selection) {
            R.id.drawer_news -> {
                actionBar!!.setTitle(R.string.drawer_news)
                tag = "news"
                fragment = fm.findFragmentByTag(tag)
                if (fragment == null) fragment = NewsFragment()
                fm.beginTransaction().replace(R.id.fragmentContainer, fragment, tag).commit()
            }
            R.id.drawer_gallery -> {
                actionBar!!.setTitle(R.string.drawer_gallery)
                tag = "gallery"
                fragment = fm.findFragmentByTag(tag)
                if (fragment == null) fragment = GalleryFragment.newInstance(GalleryFilterEnum.all)
                fm.beginTransaction().replace(R.id.fragmentContainer, fragment, tag).commit()
            }
            R.id.drawer_tracker -> {
                actionBar!!.setTitle(R.string.drawer_tracker)
                tag = "tracker"
                fragment = fm.findFragmentByTag(tag)
                if (fragment == null) fragment = TrackerFragment.newInstance(TrackerFilterEnum.all)
                fm.beginTransaction().replace(R.id.fragmentContainer, fragment, tag).commit()
            }
            R.id.drawer_forum -> {
                actionBar!!.setTitle(R.string.drawer_forum)
                tag = "forum"
                fragment = fm.findFragmentByTag(tag)
                if (fragment == null) fragment = ForumOverviewFragment()
                fm.beginTransaction().replace(R.id.fragmentContainer, fragment, tag).commit()
            }
            R.id.drawer_settings -> {
                actionBar!!.setTitle(R.string.drawer_settings)
                tag = "settings"
                fragment = fm.findFragmentByTag(tag)
                if (fragment == null) fragment = SettingsFragment()
                fm.beginTransaction().replace(R.id.fragmentContainer, fragment, tag).commit()
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
        intent.putExtra("url", url)
        startActivity(intent)
    }

    override fun onGalleryTopicRequested(item: GalleryItem) {
        val intent = Intent(this, TopicActivity::class.java)
        intent.putExtra("url", item.url)
        intent.putExtra("imageUrl", item.imageUrl)
        startActivity(intent)
    }

    override fun onForumSectionRequested(group: String, name: String) {
        val intent = Intent(this@MainActivity, ForumSectionActivity::class.java)
        intent.putExtra("group", group)
        intent.putExtra("name", name)
        startActivity(intent)
    }

    companion object {
        private val NAV_ITEM_ID = "NAV_ITEM_ID"
    }
}
