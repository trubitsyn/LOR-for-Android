package io.github.getsmp.lorforandroid.ui.section.forum.section;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import butterknife.Bind;
import butterknife.ButterKnife;
import io.github.getsmp.lorforandroid.R;

public class ForumSectionActivity extends AppCompatActivity {
    @Bind(R.id.toolbarTop) Toolbar toolbar;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forum_section);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        String group = getIntent().getStringExtra("group");
        ForumSectionFragment fragment = ForumSectionFragment.newInstance(group);
        getSupportFragmentManager().beginTransaction().replace(R.id.forumSectionFragment, fragment).commit();
    }
}
