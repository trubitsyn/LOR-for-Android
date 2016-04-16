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

import butterknife.ButterKnife;
import io.github.getsmp.lorforandroid.R;
import io.github.getsmp.lorforandroid.ui.base.ThemeActivity;
import io.github.getsmp.lorforandroid.ui.topic.TopicActivity;

public class ForumSectionActivity extends ThemeActivity implements ForumSectionFragment.Callback {
    private String group;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forum_section);
        ButterKnife.bind(this);
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
