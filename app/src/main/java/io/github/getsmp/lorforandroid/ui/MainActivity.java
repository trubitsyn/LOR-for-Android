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

package io.github.getsmp.lorforandroid.ui;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import io.github.getsmp.lorforandroid.R;
import io.github.getsmp.lorforandroid.ui.base.TabFragment;
import io.github.getsmp.lorforandroid.ui.notification.NotificationReceiver;
import io.github.getsmp.lorforandroid.ui.section.gallery.GalleryFragment;
import io.github.getsmp.lorforandroid.ui.section.news.NewsFragment;
import io.github.getsmp.lorforandroid.ui.section.tracker.TrackerFragmentPagerAdapter;
import io.github.getsmp.lorforandroid.ui.topic.TopicActivity;
import io.github.getsmp.lorforandroid.ui.topic.TopicFragment;
import io.github.getsmp.lorforandroid.ui.util.ItemClickCallback;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, ItemClickCallback {
    @Bind(R.id.drawer_layout) DrawerLayout drawerLayout;
    @Bind(R.id.toolbarTop) Toolbar toolbar;
    @Bind(R.id.navigationView) NavigationView navigationView;
    private int navigationItemId;
    private static final String NAV_ITEM_ID = "NAV_ITEM_ID";
    private ActionBarDrawerToggle drawerToggle;
    private ActionBar actionBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        setupNotificationCheck();
        setSupportActionBar(toolbar);

        actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        // TODO: Display logo, collapsing ToolBar

        if (savedInstanceState == null) {
            navigationItemId = R.id.drawer_profile;
        } else {
            navigationItemId = savedInstanceState.getInt(NAV_ITEM_ID);
        }

        navigationView.setNavigationItemSelectedListener(this);

        drawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.open, R.string.close);
        drawerLayout.setDrawerListener(drawerToggle);
        drawerToggle.syncState();

        onNavigationItemSelected(navigationView.getMenu().findItem(navigationItemId));
    }

    private void setupNotificationCheck() {
        Intent alarmIntent = new Intent(this, NotificationReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, alarmIntent, 0);
        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        int interval = 1000 * 60 * 5; // Every 5 minutes
        alarmManager.cancel(pendingIntent);
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(), interval, pendingIntent);
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
        switch (selection) {
            case R.id.drawer_profile:
                actionBar.setTitle(R.string.drawer_profile);
                UserFragment userFragment = new UserFragment();
                fm.beginTransaction().replace(R.id.fragmentContainer, userFragment).commit();
                fm.executePendingTransactions();
                userFragment.getInfo();
                break;
            case R.id.drawer_news:
                actionBar.setTitle(R.string.drawer_news);
                NewsFragment newsFragment = new NewsFragment();
                fm.beginTransaction().replace(R.id.fragmentContainer, newsFragment).commit();
                fm.executePendingTransactions();
                newsFragment.getListItems();
                break;
            case R.id.drawer_gallery:
                actionBar.setTitle(R.string.drawer_gallery);
                GalleryFragment galleryFragment = new GalleryFragment();
                fm.beginTransaction().replace(R.id.fragmentContainer, galleryFragment).commit();
                fm.executePendingTransactions();
                galleryFragment.getListItems();
                break;
            case R.id.drawer_tracker:
                actionBar.setTitle(R.string.drawer_tracker);
                TabFragment tabFragment = new TabFragment();
                tabFragment.setAdapter(new TrackerFragmentPagerAdapter(fm));
                fm.beginTransaction().replace(R.id.fragmentContainer, tabFragment).commit();
                fm.executePendingTransactions();
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
        outState.putInt(NAV_ITEM_ID, navigationItemId);
    }

    @Override
    public void onItemClicked(String url) {
        TopicFragment topicFragment = (TopicFragment) getSupportFragmentManager().findFragmentById(R.id.topicFragment);

        // TODO: Add support for tablets
        if (topicFragment != null) {
            topicFragment.loadTopic(url);
        } else {
            Intent intent = new Intent(this, TopicActivity.class);
            intent.putExtra("url", url);
            startActivity(intent);
        }
    }
}
