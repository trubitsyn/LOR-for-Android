package io.github.getsmp.lorforandroid.ui.util;

import android.app.Activity;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import io.github.getsmp.lorforandroid.R;

public class SpinnerViewUtils {
    public static void setSpinnerView(Activity activity, int stringArrayResource, int defaultSelection, final AdapterView.OnItemSelectedListener listener) {
        View spinnerView = View.inflate(activity, R.layout.spinner, null);
        final Spinner spinner = (Spinner) spinnerView.findViewById(R.id.toolbar_spinner);
        final ArrayAdapter<CharSequence> spinnerAdapter = ArrayAdapter.createFromResource(activity, stringArrayResource, android.R.layout.simple_spinner_item);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(spinnerAdapter);
        spinner.setSelection(defaultSelection);

        ActionBar supportBar =((AppCompatActivity) activity).getSupportActionBar();
        supportBar.setDisplayShowCustomEnabled(true);
        supportBar.setCustomView(spinnerView);

        spinner.post(new Runnable() {
            @Override
            public void run() {
                spinner.setOnItemSelectedListener(listener);
            }
        });
    }
}
