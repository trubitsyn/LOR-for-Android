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

package io.github.getsmp.lorforandroid.ui.topic;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import butterknife.Bind;
import butterknife.ButterKnife;
import io.github.getsmp.lorforandroid.R;
import io.github.getsmp.lorforandroid.ui.base.ThemeActivity;
import io.github.getsmp.lorforandroid.ui.comment.CommentActivity;
import io.github.getsmp.lorforandroid.ui.section.SectionTypeEnum;
import io.github.getsmp.lorforandroid.util.StringUtils;

public class TopicActivity extends ThemeActivity {
    @Bind(R.id.toolbarTop) Toolbar toolbar;
    private String url;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_topic);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        if (savedInstanceState == null) {
            Intent intent = getIntent();
            SectionTypeEnum type = SectionTypeEnum.values()[getIntent().getIntExtra("type", SectionTypeEnum.NEWS.ordinal())];
            Fragment topicFragment = null;

            switch (type) {
                case GALLERY:
                    url = StringUtils.removeParams(intent.getStringExtra("url"));
                    String imageUrl = intent.getStringExtra("imageUrl");
                    topicFragment = TopicFragmentGallery.newInstance(url, imageUrl);
                    break;
                default:
                    url = StringUtils.removeParams(intent.getStringExtra("url"));
                    topicFragment = TopicFragmentStandard.newInstance(url);
                    break;

            }
            getSupportFragmentManager().beginTransaction().replace(R.id.topicFragmentContainer, topicFragment).commit();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_topic, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            case R.id.showComments:
                Intent intent = new Intent(TopicActivity.this, CommentActivity.class);
                intent.putExtra("url", url);
                startActivity(intent);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
