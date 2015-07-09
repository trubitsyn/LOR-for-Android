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

package net.voxelplanet.lorforandroid.ui.comment;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import net.voxelplanet.lorforandroid.R;
import net.voxelplanet.lorforandroid.model.Comment;

public class CommentActivity extends AppCompatActivity implements CommentClickListener {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comments);

        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarTop);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        if (savedInstanceState == null) {
            CommentFragment commentFragment = (CommentFragment) getSupportFragmentManager().findFragmentById(R.id.commentsFragment);
            String url = getIntent().getStringExtra("url");
            commentFragment.loadComments(url);
        }
    }

    @Override
    public void onParentLink(Comment parentComment) {
        CommentPreviewFragment commentPreviewFragment = new CommentPreviewFragment();
        commentPreviewFragment.initializeView(parentComment);
        commentPreviewFragment.show(getSupportFragmentManager(), "commentPreviewFragment");
    }
}
