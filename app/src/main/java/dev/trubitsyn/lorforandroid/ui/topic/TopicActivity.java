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

package dev.trubitsyn.lorforandroid.ui.topic;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import butterknife.ButterKnife;
import dev.trubitsyn.lorforandroid.R;
import dev.trubitsyn.lorforandroid.ui.base.ThemeActivity;
import dev.trubitsyn.lorforandroid.ui.comment.CommentActivity;
import dev.trubitsyn.lorforandroid.util.StringUtils;

public class TopicActivity extends ThemeActivity {
    private String url;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_topic);
        ButterKnife.bind(this);
        setupActionBar(this);

        String tag = "topicFragment";
        TopicFragment topicFragment = (TopicFragment) getSupportFragmentManager().findFragmentByTag(tag);

        if (topicFragment == null) {
            Intent intent = getIntent();
            url = StringUtils.removeParams(intent.getStringExtra("url"));
            String imageUrl = intent.getStringExtra("imageUrl");
            topicFragment = TopicFragment.newInstance(url, imageUrl);
        }
        getSupportFragmentManager().beginTransaction().replace(R.id.topicFragmentContainer, topicFragment, tag).commit();
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
