/*
 * Copyright 2015 getsmp
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

package net.voxelplanet.lorforandroid.ui;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import net.voxelplanet.lorforandroid.R;
import net.voxelplanet.lorforandroid.ui.gallery.GalleryFragment;
import net.voxelplanet.lorforandroid.ui.news.NewsFragment;
import net.voxelplanet.lorforandroid.ui.topic.TopicActivity;
import net.voxelplanet.lorforandroid.ui.topic.TopicFragment;
import net.voxelplanet.lorforandroid.ui.tracker.TrackerFragment;
import net.voxelplanet.lorforandroid.ui.util.ItemClickCallback;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, ItemClickCallback {
    private DrawerLayout drawerLayout;
    private int navigationItemId;
    private ActionBarDrawerToggle drawerToggle;
    private ActionBar actionBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarTop);
        setSupportActionBar(toolbar);

        actionBar = getSupportActionBar();
        //actionBar.setHomeAsUpIndicator(R.drawable.ic_menu);
        actionBar.setDisplayHomeAsUpEnabled(true);

        if (savedInstanceState == null) {
            navigationItemId = R.id.drawer_profile;
        } else {
            navigationItemId = savedInstanceState.getInt("NAV_ITEM_ID");
        }

        NavigationView navigationView = (NavigationView) findViewById(R.id.navigationView);
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
        navigate(menuItem.getItemId());
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    private void navigate(int selection) {
        switch (selection) {
            case R.id.drawer_profile:
                actionBar.setTitle(R.string.drawer_profile);
                UserFragment userFragment = new UserFragment();
                getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer, userFragment).commit();
                getSupportFragmentManager().executePendingTransactions();
                userFragment.getInfo();
                break;
            case R.id.drawer_news:
                actionBar.setTitle(R.string.drawer_news);
                NewsFragment newsFragment = new NewsFragment();
                getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer, newsFragment).commit();
                getSupportFragmentManager().executePendingTransactions();
                newsFragment.getListItems();
                break;
            case R.id.drawer_gallery:
                actionBar.setTitle(R.string.drawer_gallery);
                GalleryFragment galleryFragment = new GalleryFragment();
                getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer, galleryFragment).commit();
                getSupportFragmentManager().executePendingTransactions();
                galleryFragment.getListItems();
                break;
            case R.id.drawer_tracker:
                actionBar.setTitle(R.string.drawer_tracker);
                TrackerFragment trackerFragment = new TrackerFragment();
                getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer, trackerFragment).commit();
                getSupportFragmentManager().executePendingTransactions();
                trackerFragment.getListItems();
                break;
            case R.id.drawer_settings:
                actionBar.setTitle(R.string.drawer_settings);
                getFragmentManager().beginTransaction().replace(R.id.fragmentContainer, new SettingsFragment()).commit();
                break;
        }
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
        outState.putInt("NAV_ITEM_ID", navigationItemId);
    }

    @Override
    public void onItemClicked(String url) {
        TopicFragment topicFragment = (TopicFragment) getSupportFragmentManager().findFragmentById(R.id.topicFragment);

        if (topicFragment != null) {
            topicFragment.loadTopic(url);
        } else {
            Intent intent = new Intent(this, TopicActivity.class);
            intent.putExtra("url", url);
            startActivity(intent);
        }
    }
}
