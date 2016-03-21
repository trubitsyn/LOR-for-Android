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

package io.github.getsmp.lorforandroid.ui.section.tracker;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.loopj.android.http.RequestParams;

import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import io.github.getsmp.lorforandroid.R;
import io.github.getsmp.lorforandroid.ui.section.ItemCommon;
import io.github.getsmp.lorforandroid.ui.section.SectionCommon;
import io.github.getsmp.lorforandroid.ui.util.ItemClickCallback;
import io.github.getsmp.lorforandroid.util.FragmentReplaceCallback;
import io.github.getsmp.lorforandroid.util.StringUtils;

public class TrackerFragment extends SectionCommon {
    private String filter;

    @Override
    protected void generateDataSet(Element responseBody) {
        Elements topics = responseBody.select("tbody > tr");
        for (Element topic : topics) {
            items.add(new ItemCommon(
                    topic.select("td:eq(1)").select("a").first().attr("href").substring(1),
                    topic.select("td:eq(1)").select("a").first().ownText(),
                    topic.select("a.secondary").first().ownText(),
                    StringUtils.tagsFromElements(topic.select("span.tag")),
                    topic.select("time").first().ownText(),
                    topic.select("td.dateinterval > time").first().nextSibling().toString().replace(", ", ""),
                    StringUtils.addEnding(topic.select("td.numbers").first().ownText())
            ));
        }
    }

    public static TrackerFragment newInstance(TrackerFilterEnum trackerFilterEnum) {
        TrackerFragment trackerFragment = new TrackerFragment();
        Bundle args = new Bundle();
        args.putString("filter", trackerFilterEnum.name());
        trackerFragment.setArguments(args);
        return trackerFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        filter = getArguments().getString("filter");
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        View spinnerView = View.inflate(getActivity(), R.layout.spinner, null);
        final Spinner spinner = (Spinner) spinnerView.findViewById(R.id.toolbar_spinner);
        final ArrayAdapter<CharSequence> spinnerAdapter = ArrayAdapter.createFromResource(getActivity(), R.array.tracker_spinner, android.R.layout.simple_spinner_item);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(spinnerAdapter);
        spinner.setSelection(TrackerFilterEnum.valueOf(filter).ordinal());

        ((AppCompatActivity) getActivity()).getSupportActionBar().setCustomView(spinnerView);

        final AdapterView.OnItemSelectedListener listener = new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                ((FragmentReplaceCallback) getActivity()).replace(R.id.fragmentContainer, newInstance(TrackerFilterEnum.values()[position]), "tracker");
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        };

        spinner.post(new Runnable() {
            @Override
            public void run() {
                spinner.setOnItemSelectedListener(listener);
            }
        });
    }

    @Override
    public int getItemsPerPage() {
        return 30;
    }

    @Override
    public int getMaxOffset() {
        return 150;
    }

    @Override
    public String getPath() {
        return "tracker";
    }

    @Override
    public RequestParams getRequestParams() {
        return new RequestParams("offset", offset, "filter", filter);
    }

    @Override
    protected RecyclerView.Adapter getAdapter() {
        return new TrackerAdapter(items);
    }

    @Override
    protected void onItemClickCallback(int position) {
        ItemCommon item = (ItemCommon) items.get(position);
        ((ItemClickCallback) context).onTopicRequested(item.getUrl());
    }
}
