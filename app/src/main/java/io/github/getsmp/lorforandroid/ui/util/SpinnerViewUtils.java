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
