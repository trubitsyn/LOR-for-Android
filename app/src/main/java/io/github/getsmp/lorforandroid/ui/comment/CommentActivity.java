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

package io.github.getsmp.lorforandroid.ui.comment;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import io.github.getsmp.lorforandroid.R;
import io.github.getsmp.lorforandroid.model.Comment;

public class CommentActivity extends AppCompatActivity implements CommentClickListener {
    @Bind(R.id.toolbarTop) Toolbar toolbar;
    private String url;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comments);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        if (savedInstanceState == null) {
            url = getIntent().getStringExtra("url");
            replace();
        }
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
        CommentFragment commentFragment = CommentFragment.newInstance(url);
        getSupportFragmentManager().beginTransaction().replace(R.id.commentFragmentContainer, commentFragment).commit();
    }

    @Override
    public void showParent(List<Comment> comments, Comment parentComment) {
        CommentPreviewFragment commentPreviewFragment = new CommentPreviewFragment();
        commentPreviewFragment.setComments(comments, parentComment);
        commentPreviewFragment.show(getSupportFragmentManager(), "commentPreviewFragment");
    }
}
