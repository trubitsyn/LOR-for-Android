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

package io.github.getsmp.lorforandroid.ui.section.forum.section;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import butterknife.Bind;
import butterknife.ButterKnife;
import io.github.getsmp.lorforandroid.R;
import io.github.getsmp.lorforandroid.ui.base.ThemeActivity;
import io.github.getsmp.lorforandroid.ui.topic.TopicActivity;

public class ForumSectionActivity extends ThemeActivity implements ForumSectionFragment.Callback {
    @Bind(R.id.toolbarTop) Toolbar toolbar;
    private ForumSectionFragment fragment;
    private String group;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forum_section);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        group = getIntent().getStringExtra("group");
        String name = getIntent().getStringExtra("name");
        getSupportActionBar().setTitle(name);
        replace();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.refreshButton:
                replace();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void replace() {
        ForumSectionFragment fragment = ForumSectionFragment.newInstance(group);
        getSupportFragmentManager().beginTransaction().replace(R.id.forumSectionFragment, fragment).commit();
    }

    @Override
    public void returnToActivity(String url) {
        Intent intent = new Intent(this, TopicActivity.class);
        intent.putExtra("url", url);
        startActivity(intent);
    }
}
