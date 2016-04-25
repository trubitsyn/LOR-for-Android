/*
 * Copyright (c) 2016 getsmp
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

package io.github.getsmp.lorforandroid.ui.comment;

import android.os.Bundle;
import android.view.MenuItem;

import java.util.List;

import butterknife.ButterKnife;
import io.github.getsmp.lorforandroid.R;
import io.github.getsmp.lorforandroid.api.model.Comment;
import io.github.getsmp.lorforandroid.ui.base.ThemeActivity;

public class CommentActivity extends ThemeActivity implements CommentClickListener {
    private String url;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comments);
        ButterKnife.bind(this);
        setupActionBar(this);

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
