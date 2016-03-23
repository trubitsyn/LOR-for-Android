/*
 *
 *  * Copyright 2016 getsmp
 *  * Licensed under the Apache License, Version 2.0 (the "License");
 *  * you may not use this file except in compliance with the License.
 *  * You may obtain a copy of the License at
 *  *
 *  *     http://www.apache.org/licenses/LICENSE-2.0
 *  *
 *  * Unless required by applicable law or agreed to in writing, software
 *  * distributed under the License is distributed on an "AS IS" BASIS,
 *  * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  * See the License for the specific language governing permissions and
 *  * limitations under the License.
 *
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
