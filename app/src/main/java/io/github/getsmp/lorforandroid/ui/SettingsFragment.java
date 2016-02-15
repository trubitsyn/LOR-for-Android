package io.github.getsmp.lorforandroid.ui;

import android.os.Bundle;
import android.support.v7.preference.PreferenceFragmentCompat;

import io.github.getsmp.lorforandroid.R;


public class SettingsFragment extends PreferenceFragmentCompat {
    @Override
    public void onCreatePreferences(Bundle bundle, String s) {
        addPreferencesFromResource(R.xml.preferences);
    }
}
