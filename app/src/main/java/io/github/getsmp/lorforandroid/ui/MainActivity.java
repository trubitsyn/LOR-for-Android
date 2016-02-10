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
import io.github.getsmp.lorforandroid.ui.base.TabFragment;
import io.github.getsmp.lorforandroid.ui.base.UpdateActivity;
import io.github.getsmp.lorforandroid.ui.section.forum.ForumOverviewFragment;
import io.github.getsmp.lorforandroid.ui.section.forum.section.ForumSectionActivity;
import io.github.getsmp.lorforandroid.ui.section.gallery.GalleryFragment;
import io.github.getsmp.lorforandroid.ui.section.news.NewsFragment;
import io.github.getsmp.lorforandroid.ui.section.tracker.TrackerFragmentPagerAdapter;
import io.github.getsmp.lorforandroid.ui.topic.TopicActivity;
import io.github.getsmp.lorforandroid.ui.util.ItemClickCallback;

public class MainActivity extends UpdateActivity implements NavigationView.OnNavigationItemSelectedListener, ItemClickCallback {
    private static final String NAV_ITEM_ID = "NAV_ITEM_ID";
    @Bind(R.id.drawer_layout) DrawerLayout drawerLayout;
    @Bind(R.id.toolbarTop) Toolbar toolbar;
    @Bind(R.id.navigationView) NavigationView navigationView;
    private int navigationItemId;
    private ActionBarDrawerToggle drawerToggle;
    private ActionBar actionBar;
    private Bundle saved;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);

        actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        // TODO: Display logo, collapsing ToolBar

        if (savedInstanceState == null) {
            navigationItemId = R.id.drawer_news;
        } else {
            navigationItemId = savedInstanceState.getInt(NAV_ITEM_ID);
        }

        saved = savedInstanceState;

        navigationView.setNavigationItemSelectedListener(this);

        drawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.open, R.string.close);
        drawerLayout.setDrawerListener(drawerToggle);
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
                fragment = (saved == null) ? new NewsFragment() : fm.findFragmentByTag(tag);
                fm.beginTransaction().replace(R.id.fragmentContainer, fragment, tag).addToBackStack(tag).commit();
                break;
            case R.id.drawer_gallery:
                actionBar.setTitle(R.string.drawer_gallery);
                tag = "gallery";
                fragment = (saved == null) ? new GalleryFragment() : fm.findFragmentByTag(tag);
                fm.beginTransaction().replace(R.id.fragmentContainer, fragment, tag).addToBackStack(tag).commit();
                break;
            case R.id.drawer_tracker:
                actionBar.setTitle(R.string.drawer_tracker);
                tag = "tracker";
                TabFragment tabFragment;
                if (saved == null) {
                    tabFragment = new TabFragment();
                    tabFragment.setAdapter(new TrackerFragmentPagerAdapter(fm));
                } else {
                    tabFragment = (TabFragment) fm.findFragmentByTag(tag);
                }
                fm.beginTransaction().replace(R.id.fragmentContainer, tabFragment, tag).addToBackStack(tag).commit();
                break;
            case R.id.drawer_forum:
                actionBar.setTitle(R.string.drawer_forum);
                tag = "forum";
                fragment = (saved == null) ? new ForumOverviewFragment() : fm.findFragmentByTag(tag);
                fm.beginTransaction().replace(R.id.fragmentContainer, fragment, tag).addToBackStack(tag).commit();
                break;
            case R.id.drawer_settings:
                actionBar.setTitle(R.string.drawer_settings);
                tag = "settings";
                fragment = (saved == null) ? new Fragment() : fm.findFragmentByTag(tag);
                fm.beginTransaction().replace(R.id.fragmentContainer, fragment, tag).addToBackStack(tag).commit();
                // TODO: Show settings
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
            case R.id.refreshButton:
                navigate(navigationItemId);
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
        intent.putExtra("url", url);
        startActivity(intent);
    }

    @Override
    public void onForumSectionRequested(String group, String name) {
        Intent intent = new Intent(MainActivity.this, ForumSectionActivity.class);
        intent.putExtra("group", group);
        intent.putExtra("name", name);
        startActivity(intent);
    }
}
