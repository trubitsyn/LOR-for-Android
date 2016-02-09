package io.github.getsmp.lorforandroid.ui.base;

import android.support.v7.app.AppCompatActivity;
import android.view.Menu;

import io.github.getsmp.lorforandroid.R;

public class UpdateActivity extends AppCompatActivity {
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_wth_refresh, menu);
        return true;
    }
}
