/*
 * Copyright 2016 getsmp
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.github.getsmp.lorforandroid.ui;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import butterknife.Bind;
import butterknife.ButterKnife;
import io.github.getsmp.lorforandroid.R;
import io.github.getsmp.lorforandroid.ui.base.ThemeActivity;
import io.github.getsmp.lorforandroid.ui.section.SectionTypeEnum;
import io.github.getsmp.lorforandroid.ui.section.forum.ForumOverviewFragment;
import io.github.getsmp.lorforandroid.ui.section.forum.section.ForumSectionActivity;
import io.github.getsmp.lorforandroid.ui.section.gallery.GalleryFilterEnum;
import io.github.getsmp.lorforandroid.ui.section.gallery.GalleryFragment;
import io.github.getsmp.lorforandroid.ui.section.gallery.GalleryItem;
import io.github.getsmp.lorforandroid.ui.section.news.NewsFragment;
import io.github.getsmp.lorforandroid.ui.section.tracker.TrackerFilterEnum;
import io.github.getsmp.lorforandroid.ui.section.tracker.TrackerFragment;
import io.github.getsmp.lorforandroid.ui.topic.TopicActivity;
import io.github.getsmp.lorforandroid.ui.util.ItemClickCallback;
import io.github.getsmp.lorforandroid.util.FragmentReplaceCallback;

public class MainActivity extends ThemeActivity implements NavigationView.OnNavigationItemSelectedListener, ItemClickCallback, FragmentReplaceCallback {
    private static final String NAV_ITEM_ID = "NAV_ITEM_ID";
    @Bind(R.id.drawer_layout) DrawerLayout drawerLayout;
    @Bind(R.id.toolbarTop) Toolbar toolbar;
    @Bind(R.id.navigationView) NavigationView navigationView;
    private int navigationItemId;
    private ActionBarDrawerToggle drawerToggle;
    private ActionBar actionBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);

        actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowCustomEnabled(true);

        // TODO: Display logo, collapsing ToolBar

        if (savedInstanceState == null) {
            if (getIntent().getBooleanExtra(getString(R.string.intent_settings), false)) {
                navigationItemId = R.id.drawer_settings;
            } else {
                navigationItemId = R.id.drawer_news;
            }
        } else {
            navigationItemId = savedInstanceState.getInt(NAV_ITEM_ID);
        }

        navigationView.setNavigationItemSelectedListener(this);

        drawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.open, R.string.close);
        drawerLayout.addDrawerListener(drawerToggle);
        drawerToggle.syncState();

        onNavigationItemSelected(navigationView.getMenu().findItem(navigationItemId));
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem menuItem) {
        menuItem.setChecked(true);
        navigationItemId = menuItem.getItemId();
        navigate(navigationItemId);
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    private void navigate(int selection) {
        FragmentManager fm = getSupportFragmentManager();
        Fragment fragment;
        String tag;
        switch (selection) {
            case R.id.drawer_news:
                actionBar.setTitle(R.string.drawer_news);
                tag = "news";
                fragment = fm.findFragmentByTag(tag);
                if (fragment == null) fragment = new NewsFragment();
                fm.beginTransaction().replace(R.id.fragmentContainer, fragment, tag).commit();
                break;
            case R.id.drawer_gallery:
                actionBar.setTitle(R.string.drawer_gallery);
                tag = "gallery";
                fragment = fm.findFragmentByTag(tag);
                if (fragment == null) fragment = GalleryFragment.newInstance(GalleryFilterEnum.all);
                fm.beginTransaction().replace(R.id.fragmentContainer, fragment, tag).commit();
                break;
            case R.id.drawer_tracker:
                actionBar.setTitle(R.string.drawer_tracker);
                tag = "tracker";
                fragment = fm.findFragmentByTag(tag);
                if (fragment == null) fragment = TrackerFragment.newInstance(TrackerFilterEnum.all);
                fm.beginTransaction().replace(R.id.fragmentContainer, fragment, tag).commit();
                break;
            case R.id.drawer_forum:
                actionBar.setTitle(R.string.drawer_forum);
                tag = "forum";
                fragment = fm.findFragmentByTag(tag);
                if (fragment == null) fragment = new ForumOverviewFragment();
                fm.beginTransaction().replace(R.id.fragmentContainer, fragment, tag).commit();
                break;
            case R.id.drawer_settings:
                actionBar.setTitle(R.string.drawer_settings);
                tag = "settings";
                fragment = fm.findFragmentByTag(tag);
                if (fragment == null) fragment = new SettingsFragment();
                fm.beginTransaction().replace(R.id.fragmentContainer, fragment, tag).commit();
                break;
        }
        fm.executePendingTransactions();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                drawerLayout.openDrawer(GravityCompat.START);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public void onConfigurationChanged(Configuration newConfiguration) {
        super.onConfigurationChanged(newConfiguration);
        drawerToggle.onConfigurationChanged(newConfiguration);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(NAV_ITEM_ID, navigationItemId);
    }

    @Override
    public void onTopicRequested(String url) {
        Intent intent = new Intent(this, TopicActivity.class);
        intent.putExtra("type", SectionTypeEnum.NEWS.ordinal());
        intent.putExtra("url", url);
        startActivity(intent);
    }

    @Override
    public void onGalleryTopicRequested(GalleryItem item) {
        Intent intent = new Intent(this, TopicActivity.class);
        intent.putExtra("type", SectionTypeEnum.GALLERY.ordinal());
        intent.putExtra("url", item.getUrl());
        intent.putExtra("imageUrl", item.getImageUrl());
        startActivity(intent);
    }

    @Override
    public void onForumSectionRequested(String group, String name) {
        Intent intent = new Intent(MainActivity.this, ForumSectionActivity.class);
        intent.putExtra("group", group);
        intent.putExtra("name", name);
        startActivity(intent);
    }

    @Override
    public void replace(int containerId, Fragment fragment, String tag) {
        getSupportFragmentManager().beginTransaction().replace(containerId, fragment, tag).commit();
    }
}
