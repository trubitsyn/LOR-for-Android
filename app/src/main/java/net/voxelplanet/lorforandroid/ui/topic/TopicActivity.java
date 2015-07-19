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

package net.voxelplanet.lorforandroid.ui.topic;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import net.voxelplanet.lorforandroid.R;
import net.voxelplanet.lorforandroid.ui.comment.CommentActivity;
import net.voxelplanet.lorforandroid.util.StringUtils;

import butterknife.Bind;
import butterknife.ButterKnife;

public class TopicActivity extends AppCompatActivity {
    @Bind(R.id.commentsTextView)
    private TextView commentsTextView;
    @Bind(R.id.toolbarTop)
    private Toolbar toolbar;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_topic);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        if (savedInstanceState == null) {
            TopicFragment topicFragment = (TopicFragment) getSupportFragmentManager().findFragmentById(R.id.topicFragment);
            final String url = StringUtils.fixUrl(getIntent().getStringExtra("url"));
            topicFragment.loadTopic(url);
            commentsTextView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(TopicActivity.this, CommentActivity.class);
                    intent.putExtra("url", url);
                    startActivity(intent);
                }
            });
        }
    }
}
