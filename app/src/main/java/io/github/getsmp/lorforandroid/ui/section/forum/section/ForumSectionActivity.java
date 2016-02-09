package io.github.getsmp.lorforandroid.ui.section.forum.section;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import butterknife.Bind;
import butterknife.ButterKnife;
import io.github.getsmp.lorforandroid.R;
import io.github.getsmp.lorforandroid.ui.base.UpdateActivity;
import io.github.getsmp.lorforandroid.ui.topic.TopicActivity;

public class ForumSectionActivity extends UpdateActivity implements ForumSectionFragment.Callback {
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
