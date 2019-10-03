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

package dev.trubitsyn.lorforandroid.ui.section.forum.section;

import android.content.Intent;
import android.os.Bundle;

import butterknife.ButterKnife;
import dev.trubitsyn.lorforandroid.R;
import dev.trubitsyn.lorforandroid.ui.base.ThemeActivity;
import dev.trubitsyn.lorforandroid.ui.topic.TopicActivity;

public class ForumSectionActivity extends ThemeActivity implements ForumSectionFragment.Callback {
    private String group;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forum_section);
        unbinder = ButterKnife.bind(this);
        setupActionBar(this);

        group = getIntent().getStringExtra("group");
        String name = getIntent().getStringExtra("name");
        actionBar.setTitle(name);
        replace();
    }

    private void replace() {
        ForumSectionFragment fragment = (ForumSectionFragment) getSupportFragmentManager().findFragmentByTag("forumSectionFragment");
        if (fragment == null) fragment = ForumSectionFragment.newInstance(group);
        getSupportFragmentManager().beginTransaction().replace(R.id.forumSectionFragment, fragment, "forumSectionFragment").commit();
    }

    @Override
    public void returnToActivity(String url) {
        Intent intent = new Intent(this, TopicActivity.class);
        intent.putExtra("url", url);
        startActivity(intent);
    }
}
